package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * Used to store a boolean indicator or flag
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:31 PM
 */
public class BooleanAttribute extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private Boolean value;

    public BooleanAttribute() {

    }

    public BooleanAttribute(Boolean val) {
        this.value = val;

    }

    /**
     * @return the Value
     */
    public Boolean getValue() {
        return value;
    }

    /**
     * @param Value the Value to set
     */
    public void setValue(Boolean value) {
        this.value = value;
    }
}//end BooleanAttribute
