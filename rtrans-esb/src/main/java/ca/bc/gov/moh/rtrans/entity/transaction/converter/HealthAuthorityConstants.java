/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

/**
 *
 * @author Dan.Stepanov
 */
public class HealthAuthorityConstants {
    
    /**
     * homeless postal code value
     */
    public static final String HOMELESS_POSTAL_CODE = "V6Y 2A1";
    /**
     * status code active value
     */
    public static final String STATUS_CODE_ACTIVE = "active";
    /*
    * Constants for HCIM_IN_GetDemographicsResponse in GD response message
    */
    public static final String HCIM_IN_GET_DEMO_RESPONSE = "HCIM_IN_GetDemographicsResponse";
    /*
    * Constants for GetEligibility in GE response message
    */
    public static final String QUCR_IN_210101BC = "QUCR_IN210101BC";
    /*
    * Constants for STATUS_CODE='Aborted' in GE response message
    */
    public static final String STATUS_CODE_ABORTED = "Aborted";
    /*
    * Constants for has eligibility='NO' flag in GE response message
    */
    public static final String HAS_ELIGIB_NO_FLAG = "NO";
    /*
    * Constants for has eligibility='YES' flag in GE response message
    */
    public static final String HAS_ELIGIB_YES_FLAG = "YES";
    /*
    * Constants for has eligibility='UNKNOWN' flag in GE response message
    */
    public static final String HAS_ELIGIB_UNKNOWN_FLAG = "UNKNOWN";
    /*
    * Constants for queryResponseCode code='NF' flag in GE response message
    */
    public static final String QUERY_RESP_CODE_NOT_FOUND = "NF";
    /*
    * Constants used in HapiEntityConverterV23/HapiEntityConverterV24 classes
    */    
    public static final String NAME_TYPE_CODE_V2_CURRENT = "Current";
    public static final String CONFIDENTIAL_MASK_LONG = "confidential";
    public static final String CONFIDENTIAL_MASK_SHORT = "*";
    public static final String NAME_TYPE_CODE_PREFERRED = "P";
    public static final String NAME_TYPE_CODE_LEGAL = "L";
    public static final String ADRESS_NAME_PHYS_PST_TYPES = "PHYS PST";
    public static final String BC_PHN = "BC Health Care Number";
    public static final String NATIONAL_HEALTH_NUMBER = "National Health Number";
    public static final String STATE_HEALTH_NUMBER = "State Health Number";
    public static final String AB_PHN = "ABPHN";
    public static final String PEI_HEALTH_NUMBER = "PEI Healthcare Number";
    public static final String PEI_PHN = "PEPHN";
    public static final String QUEBEC_HEALTH_NUMBER = "Quebec Healthcare Number";
    public static final String QC_PHN = "QCPHN";
    public static final String SASKATCHEWAN_HEALTH_NUMBER = "Saskatchewan Health Services Number";
    public static final String SK_PHN = "SKPHN";
    public static final String YUKON_HEALTH_NUMBER = "Yukon Territory Healthcare Number";
    public static final String YT_PHN = "YTPHN";
    public static final String ALBERTA_HEALTH_NUMBER = "Alberta Health Unique Lifetime Identifier PHN";
    public static final String AHULI_PHN = "AHULI";
    public static final String CANADIAN_FORCES_HEALTH_NUMBER = "Canadian Armed Forces Identification Number";
    public static final String CACF_PHN = "CACF";
    public static final String CANADIAN_RCMP_HEALTH_NUMBER = "Canadian RCMP Regiment Number";
    public static final String RCMP_PHN = "RCMP";
    public static final String CANADIAN_VETERAN_HEALTH_NUMBER = "Veteran Affairs Canadian Identification Number";
    public static final String VAC_PHN = "VAC";
    public static final String MANITOBA_HEALTH_NUMBER = "Manitoba Health Registration";
    public static final String MB_PHN = "MBPHN";
    public static final String NEW_BRUNSWICK_HEALTH_NUMBER = "New Brunswick Medicare";
    public static final String NB_PHN = "NBPHN";    
    public static final String NEWFOUNDLAND_HEALTH_NUMBER = "Newfoundland Labrador Healthcare number";
    public static final String NF_PHN = "NFPHN"; 
    public static final String NOVA_SCOTIA_HEALTH_NUMBER = "Nova Scotia PHN";
    public static final String NS_PHN = "NSPHN"; 
    public static final String NORTHWEST_TERRITORY_HEALTH_NUMBER = "Northwest territories PHN";
    public static final String NT_PHN = "NTPHN";    
    public static final String NUNAVUT_HEALTH_NUMBER = "Nunavut Healthcare Number";
    public static final String NU_PHN = "NUPHN"; 
    public static final String ONTARIO_HEALTH_NUMBER = "Ontario Healthcare Number";
    public static final String ON_PHN = "ONPHN";
    public static final String DEFAULT_ALT_ID = "DEFAULT_ID";
    public static final String BCPHN_ID = "BCPHN";
    public static final String MOH_CRS_ID = "MOH_CRS";
    public static final String PHSA_P_ID = "PHSA_P";
    public static final String NHN_ID = "NHN";
    /*
    * Constants to identify OIDs for each alternate ids
    */ 
    public static final String ABPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.2";
    public static final String PEPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.10";
    public static final String QCPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.11";
    public static final String SKPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.12";
    public static final String YTPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.13";
    public static final String AHULI_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.14";
    public static final String CACF_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.15";
    public static final String RCMP_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.16";
    public static final String VAC_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.18";
    public static final String MBPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.3";
    public static final String NBPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.4";
    public static final String NFPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.5";
    public static final String NSPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.6";
    public static final String NTPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.7";
    public static final String NUPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.8";
    public static final String ONPHN_REGISTRATION_IDENTIFIER_ROOT = "2.16.840.1.113883.3.51.1.1.6.9";
    public static final String BCPHN_OID = "2.16.840.1.113883.3.51.1.1.6.1";
    public static final String MESSAGE_ID_OID = "2.16.840.1.113883.3.51.1.1.1";
    public static final String MRN_OID = "2.16.840.1.113883.3.51.1.1.6";
    public static final String NBPHN_OID = "NBPHN";
    public static final String MRN_ID = "MRN";
    public static final String PHN_ID = "PHN";
    
    //R Transactions
    public static final String UNIT_RD = "RD";
    public static final String MOH = "MOH";
    public static final String BC = "BC";
    public static final String PH = "PH";
    
}