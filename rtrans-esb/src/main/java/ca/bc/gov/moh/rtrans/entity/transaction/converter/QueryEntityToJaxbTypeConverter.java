/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.entity.GenderAttribute;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.EntityToJaxbTypeConverter;
import org.hl7.v3.CV;
import org.hl7.v3.NullFlavor;

/**
 *
 * @author patrick.weckermann
 * 
 * Query includes find candidates and get demographics. Query transactions and non-query transactions typically have different mappings and defaults.
 */
public class QueryEntityToJaxbTypeConverter extends EntityToJaxbTypeConverter {

    protected static CV convertGenderAttributeToCV(GenderAttribute genderAttribute) {
        if (genderAttribute == null || genderAttribute.getValue() == null) {
            return null;
        }
        CV gender = objectFactory.createCV();
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
