/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author trevor.schiavone
 */
public class HnSecureAddLeadingBytesTest {
    /**
     * Test of process method of class HnSecureAddLeadingBytes.
     */
    @Test
    public void testProcess() {

        String testMessage
                = "MSH|^~\\&|PHSACERNERRAW|MOH_CRS|EMPI_BCHCIM|BCHCIM|20140625115359||QBP^Q21^QBP_Z21|164942|T|2.4\r"
                + "EVN|Query Demographics|20140625115359||Query Demographics|PHSA321754^EKB^VKDMG^R^^^^^COPSE^^^^EI~P1CMAH^EKB^VKDMG^R^^^^^CHRPI^^^^PID\r"
                + "QPD|Q21^Get Person Demographics^HL7003|164942|9151098425^^^BC Provincial Id Number^NHN\r"
                + "RCP|D|10^RD\r"
                + "PID|1||1234567890^^^BC Provincial Id Number^NHN||||||||||||||||||||||0||||||N";
        
        String addMessage 
                = "00000404MSH|^~\\&|PHSACERNERRAW|MOH_CRS|EMPI_BCHCIM|BCHCIM|20140625115359||QBP^Q21^QBP_Z21|164942|T|2.4\r"
                + "EVN|Query Demographics|20140625115359||Query Demographics|PHSA321754^EKB^VKDMG^R^^^^^COPSE^^^^EI~P1CMAH^EKB^VKDMG^R^^^^^CHRPI^^^^PID\r"
                + "QPD|Q21^Get Person Demographics^HL7003|164942|9151098425^^^BC Provincial Id Number^NHN\r"
                + "RCP|D|10^RD\r"
                + "PID|1||1234567890^^^BC Provincial Id Number^NHN||||||||||||||||||||||0||||||N";
        
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        exchange.getIn().setBody(testMessage);
                     
        HnSecureAddLeadingBytes instance = new HnSecureAddLeadingBytes();
        instance.process(exchange);
        
        //assertions
        assertEquals(exchange.getIn().getBody(), addMessage);

    }
}
