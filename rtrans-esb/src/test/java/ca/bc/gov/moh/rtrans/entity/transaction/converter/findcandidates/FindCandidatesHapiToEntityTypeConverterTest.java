/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;

import ca.bc.gov.moh.rtrans.entity.GenderValues;
import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidates;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.service.v2.Hl7v2Parser;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09;
import java.util.Date;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test FindCandidatesHapiToEntityTypeConverter which converts v2 R09 to Entity Find Candidates
 * @author kuan.fan
 */
public class FindCandidatesHapiToEntityTypeConverterTest {
    
    /**
     * Test FindCandidatesHapiToEntityTypeConverter which converts R09 to entity class FindCandidates
     * @throws Exception thrown if Hapi parser has errors 
     */
    @Test
    public void testConvertR09() throws Exception {
       
        FindCandidates fc = createFindCandidatesRequestEntity();
        
        //test MSH segment converting
        assertEquals("20170530161528", fc.getMessageId());
        Date creationTime = DataTypeConverter.DATE_TIME_FORMAT.parse("20170530161528");
        assertEquals(creationTime, fc.getCreationTime());
        assertEquals("RAIPRSN-NM-SRCH", fc.getReceiver().get(0).getSystemName());
        assertEquals("BC00002041",fc.getReceiver().get(0).getOrganization());
        assertEquals("HNWEB", fc.getSender().getSystemName());
        assertEquals("BC01000106", fc.getSender().getOrganization());
        assertEquals("GARY.DONOGHUE", fc.getAuthor().getUser().getUserId());
        
        //test PID segment converting
        Date birthDate = DataTypeConverter.YEAR_MONTH_DAY_FORMAT.parse("19780924");
        assertEquals(birthDate, fc.getPerson().getBirthDate().getValue());
        assertEquals(GenderValues.Male.value(), fc.getPerson().getGender().getValue().value());
        
        //test ZIA segment converting
        assertEquals("branton", fc.getPerson().getName().get(0).getLastName());
        assertEquals("eward", fc.getPerson().getName().get(0).getFirstName());
        assertEquals("METHIONYLTHREONYL", fc.getPerson().getName().get(0).getMiddleName());
        
    }
    
    public static FindCandidates createFindCandidatesRequestEntity() throws Exception {
        
        String r09 = "MSH|^~\\&|HNWEB|BC01000106|RAIPRSN-NM-SRCH|BC00002041|20170530161528|GARY.DONOGHUE|R09|20170530161528|D|unitTestFCMsh12||\r"+
        "ZHD|20170530161528|^^00000010|TRAININGAdmin||||2.4\r"+
        "QRD|||||||^RD||PSN\r"+
        "PID||^^^BC^PH|||||19780924|M\r"+
        "ZIA|||||||||||||||branton^eward^METHIONYLTHREONYL\r";

        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);
        exchange.getIn().setBody(r09);

        Hl7v2Parser instance = new Hl7v2Parser();
        instance.process(exchange);

        R09 r09t = exchange.getIn().getBody(R09.class);
        FindCandidates fc = FindCandidatesHapiToEntityTypeConverter.convert(r09t, exchange);

        return fc;
    }
    
}
