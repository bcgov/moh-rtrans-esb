#!/bin/bash
# Manual feature build + deploy for rtrans-esb
# Usage:
#   Build & deploy: ./rtrans-feature-deploy.sh <environment> <image-tag>
#   Promote:        ./rtrans-feature-deploy.sh promote <source-env> <target-env> <image-tag>
#
#   Environments: silver-dev, silver-test, silver-sbx, silver-prod, gold-prod, gold-dr
#   Image tag: descriptive name (e.g. java_11_upgrade)
#
#   The registry tag is: <image-tag>-<SHORT_SHA>
#   e.g. java_11_upgrade-a1b2c3d4e5f6
#
# Examples:
#   ./rtrans-feature-deploy.sh silver-dev java_11_upgrade
#   ./rtrans-feature-deploy.sh promote silver-dev silver-test java_11_upgrade-a1b2c3d4e5f6
#   ./rtrans-feature-deploy.sh promote silver-test silver-sbx java_11_upgrade-a1b2c3d4e5f6
#   ./rtrans-feature-deploy.sh promote silver-test silver-prod java_11_upgrade-a1b2c3d4e5f6
#
#   ./rtrans-feature-deploy.sh gold-prod java_11_upgrade
#   ./rtrans-feature-deploy.sh gold-dr   java_11_upgrade-a1b2c3d4e5f6
#
# Notes:
#   - Build mode appends SHORT_SHA to your tag automatically
#   - Promote mode requires the full composite tag (with SHA)
#   - gold-prod builds, pushes and deploys to Gold, then prompts to switch to Gold DR
#   - gold-dr is a promote-only target (no build, just tag + deploy on Gold DR cluster)
#   - You cannot be logged into Gold and Gold DR simultaneously
#
# Prerequisites: podman, oc, mvn, git (WSL2)
# Must be logged into the target OpenShift cluster before running.
set -eu

IMAGE_NAME="rtrans-esb"
GOLD_REGISTRY="image-registry.apps.gold.devops.gov.bc.ca"
GOLDDR_REGISTRY="image-registry.apps.golddr.devops.gov.bc.ca"
SILVER_REGISTRY="image-registry.apps.silver.devops.gov.bc.ca"
INTERNAL_REGISTRY="image-registry.openshift-image-registry.svc:5000"

# Returns: namespace tools-namespace imagestream deployment
resolve_env() {
  case "$1" in
    silver-dev)  echo "c5839f-dev c5839f-tools rtrans-esb-dev rtrans-esb-dev" ;;
    silver-test) echo "c5839f-test c5839f-tools rtrans-esb-test rtrans-esb-test" ;;
    silver-sbx)  echo "c5839f-test c5839f-tools rtrans-esb-sbx rtrans-esb-sbx" ;;
    silver-prod) echo "c5839f-prod c5839f-tools rtrans-esb rtrans-esb" ;;
    gold-prod)   echo "c8d6f2-prod c8d6f2-tools rtrans-esb rtrans-esb" ;;
    gold-dr)     echo "c8d6f2-prod c8d6f2-tools rtrans-esb rtrans-esb" ;;
    *)
      echo "Unknown environment: $1" >&2
      echo "Valid: silver-dev, silver-test, silver-sbx, silver-prod, gold-prod, gold-dr" >&2
      exit 1
      ;;
  esac
}

# --- Promote mode ---
if [ "${1:-}" = "promote" ]; then
  SOURCE_ENV="${2:?Usage: $0 promote <source-env> <target-env> <full-image-tag>}"
  TARGET_ENV="${3:?Usage: $0 promote <source-env> <target-env> <full-image-tag>}"
  FULL_TAG="${4:?Usage: $0 promote <source-env> <target-env> <full-image-tag>}"

  SRC=$(resolve_env "${SOURCE_ENV}")
  SRC_NS=$(echo "${SRC}" | awk '{print $1}')
  SRC_IS=$(echo "${SRC}" | awk '{print $3}')

  TGT=$(resolve_env "${TARGET_ENV}")
  TGT_NS=$(echo "${TGT}" | awk '{print $1}')
  TGT_IS=$(echo "${TGT}" | awk '{print $3}')
  TGT_DEPLOY=$(echo "${TGT}" | awk '{print $4}')

  oc whoami || { echo "Not logged in to OpenShift."; exit 1; }

  echo "=== Promoting ${FULL_TAG}: ${SOURCE_ENV} -> ${TARGET_ENV} ==="

  echo "oc tag ${SRC_NS}/${SRC_IS}:${FULL_TAG} ${TGT_NS}/${TGT_IS}:${FULL_TAG}"
  oc tag ${SRC_NS}/${SRC_IS}:${FULL_TAG} ${TGT_NS}/${TGT_IS}:${FULL_TAG}

  echo "oc set image \"deployment/${TGT_DEPLOY}\" rtrans-esb=\"${INTERNAL_REGISTRY}/${TGT_NS}/${TGT_IS}:${FULL_TAG}\" -n \"${TGT_NS}\""
  oc set image "deployment/${TGT_DEPLOY}" rtrans-esb="${INTERNAL_REGISTRY}/${TGT_NS}/${TGT_IS}:${FULL_TAG}" -n "${TGT_NS}"

  exit 0
fi

# --- Build & deploy mode ---
ENV="${1:?Usage: $0 <environment> <image-tag>}"
IMAGE_TAG="${2:?Usage: $0 <environment> <image-tag>}"

