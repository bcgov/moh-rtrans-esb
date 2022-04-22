package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * Information about who, what, and where information is to be communicated with
 * another system.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:31 PM
 */
public class CommunicationFunction implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private String organization;
    private String systemName;

    public CommunicationFunction() {

    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the systemName
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * @param systemName the systemName to set
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}//end CommunicationFunction
