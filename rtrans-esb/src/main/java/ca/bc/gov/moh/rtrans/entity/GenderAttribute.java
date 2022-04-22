package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * A persons gender
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:32 PM
 */
public class GenderAttribute extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private GenderValues value;

    public GenderAttribute() {

    }

    public GenderAttribute(GenderValues val) {
        this.value = val;

    }

    /**
     * @return the value
     */
    public GenderValues getValue() {
        return value;
    }

    /**
     * @param Value the value to set
     */
    public void setValue(GenderValues Value) {
        this.value = Value;
    }
}//end GenderAttribute
