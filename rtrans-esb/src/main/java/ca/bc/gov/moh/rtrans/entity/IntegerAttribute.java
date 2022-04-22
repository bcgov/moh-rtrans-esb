package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * Used to store a # (not an identifier, see IdentifierAttribute).
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:33 PM
 */
public class IntegerAttribute extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private int value;

    public IntegerAttribute() {

    }

    public IntegerAttribute(int v) {
        this.value = v;

    }

    /**
     * @return the Value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param Value the Value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
}//end IntegerAttribute
