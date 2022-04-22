/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.entity.transaction.converter;


import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.camel.Converter;

/**
 *
 * @author conrad.gustafson
 */
@Converter
public class HapiStringConverter {
//    @Converter
    private static String convertHapiToString(Message message) throws HL7Exception {
        HapiContext context = new DefaultHapiContext();
        PipeParser parser = context.getPipeParser();
       return parser.encode(message);
    }
  
   @Converter
   public static InputStream convertHapiToInputStream(Message message) throws HL7Exception, IOException {
        String asString = convertHapiToString(message);
        InputStream asStream = new ByteArrayInputStream(asString.getBytes("UTF-8"));
        return asStream;
    }

}
