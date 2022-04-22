package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This stores address information for a physical location
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:31 PM
 */
public class AddressAttribute extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private String city;
    private String country;
    private Boolean isVerified;
    private String postalCode;
    private String province;
    private List<String> streetAddressLines = new ArrayList<>();
    private AddressTypes type;
    
    public AddressAttribute() {

    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
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
     * @return the isVerified
     */
    public Boolean getIsVerified() {
        return isVerified;
    }

    /**
     * @param isVerified the isVerified to set
     */
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the streetAddressLines
     */
    public List<String> getStreetAddressLines() {
        return streetAddressLines;
    }

    /**
     * @param streetAddressLines the streetAddressLines to set
     */
    public void setStreetAddressLines(List<String> streetAddressLines) {
        this.streetAddressLines = streetAddressLines;
    }

    /**
     * @return the type
     */
    public AddressTypes getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    @Override
    public void setType(AddressTypes type) {
        this.type = type;
    }

    public static final String COUNTRY_CODE_CANADA = "CA";
    public static final String COUNTRY_CODE_US = "US";
    
    public boolean isCanada() {
        return COUNTRY_CODE_CANADA.equalsIgnoreCase(country);
    }

    public boolean isUS() {
        return COUNTRY_CODE_US.equalsIgnoreCase(country);
    }
}//end AddressAttribute
