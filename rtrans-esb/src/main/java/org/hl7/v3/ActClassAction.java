
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActClassAction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActClassAction">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ACCM"/>
 *     &lt;enumeration value="ACCT"/>
 *     &lt;enumeration value="ACSN"/>
 *     &lt;enumeration value="ACT"/>
 *     &lt;enumeration value="ACTN"/>
 *     &lt;enumeration value="ADJUD"/>
 *     &lt;enumeration value="AEXPOS"/>
 *     &lt;enumeration value="ALRT"/>
 *     &lt;enumeration value="BATTERY"/>
 *     &lt;enumeration value="CACT"/>
 *     &lt;enumeration value="CASE"/>
 *     &lt;enumeration value="CATEGORY"/>
 *     &lt;enumeration value="CDALVLONE"/>
 *     &lt;enumeration value="CLNTRL"/>
 *     &lt;enumeration value="CLUSTER"/>
 *     &lt;enumeration value="CNOD"/>
 *     &lt;enumeration value="CNTRCT"/>
 *     &lt;enumeration value="COMPOSITION"/>
 *     &lt;enumeration value="COND"/>
 *     &lt;enumeration value="CONS"/>
 *     &lt;enumeration value="CONTAINER"/>
 *     &lt;enumeration value="CONTREG"/>
 *     &lt;enumeration value="COV"/>
 *     &lt;enumeration value="CTTEVENT"/>
 *     &lt;enumeration value="DETPOL"/>
 *     &lt;enumeration value="DGIMG"/>
 *     &lt;enumeration value="DIET"/>
 *     &lt;enumeration value="DISPACT"/>
 *     &lt;enumeration value="DOC"/>
 *     &lt;enumeration value="DOCBODY"/>
 *     &lt;enumeration value="DOCCLIN"/>
 *     &lt;enumeration value="DOCSECT"/>
 *     &lt;enumeration value="EHR"/>
 *     &lt;enumeration value="ENC"/>
 *     &lt;enumeration value="EXP"/>
 *     &lt;enumeration value="EXPOS"/>
 *     &lt;enumeration value="EXTRACT"/>
 *     &lt;enumeration value="FCNTRCT"/>
 *     &lt;enumeration value="FOLDER"/>
 *     &lt;enumeration value="GEN"/>
 *     &lt;enumeration value="GROUPER"/>
 *     &lt;enumeration value="INC"/>
 *     &lt;enumeration value="INFO"/>
 *     &lt;enumeration value="INFRM"/>
 *     &lt;enumeration value="INVE"/>
 *     &lt;enumeration value="INVSTG"/>
 *     &lt;enumeration value="JURISPOL"/>
 *     &lt;enumeration value="LIST"/>
 *     &lt;enumeration value="LLD"/>
 *     &lt;enumeration value="LOC"/>
 *     &lt;enumeration value="MPROT"/>
 *     &lt;enumeration value="OBS"/>
 *     &lt;enumeration value="OBSCOR"/>
 *     &lt;enumeration value="OBSSER"/>
 *     &lt;enumeration value="ORGPOL"/>
 *     &lt;enumeration value="OUTB"/>
 *     &lt;enumeration value="PCPR"/>
 *     &lt;enumeration value="PHN"/>
 *     &lt;enumeration value="POL"/>
 *     &lt;enumeration value="POLICY"/>
 *     &lt;enumeration value="POS"/>
 *     &lt;enumeration value="POSACC"/>
 *     &lt;enumeration value="POSCOORD"/>
 *     &lt;enumeration value="PRN"/>
 *     &lt;enumeration value="PROC"/>
 *     &lt;enumeration value="REG"/>
 *     &lt;enumeration value="REV"/>
 *     &lt;enumeration value="RLD"/>
 *     &lt;enumeration value="ROIBND"/>
 *     &lt;enumeration value="ROIOVL"/>
 *     &lt;enumeration value="RTRD"/>
 *     &lt;enumeration value="SBADM"/>
 *     &lt;enumeration value="SCOPOL"/>
 *     &lt;enumeration value="SEQ"/>
 *     &lt;enumeration value="SEQVAR"/>
 *     &lt;enumeration value="SFWL"/>
 *     &lt;enumeration value="SIT"/>
 *     &lt;enumeration value="SPCOBS"/>
 *     &lt;enumeration value="SPCTRT"/>
 *     &lt;enumeration value="SPECCOLLECT"/>
 *     &lt;enumeration value="SPLY"/>
 *     &lt;enumeration value="STC"/>
 *     &lt;enumeration value="STDPOL"/>
 *     &lt;enumeration value="STN"/>
 *     &lt;enumeration value="STORE"/>
 *     &lt;enumeration value="SUBST"/>
 *     &lt;enumeration value="SUP"/>
 *     &lt;enumeration value="TEXPOS"/>
 *     &lt;enumeration value="TOPIC"/>
 *     &lt;enumeration value="TRD"/>
 *     &lt;enumeration value="TRFR"/>
 *     &lt;enumeration value="TRNS"/>
 *     &lt;enumeration value="VERIF"/>
 *     &lt;enumeration value="XACT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActClassAction")
@XmlEnum
public enum ActClassAction {

    ACCM,
    ACCT,
    ACSN,
    ACT,
    ACTN,
    ADJUD,
    AEXPOS,
    ALRT,
    BATTERY,
    CACT,
    CASE,
    CATEGORY,
    CDALVLONE,
    CLNTRL,
    CLUSTER,
    CNOD,
    CNTRCT,
    COMPOSITION,
    COND,
    CONS,
    CONTAINER,
    CONTREG,
    COV,
    CTTEVENT,
    DETPOL,
    DGIMG,
    DIET,
    DISPACT,
    DOC,
    DOCBODY,
    DOCCLIN,
    DOCSECT,
    EHR,
    ENC,
    EXP,
    EXPOS,
    EXTRACT,
    FCNTRCT,
    FOLDER,
    GEN,
    GROUPER,
    INC,
    INFO,
    INFRM,
    INVE,
    INVSTG,
    JURISPOL,
    LIST,
    LLD,
    LOC,
    MPROT,
    OBS,
    OBSCOR,
    OBSSER,
    ORGPOL,
    OUTB,
    PCPR,
    PHN,
    POL,
    POLICY,
    POS,
    POSACC,
    POSCOORD,
    PRN,
    PROC,
    REG,
    REV,
    RLD,
    ROIBND,
    ROIOVL,
    RTRD,
    SBADM,
    SCOPOL,
    SEQ,
    SEQVAR,
    SFWL,
    SIT,
    SPCOBS,
    SPCTRT,
    SPECCOLLECT,
    SPLY,
    STC,
    STDPOL,
    STN,
    STORE,
    SUBST,
    SUP,
    TEXPOS,
    TOPIC,
    TRD,
    TRFR,
    TRNS,
    VERIF,
    XACT;

    public String value() {
        return name();
    }

    public static ActClassAction fromValue(String v) {
        return valueOf(v);
    }

}
