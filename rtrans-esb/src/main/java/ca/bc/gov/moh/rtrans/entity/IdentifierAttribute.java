package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Stores an identifier that can be used to reference or look up a record.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:32 PM
 */
public class IdentifierAttribute extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private Date expireyDate;
    private String source;
    private String value;
    public IdentifierTypes type;

    public IdentifierAttribute() {

    }

    public IdentifierAttribute(String val, String src, IdentifierTypes tp) {
        this.source = src;
        this.value = val;
        this.type = tp;
    }

    public Date getDxpireyDate() {
        return this.expireyDate;
    }

    public void setDxpireyDate(Date value) {
        this.expireyDate = value;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public IdentifierTypes getType() {
        return this.type;
    }

    public void setType(IdentifierTypes value) {
        this.type = value;
    }

    public boolean isBCPHN() {
        return IdentifierTypes.BCPHN.equals(getType());
    }
    
    public boolean isMRN() {
        return IdentifierTypes.MRN.equals(getType());
    }

}//end IdentifierAttribute
