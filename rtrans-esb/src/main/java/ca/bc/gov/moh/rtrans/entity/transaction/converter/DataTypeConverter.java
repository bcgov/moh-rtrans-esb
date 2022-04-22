/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import static ca.bc.gov.moh.rtrans.entity.AddressTypes.Home;
import static ca.bc.gov.moh.rtrans.entity.AddressTypes.Mail;
import static ca.bc.gov.moh.rtrans.entity.AddressTypes.Work;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.GenderAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderValues;
import static ca.bc.gov.moh.rtrans.entity.GenderValues.Female;
import static ca.bc.gov.moh.rtrans.entity.GenderValues.Male;
import static ca.bc.gov.moh.rtrans.entity.GenderValues.NotUniquelyIdentified;
import static ca.bc.gov.moh.rtrans.entity.GenderValues.Unknown;
import ca.bc.gov.moh.rtrans.entity.PersonNameTypes;
import static ca.bc.gov.moh.rtrans.entity.PersonNameTypes.Declared;
import static ca.bc.gov.moh.rtrans.entity.PersonNameTypes.Documented;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.datatype.IS;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hl7.v3.TS;

/**
 *
 * @author conrad.gustafson
 */
public class DataTypeConverter {

    public static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YEAR_MONTH_DAY_HOUR_FORMAT = new SimpleDateFormat("yyyyMMddHH");
    public static final SimpleDateFormat YEAR_MONTH_DAY_HOUR_MINUTE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss.S");

    public static final List<SimpleDateFormat> dateParsers = new ArrayList<>();

    static {

        dateParsers.add(YEAR_MONTH_DAY_FORMAT);
        dateParsers.add(YEAR_MONTH_DAY_HOUR_FORMAT);
        dateParsers.add(YEAR_MONTH_DAY_HOUR_MINUTE_FORMAT);
        dateParsers.add(DATE_TIME_FORMAT);
        dateParsers.add(FULL_DATE_TIME_FORMAT);
    }

    /**
     * By Default SimpleDateFormat objects use Lenient parsing, which will
     * convert invalid dates (such as 2000-01-00) into valid ones (1999-12-31).
     * Turn off lenient mode to enforce strict parsing
     */
    static {
        YEAR_MONTH_DAY_FORMAT.setLenient(false);
        YEAR_MONTH_DAY_HOUR_FORMAT.setLenient(false);
        YEAR_MONTH_DAY_HOUR_MINUTE_FORMAT.setLenient(false);
        DATE_TIME_FORMAT.setLenient(false);
        FULL_DATE_TIME_FORMAT.setLenient(false);
    }

    public static final String GENDER_FEMALE_V2 = "F";
    public static final String GENDER_MALE_V2 = "M";
    public static final String GENDER_UNKNOWN_V2 = "U";

    public static final String PERSON_NAME_TYPE_LEGAL_DOCUMENTED = "C";
    public static final String PERSON_NAME_TYPE_LEGAL_DECLARED = "L";
    public static final String COMMUNICATION_TYPE_HOME_V2 = "PH";
    public static final String COMMUNICATION_TYPE_HOME_V3 = "H";

    public static final String ADDRESS_TYPE_MAILING = "PST";
    public static final String ADDRESS_TYPE_PHYSICAL = "PHYS";
    private static final String DEFAULT_REGION_CANADA = "CA";
    
    public static final String ADDRESS_TYPE_V2_HOME = "H";
    public static final String ADDRESS_TYPE_V2_MAIL = "M";
    public static final String ADDRESS_IS_VALID = "Y";
    public static final String ADDRESS_IS_NOT_VALID = "N";
    
    public static String convertIsAddressValid(boolean isValid) {
        if (isValid) {
            return ADDRESS_IS_VALID;
        }
        return ADDRESS_IS_NOT_VALID;
        
    }

    /**
     * Converts a String to a Date 
     * @param value String value to parse
     * @return Date - the parsed Date
     */
    public static Date convertToDate(String value){
         Date dateValue = null;
         
         if (value == null) {
             return dateValue;
         }
         //Determine which parser to use based on the string's length
         int index = (value.length() - 8) / 2;
         
         if(index < 0){
             throw new RuntimeException("The date format for "+value+" is not accepted");
         }
         
         if (index>=dateParsers.size()){
             index = dateParsers.size()-1;
             //RPD messages have 4 decimal places for seconds, so trim off the last digit.
             if (value.length()>18){
                value = value.substring(0,18);
             }
         }
        try {
            //SimpleDateFormat is not threadsafe, so we need to synchronize on the parser object
            //otherwise we'll get strange behavior under load
            SimpleDateFormat notThreadSafe = dateParsers.get(index);
            synchronized (notThreadSafe) {
                dateValue = notThreadSafe.parse(value);
            }
        } catch (ParseException pe) {
            //pe.printStackTrace();
            throw new RuntimeException(pe);
        }
        return dateValue;
    }
    
