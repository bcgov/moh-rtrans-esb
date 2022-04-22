package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Confidentiality mask attribute. Used by the EMPI for storing masked
 * attributes.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:31 PM
 */
public class ConfidentialityMaskAttribute extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private List<Attribute> maskedAttributeTypes;

    public ConfidentialityMaskAttribute() {

    }

    /**
     * @return the maskedAttributeTypes
     */
    public List<Attribute> getMaskedAttributeTypes() {
        return maskedAttributeTypes;
    }

    /**
     * @param MaskedAttributeTypes the maskedAttributeTypes to set
     */
    public void setMaskedAttributeTypes(List<Attribute> MaskedAttributeTypes) {
        this.maskedAttributeTypes = MaskedAttributeTypes;
    }
}//end ConfidentialityMaskAttribute
