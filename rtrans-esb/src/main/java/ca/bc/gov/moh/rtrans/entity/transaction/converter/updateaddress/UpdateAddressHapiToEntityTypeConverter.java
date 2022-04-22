/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.updateaddress;

import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographics;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HapiEntityConverterV24;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R07;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.datatype.CX;
import java.text.ParseException;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;

/**
 *
 * @author conrad.gustafson
 */
@Converter
public class UpdateAddressHapiToEntityTypeConverter {
    
    @Converter
    public static GetDemographics convertR07(R07 r07Message, Exchange exchange) throws HL7Exception, ParseException {
        
        HapiEntityConverterV24 hapiEntityConverterV24 = new HapiEntityConverterV24(exchange);
        GetDemographics getDemographics = new GetDemographics();
        
        if (!r07Message.getMSH().isEmpty()) {
            hapiEntityConverterV24.mapMSHToRequestMessage(r07Message.getMSH(), getDemographics);
        }
        
        if (!r07Message.getPID().isEmpty()) {
            CX patientId = r07Message.getPID().getPid2_PatientID();
            hapiEntityConverterV24.mapCXToId(patientId, getDemographics.getIdentifier());
        }

        return getDemographics;
    }

}
