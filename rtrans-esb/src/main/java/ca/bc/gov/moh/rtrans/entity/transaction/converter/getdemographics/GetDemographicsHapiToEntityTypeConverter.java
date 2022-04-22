/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;

import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographics;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HapiEntityConverterV24;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R03;
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
public class GetDemographicsHapiToEntityTypeConverter {
    
    @Converter
    public static GetDemographics convertR03(R03 r03Message, Exchange exchange) throws HL7Exception, ParseException {
        
        HapiEntityConverterV24 hapiEntityConverterV24 = new HapiEntityConverterV24(exchange);
        GetDemographics getDemographics = new GetDemographics();
        
        if (!r03Message.getMSH().isEmpty()) {
            hapiEntityConverterV24.mapMSHToRequestMessage(r03Message.getMSH(), getDemographics);
        }
        
        if (r03Message.getZHD().isEmpty()) {
            throw new ParseException("ZHD is a required field", 0);
        }

        if (!r03Message.getPID().isEmpty()) {
            CX patientId = r03Message.getPID().getPid2_PatientID();
            hapiEntityConverterV24.mapCXToId(patientId, getDemographics.getIdentifier());
        } else {
            throw new ParseException("PID is a required field", 0);
        }

        return getDemographics;
    }

}
