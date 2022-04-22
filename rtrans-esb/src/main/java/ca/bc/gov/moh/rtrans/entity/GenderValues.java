package ca.bc.gov.moh.rtrans.entity;

/**
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:32 PM
 */
public enum GenderValues {

    Female,
    Male,
    NotUniquelyIdentified,
    Unknown;
    
    public String value() {
        return name();
    }
}//end GenderValues