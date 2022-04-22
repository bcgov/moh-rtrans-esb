package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * An email address
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:32 PM
 */
public class EmailAttribute extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private String address;
    private CommunicationTypes type;

    public EmailAttribute() {

    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
}//end EmailAttribute
