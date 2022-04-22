package ca.bc.gov.moh.rtrans.entity;

import ca.bc.gov.moh.rtrans.entity.transaction.converter.HealthAuthorityConstants;

/**
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:33 PM
 */
public enum IdentifierTypes {
    /**
     * Source Record Identifier
     */
    SRI,
    /**
     * British Columbia PHN
     */
    BCPHN,
    /**
     * Other provinces PHNs
     */
    PEPHN,
    QCPHN,
    SKPHN,
    YKPHN,
    AHULI,
    CACF,
    RCMP,
    VAC,
    ABPHN,
    MBPHN,
    NBPHN,
    NFPHN,
    NUPHN,
    ONPHN,
    YTPHN,
    NSPHN,
    NTPHN,
    HDID,
    HDID_LVL_1,
    HDID_LVL_2,
    HDID_LVL_3,
    HDID_LVL_4,
    USERID,
    EID,
    SSRI,
    MEMRECNO,
    //MRN
    MRN,
    //message id
    MSGID,
    //interactionID
    INTERID,
    //default id
    DEFAULT_ID;

    public static IdentifierTypes fromOIDtoType(String oid) {

        if (oid==null){
            return null;
        }
        
        switch (oid) {
            case HealthAuthorityConstants.BCPHN_OID:
                return BCPHN;
            case HealthAuthorityConstants.MESSAGE_ID_OID:
                return MSGID;
            case HealthAuthorityConstants.MRN_OID:
                return MRN;
            case HealthAuthorityConstants.NUPHN_REGISTRATION_IDENTIFIER_ROOT:
                return NUPHN;
            case HealthAuthorityConstants.ABPHN_REGISTRATION_IDENTIFIER_ROOT:
                return ABPHN;
            case HealthAuthorityConstants.PEPHN_REGISTRATION_IDENTIFIER_ROOT:
                return PEPHN;
            case HealthAuthorityConstants.QCPHN_REGISTRATION_IDENTIFIER_ROOT:
                return QCPHN;
            case HealthAuthorityConstants.SKPHN_REGISTRATION_IDENTIFIER_ROOT:
                return SKPHN;
            case HealthAuthorityConstants.YTPHN_REGISTRATION_IDENTIFIER_ROOT:
                return YTPHN;
            case HealthAuthorityConstants.AHULI_REGISTRATION_IDENTIFIER_ROOT:
                return AHULI;
            case HealthAuthorityConstants.CACF_REGISTRATION_IDENTIFIER_ROOT:
                return CACF;
            case HealthAuthorityConstants.RCMP_REGISTRATION_IDENTIFIER_ROOT:
                return RCMP;
            case HealthAuthorityConstants.VAC_REGISTRATION_IDENTIFIER_ROOT:
                return VAC;
            case HealthAuthorityConstants.MBPHN_REGISTRATION_IDENTIFIER_ROOT:
                return MBPHN;
            case HealthAuthorityConstants.NBPHN_REGISTRATION_IDENTIFIER_ROOT:
                return NBPHN;
            case HealthAuthorityConstants.NFPHN_REGISTRATION_IDENTIFIER_ROOT:
                return NFPHN;
            case HealthAuthorityConstants.NSPHN_REGISTRATION_IDENTIFIER_ROOT:
                return NSPHN;
            case HealthAuthorityConstants.NTPHN_REGISTRATION_IDENTIFIER_ROOT:
                return NTPHN;
            case HealthAuthorityConstants.ONPHN_REGISTRATION_IDENTIFIER_ROOT:
                return ONPHN;     
            default:
                break;
        }
        return null;
    }

    public static IdentifierTypes fromValueToType(String encode) {

        if (encode.equals(HealthAuthorityConstants.MRN_ID)) {
            return MRN;
        }
        if (encode.equalsIgnoreCase(HealthAuthorityConstants.NHN_ID)) {
            return BCPHN;
        }
        if (encode.equalsIgnoreCase(HealthAuthorityConstants.PHN_ID)) {
            return BCPHN;
        }
        if (encode.trim().equalsIgnoreCase("PH")) {
            return BCPHN;
        }
        return IdentifierTypes.valueOf(encode);
    }

    public static String fromTypeToOID(IdentifierTypes type) {

        if (type==null){
            return null;
        }
        
        switch (type) {
            case BCPHN:
                return HealthAuthorityConstants.BCPHN_OID;
            case MSGID:
                return HealthAuthorityConstants.MESSAGE_ID_OID;
            case MRN:
                return HealthAuthorityConstants.MRN_OID;
            case NUPHN:
                return HealthAuthorityConstants.NUPHN_REGISTRATION_IDENTIFIER_ROOT;
            case ABPHN:
                return HealthAuthorityConstants.ABPHN_REGISTRATION_IDENTIFIER_ROOT;
            case PEPHN:
                return HealthAuthorityConstants.PEPHN_REGISTRATION_IDENTIFIER_ROOT;
            case QCPHN:
                return HealthAuthorityConstants.QCPHN_REGISTRATION_IDENTIFIER_ROOT;
            case SKPHN:
                return HealthAuthorityConstants.SKPHN_REGISTRATION_IDENTIFIER_ROOT;
            case YTPHN:
                return HealthAuthorityConstants.YTPHN_REGISTRATION_IDENTIFIER_ROOT;
            case AHULI:
                return HealthAuthorityConstants.AHULI_REGISTRATION_IDENTIFIER_ROOT;
            case CACF:
                return HealthAuthorityConstants.CACF_REGISTRATION_IDENTIFIER_ROOT;
            case RCMP:
                return HealthAuthorityConstants.RCMP_REGISTRATION_IDENTIFIER_ROOT;
            case VAC:
                return HealthAuthorityConstants.VAC_REGISTRATION_IDENTIFIER_ROOT;
            case MBPHN:
                return HealthAuthorityConstants.MBPHN_REGISTRATION_IDENTIFIER_ROOT;
            case NBPHN:
                return HealthAuthorityConstants.NBPHN_REGISTRATION_IDENTIFIER_ROOT;
            case NFPHN:
                return HealthAuthorityConstants.NFPHN_REGISTRATION_IDENTIFIER_ROOT;
            case NSPHN:
                return HealthAuthorityConstants.NSPHN_REGISTRATION_IDENTIFIER_ROOT;
            case NTPHN:
                return HealthAuthorityConstants.NTPHN_REGISTRATION_IDENTIFIER_ROOT;
            case ONPHN:
                return HealthAuthorityConstants.ONPHN_REGISTRATION_IDENTIFIER_ROOT;     
            default:
                break;
        }
        return null;
    }
    
    public static boolean contains(String s) {
        for(IdentifierTypes type:values())
            if (type.name().equals(s)) 
                return true;
        return false;
    } 
}