BRANCH_NAME=$(git rev-parse --abbrev-ref HEAD)
SHORT_SHA=$(git rev-parse --short=12 HEAD)
FULL_TAG="${IMAGE_TAG}-${SHORT_SHA}"

echo "=== Branch: ${BRANCH_NAME} | Tag: ${IMAGE_TAG} | SHA: ${SHORT_SHA} ==="
echo "=== Registry tag: ${FULL_TAG} ==="

# 1. Maven build
cd rtrans-esb
mvn install -Dimpl-version="${IMAGE_TAG}" -Dgit-commit="${SHORT_SHA}"
cd ..

# 2. Podman build (local image stays 'latest' to avoid accumulation)
podman build -t "${IMAGE_NAME}:latest" -f rtrans-esb/Dockerfile rtrans-esb/

RESOLVED=$(resolve_env "${ENV}")
TARGET_NS=$(echo "${RESOLVED}" | awk '{print $1}')
TOOLS_NS=$(echo "${RESOLVED}" | awk '{print $2}')
TARGET_IS=$(echo "${RESOLVED}" | awk '{print $3}')
TARGET_DEPLOY=$(echo "${RESOLVED}" | awk '{print $4}')

case "${ENV}" in
  silver-dev|silver-test|silver-sbx|silver-prod)
    echo "=== Deploying to ${ENV} ==="
    oc whoami || { echo "Not logged in. Run: oc login --server=https://api.silver.devops.gov.bc.ca:6443"; exit 1; }
    oc whoami -t | podman login "${SILVER_REGISTRY}" -u "$(oc whoami)" --password-stdin

    podman tag "${IMAGE_NAME}:latest" "${SILVER_REGISTRY}/${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}"
    podman push "${SILVER_REGISTRY}/${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}"

    oc tag "${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}" "${TARGET_NS}/${TARGET_IS}:${FULL_TAG}"
    oc set image "deployment/${TARGET_DEPLOY}" rtrans-esb="${INTERNAL_REGISTRY}/${TARGET_NS}/${TARGET_IS}:${FULL_TAG}" -n "${TARGET_NS}"

    echo "=== ${ENV} done ==="
    ;;

  gold-prod)
    echo "=== Deploying to Gold Prod ==="
    oc whoami || { echo "Not logged in. Run: oc login --server=https://api.gold.devops.gov.bc.ca:6443"; exit 1; }
    oc whoami -t | podman login "${GOLD_REGISTRY}" -u "$(oc whoami)" --password-stdin

    podman tag "${IMAGE_NAME}:latest" "${GOLD_REGISTRY}/${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}"
    podman push "${GOLD_REGISTRY}/${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}"

    oc tag "${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}" "${TARGET_NS}/${TARGET_IS}:${FULL_TAG}"
    oc set image "deployment/${TARGET_DEPLOY}" rtrans-esb="${INTERNAL_REGISTRY}/${TARGET_NS}/${TARGET_IS}:${FULL_TAG}" -n "${TARGET_NS}"

    echo "=== Gold Prod done. Now switching to Gold DR ==="
    printf "Press Enter to log in to Gold DR..."
    read _

    echo "Log in to Gold DR:"
    echo "  1. Visit: https://oauth-openshift.apps.golddr.devops.gov.bc.ca/oauth/token/request"
    echo "  2. Copy the 'oc login --token=...' command"
    echo "  3. Paste it here and press Enter"
    read -r OC_LOGIN_CMD
    eval "${OC_LOGIN_CMD}"

    oc whoami -t | podman login "${GOLDDR_REGISTRY}" -u "$(oc whoami)" --password-stdin

    podman tag "${IMAGE_NAME}:latest" "${GOLDDR_REGISTRY}/${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}"
    podman push "${GOLDDR_REGISTRY}/${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}"

    oc tag "${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}" "${TARGET_NS}/${TARGET_IS}:${FULL_TAG}"
    oc set image "deployment/${TARGET_DEPLOY}" rtrans-esb="${INTERNAL_REGISTRY}/${TARGET_NS}/${TARGET_IS}:${FULL_TAG}" -n "${TARGET_NS}"

    echo "=== Gold DR done ==="
    ;;

  gold-dr)
    echo "=== Deploying to Gold DR only ==="
    echo "This pushes the local image to Gold DR and deploys. Use if Gold was already done."
    oc whoami || { echo "Not logged in. Run: oc login --server=https://api.golddr.devops.gov.bc.ca:6443"; exit 1; }
    oc whoami -t | podman login "${GOLDDR_REGISTRY}" -u "$(oc whoami)" --password-stdin

    podman tag "${IMAGE_NAME}:latest" "${GOLDDR_REGISTRY}/${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}"
    podman push "${GOLDDR_REGISTRY}/${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}"

    oc tag "${TOOLS_NS}/${IMAGE_NAME}:${FULL_TAG}" "${TARGET_NS}/${TARGET_IS}:${FULL_TAG}"
    oc set image "deployment/${TARGET_DEPLOY}" rtrans-esb="${INTERNAL_REGISTRY}/${TARGET_NS}/${TARGET_IS}:${FULL_TAG}" -n "${TARGET_NS}"

    echo "=== Gold DR done ==="
    ;;
esac

echo ""
echo "=== Deploy complete: ${FULL_TAG} ==="