    public static Date convertToDate(TS ts) {
       return convertToDate(ts.getValue());
    }

    public static String convertToTS(Date creationTime) {
        synchronized (DATE_TIME_FORMAT) {
            return DATE_TIME_FORMAT.format(creationTime);
        }
    }

    /**
     * 
     * @param birthTime
     * @return BirthTime as string or empty string in the case of a null birthTime
     */
    public static String convertToDateTS(Date birthTime) {
        if(birthTime != null){
            synchronized (YEAR_MONTH_DAY_FORMAT) {
                return YEAR_MONTH_DAY_FORMAT.format(birthTime);
            }
        }
        else{
           return ""; 
        }
    }

    public static String convertGenderValueToV2(GenderAttribute genderAttribute) {
        if (genderAttribute == null) {
            return null;
        }
        if (genderAttribute.getMasked()) {
            return GENDER_UNKNOWN_V2;
        }
        switch (genderAttribute.getValue()) {
            case Male:
                return GENDER_MALE_V2;
            case Female:
                return GENDER_FEMALE_V2;
            case NotUniquelyIdentified:
                return GENDER_UNKNOWN_V2;
            case Unknown:
                return GENDER_UNKNOWN_V2;
            default:
                return null;
        }
    }

    public static GenderAttribute convertV2ValueToGender(IS v2Value) {
        if (v2Value == null) {
            return null;
        }
        String value;
        try {
            value = v2Value.encode();
        } catch (HL7Exception ex) {
            throw new IllegalStateException(ex);
        }
        if (value == null) {
            return null;
        }
        switch (value) {
            case GENDER_MALE_V2:
                return new GenderAttribute(GenderValues.Male);
            case GENDER_FEMALE_V2:
                return new GenderAttribute(GenderValues.Female);
            case GENDER_UNKNOWN_V2:
                return new GenderAttribute(GenderValues.Unknown);
            default:
                return null;
        }
    }

    public static String convertPersonNameTypeValue(PersonNameTypes personNameTypeValue) {

        if (personNameTypeValue == null) {
            return null;
        }
        switch (personNameTypeValue) {
            case Documented:
                return PERSON_NAME_TYPE_LEGAL_DOCUMENTED;
            case Declared:
                return PERSON_NAME_TYPE_LEGAL_DECLARED;
            default:
                return null;
        }

    }

    public static String convertCommunicationTypeToV2(CommunicationTypes communicationType) {
        
        String commType = null;
        
        if (communicationType != null && communicationType.equals(CommunicationTypes.Home)) {
            commType = COMMUNICATION_TYPE_HOME_V2;
        }      
        return commType;     
    }
    public static String convertCommunicationTypeToV3(CommunicationTypes communicationType) {
        
        String commType = null;
        
        if (communicationType.equals(CommunicationTypes.Home)) {
            commType = COMMUNICATION_TYPE_HOME_V3;
        }        
        return commType;      
    }
    
    /**
     * Safely truncates a string returning what was entered if the string is less than the given test length or null
     * @param long_string
     * @param length the desired length for the string
     * @return 
     */
    public static String safeTruncate(String longString, int length){
        return (longString != null && longString.length() > length) ? 
                longString.substring(0,length) : longString;
    }
    
    /**
     * Takes a family name and, if it is not null and longer than 35 characters, truncates it to 35 characters
     * @param familyName
     * @return 
     */
    public static String convertFamilyNameToV2(String familyName){
        return safeTruncate(familyName, 35);
    }
    
    /**
     * Fix for DEFECT CRSHL-388
     * Truncates a string to 15 characters, or returns the given if under fifteen characters or null
     * @param personName name to truncate
     * @return truncated name
     */
    public static String convertGivenNameToV2(String personName){
        return safeTruncate(personName, 15);
    }
    
    /**
     * Truncate Address Line of City to 35 characters
     * @param address
     * @return 
     */
    public static String clipAddressLineOrCityForV2(String address){
        return safeTruncate(address, 25);
    }

    public static String convertAddressTypeValue(AddressTypes type) {

        if (type == null) {
            return null;
        }

        switch (type) {
            case Mail:
                return ADDRESS_TYPE_MAILING;
            case Home:
                return ADDRESS_TYPE_PHYSICAL;
            case Work:
                return null;
            default:
                return null;
        }
    }

    public static String convertAddressTypeValueToV2(AddressTypes type) {

        if (type == null) {
            return null;
        }

        switch (type) {
            case Mail:
                return ADDRESS_TYPE_V2_MAIL;
            case Home:
                return ADDRESS_TYPE_V2_HOME;
            case Work:
                return null;
            default:
                return null;
        }
    }

    public static Date convertStringToDate(String strValue) {
        return convertToDate(strValue);
    }
}
