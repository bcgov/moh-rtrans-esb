package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * Represents contact information for a person via a telecommunications device
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public class PhoneAttribute extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private String areaCode;
    private String country;
    private String extension;
    private String number;
    private CommunicationTypes type;
    private String rawValue;

    public PhoneAttribute() {

    }

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode the areaCode to set
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the type
     */
    public CommunicationTypes getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CommunicationTypes type) {
        this.type = type;
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

}//end PhoneAttribute
