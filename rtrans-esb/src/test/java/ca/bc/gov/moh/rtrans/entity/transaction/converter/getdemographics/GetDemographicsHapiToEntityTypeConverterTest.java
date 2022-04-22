/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;

import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographics;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.service.v2.Hl7v2Parser;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R03;
import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author trevor.schiavone
 */
public class GetDemographicsHapiToEntityTypeConverterTest {
    
    @Test
    public void testR03() throws HL7Exception, IOException, ParseException, Exception {
        String r03
                = "MSH|^~\\&|HNWEB|BC01000030|RAIGT-PRSN-DMGR|BC00001013|20170125122125|train96|R03|20170125122125|D|2.4||\r"
                + "ZHD|20170125122125|^^00000010|HNAIADMINISTRATION||||2.4\r"
                + "PID||1234567890^^^BC^PH";

        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        exchange.getIn().setBody(r03);

        Hl7v2Parser instance = new Hl7v2Parser();
        instance.process(exchange);

        R03 r03t = exchange.getIn().getBody(R03.class);
        GetDemographics gd = GetDemographicsHapiToEntityTypeConverter.convertR03(r03t, exchange);

        //test MSH segment conversion
        assertEquals("20170125122125", gd.getMessageId());
        Date creationTime = DataTypeConverter.DATE_TIME_FORMAT.parse("20170125122125");
        assertEquals(creationTime, gd.getCreationTime());
        assertEquals("RAIGT-PRSN-DMGR", gd.getReceiver().get(0).getSystemName());
        assertEquals("BC00001013",gd.getReceiver().get(0).getOrganization());
        assertEquals("HNWEB", gd.getSender().getSystemName());
        assertEquals("BC01000030", gd.getSender().getOrganization());
        assertEquals("train96", gd.getAuthor().getUser().getUserId());
        
        //test PID segement conversion
        assertEquals("1234567890", gd.getIdentifier().getValue());
        assertEquals("BC", gd.getIdentifier().getSource());
        assertEquals(IdentifierTypes.BCPHN, gd.getIdentifier().getType());
        
    }
}
