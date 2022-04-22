/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.entity.GenderAttribute;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.EntityToJaxbTypeConverter;
import org.hl7.v3.CE;
import org.hl7.v3.NullFlavor;

/**
 *
 * @author patrick.weckermann
 * 
 * Non Query includes add/update/merge/distributions. Query transactions and non-query transactions typically have different mappings and defaults.
 */
public class NonQueryEntityToJaxbTypeConverter extends EntityToJaxbTypeConverter {
     protected static CE convertGenderAttributeToCE(GenderAttribute genderAttribute) {
        if (genderAttribute == null || genderAttribute.getValue() == null) {
            return null;
        }
        CE gender = objectFactory.createCE();
        switch (genderAttribute.getValue()) {
            case Male:
                gender.setCode(HL7v3GenderValues.GENDER_MALE);
                return gender;
            case Female:
                gender.setCode(HL7v3GenderValues.GENDER_FEMALE);
                return gender;
            case Unknown:
                gender.setNullFlavor(NullFlavor.UNK);
                return gender;
            default:
                return null;
        }
    }
}
