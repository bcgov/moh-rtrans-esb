/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R03;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R07;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import java.text.ParseException;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
public class Hl7v2ParserTest {
    
    @Test
    public void testR03() throws HL7Exception, IOException, ParseException, Exception {
        String r03
                = "MSH|^~\\&|HNWeb|BC01000030|RAIGT-PRSN-DMGR|BC00001013|20170125122125|train96|R03|20170125122125|D|2.4||\r"
                + "ZHD|20170125122125|^^00000010|HNAIADMINISTRATION||||2.4\r"
                + "PID||1234567890^^^BC^PH";

        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        exchange.getIn().setBody(r03);

        Hl7v2Parser instance = new Hl7v2Parser();
        instance.process(exchange);

        R03 r03t = exchange.getIn().getBody(R03.class);
        

        assertEquals("\\F\\", r03t.getMSH().getMsh1_FieldSeparator().encode());
        assertEquals("\\S\\\\R\\\\E\\\\T\\", r03t.getMSH().getMsh2_EncodingCharacters().encode());
        assertEquals("HNWeb", r03t.getMSH().getMsh3_SendingApplication().encode());
        assertEquals("BC01000030", r03t.getMSH().getMsh4_SendingFacility().encode());
        assertEquals("RAIGT-PRSN-DMGR", r03t.getMSH().getMsh5_ReceivingApplication().encode());
        assertEquals("BC00001013", r03t.getMSH().getMsh6_ReceivingFacility().encode());
        assertEquals("20170125122125", r03t.getMSH().getMsh7_DateTimeOfMessage().encode());
        assertEquals("train96", r03t.getMSH().getMsh8_Security().encode());
        assertEquals("R03", r03t.getMSH().getMsh9_MessageType().encode());
        
        assertEquals("^^00000010", r03t.getZHD().getZHD2_Organization().encode());
        assertEquals("20170125122125", r03t.getZHD().getZHD1_EventDateTime().encode());
        assertEquals("HNAIADMINISTRATION", r03t.getZHD().getZHD3_UserGroup().encode());
        assertEquals("2.4", r03t.getZHD().getZHD7_SoftwareVersionNumber().encode());
        
        assertEquals("", r03t.getPID().getPid1_SetIDPID().encode());
        assertEquals("1234567890^^^BC^PH", r03t.getPID().getPid2_PatientID().encode());
    }
    
    /**
     * This tests illustrates the properties of emptiness in Entities. It is also useful because it's the only R07 HL7v2Parser test
     * @throws HL7Exception
     * @throws Exception 
     */
    @Test
    public void testR07() throws HL7Exception, Exception {
        String r07 = "MSH|^~\\&|HNWeb|BC01000088|RAIUPDT-PR-ADDR|BC00001000|20171019152000|train96|R07|DAUpdateAddressinCRS|D|2.4||\r" +
                "ZHD|20171019145400|^^00000010|TRAININGAdmin||||2.4\r" +
                "PID||1234567890^^^BC^PH\r" +
                "ZIA||||||||||||||||^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^||||||N\r";
        
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        exchange.getIn().setBody(r07);

        Hl7v2Parser instance = new Hl7v2Parser();
        instance.process(exchange);
        
        ZIA zia = exchange.getIn().getBody(R07.class).getZIA();
        
        assertEquals(true, zia.getExtendedAddress().isEmpty());
        assertNotEquals(null, zia.getExtendedTelephoneNumber());
        assertEquals(true, zia.getExtendedTelephoneNumber().isEmpty());
    }

}
