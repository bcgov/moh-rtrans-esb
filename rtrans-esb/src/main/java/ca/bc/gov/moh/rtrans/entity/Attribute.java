package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * This is the base class or all data fields that are stored on a client record.
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:31 PM
 */
public abstract class Attribute implements Serializable{

	private Date businessCreatedDateTime;
	private Date businessLastModifiedDateTime;
	private boolean masked = false;
	private AttributeStatus status;
	private Date systemCreatedDateTime;
	private Date systemLastModifiedDateTime;
	private Enum type;

	public Attribute(){

	}

    /**
     * @return the businessCreatedDateTime
     */
    public Date getBusinessCreatedDateTime() {
        return businessCreatedDateTime;
    }

    /**
     * @param businessCreatedDateTime the businessCreatedDateTime to set
     */
    public void setBusinessCreatedDateTime(Date businessCreatedDateTime) {
        this.businessCreatedDateTime = businessCreatedDateTime;
    }

    /**
     * @return the businessLastModifiedDateTime
     */
    public Date getBusinessLastModifiedDateTime() {
        return businessLastModifiedDateTime;
    }

    /**
     * @param businessLastModifiedDateTime the businessLastModifiedDateTime to set
     */
    public void setBusinessLastModifiedDateTime(Date businessLastModifiedDateTime) {
        this.businessLastModifiedDateTime = businessLastModifiedDateTime;
    }

    /**
     * @return the masked
     */
    public Boolean getMasked() {
        return masked;
    }

    /**
     * @param masked the masked to set
     */
    public void setMasked(Boolean masked) {
        this.masked = masked;
    }

    /**
     * @return the status
     */
    public AttributeStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(AttributeStatus status) {
        this.status = status;
    }

    /**
     * @return the systemCreatedDateTime
     */
    public Date getSystemCreatedDateTime() {
        return systemCreatedDateTime;
    }

    /**
     * @param systemCreatedDateTime the systemCreatedDateTime to set
     */
    public void setSystemCreatedDateTime(Date systemCreatedDateTime) {
        this.systemCreatedDateTime = systemCreatedDateTime;
    }

    /**
     * @return the systemLastModifiedDateTime
     */
    public Date getSystemLastModifiedDateTime() {
        return systemLastModifiedDateTime;
    }

    /**
     * @param systemLastModifiedDateTime the systemLastModifiedDateTime to set
     */
    public void setSystemLastModifiedDateTime(Date systemLastModifiedDateTime) {
        this.systemLastModifiedDateTime = systemLastModifiedDateTime;
    }

    /**
     * @return the type
     */
    public Enum getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(AddressTypes type) {
        this.type = type;
    }
}//end Attribute