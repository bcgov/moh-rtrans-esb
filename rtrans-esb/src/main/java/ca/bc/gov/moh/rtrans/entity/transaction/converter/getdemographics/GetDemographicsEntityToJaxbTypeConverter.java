/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;

import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographics;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.EntityToJaxbTypeConverter;
import org.apache.camel.Converter;
import org.hl7.v3.HCIMINGetDemographics;
import org.hl7.v3.II;
import org.slf4j.LoggerFactory;

/**
 *
 * @author conrad.gustafson
 */
@Converter
public class GetDemographicsEntityToJaxbTypeConverter extends EntityToJaxbTypeConverter {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GetDemographicsEntityToJaxbTypeConverter.class);
    
    @Converter
    public static HCIMINGetDemographics convert(GetDemographics request) {
        
        HCIMINGetDemographics getDemographics = new HCIMINGetDemographics();

        convertCommonFields(getDemographics, request);
        
        // set query parameter
        final II personID = getDemographics.getControlActProcess().getQueryByParameter().getValue().getQueryByParameterPayload().getPersonId().getValue();
        personID.setExtension(request.getIdentifier().getValue());
        
        return getDemographics;
    }
}
