/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.DateAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderValues;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonNameTypes;
import ca.bc.gov.moh.rtrans.entity.ProbabalisticPersonSearchResult;
import ca.bc.gov.moh.rtrans.entity.SystemResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidatesResponse;
import ca.bc.gov.moh.rtrans.service.v2.V2ServiceConstants;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09Response;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Test FindCandidatesHapiToEntityTypeConverter which converts v2 R09 to Entity Find Candidates
 * @author kuan.fan
 */
public class FindCandidatesEntityToHapiTypeConverterTest {
    
    CamelContext ctx = new DefaultCamelContext(); 
    Exchange exchange = new DefaultExchange(ctx);
    
    @Before
    public void setup() throws IOException {
        Properties exchangeProperties = new Properties();
        exchangeProperties.load(FindCandidatesEntityToHapiTypeConverterTest.class.getClassLoader().getResourceAsStream("errorMap.properties"));
        
        Properties haValueMapProperties = new Properties();
        haValueMapProperties.load(FindCandidatesEntityToHapiTypeConverterTest.class.getClassLoader().getResourceAsStream("haValueMapConfig.properties"));
        
        exchangeProperties.putAll(haValueMapProperties);
        
        exchange.getContext().getPropertiesComponent().setInitialProperties(exchangeProperties);
    }
    
    /**
     * Test FindCandidatesHapiToEntityTypeConverter which converts R09 to entity class FindCandidates
     * @throws Exception thrown if Hapi parser has errors 
     */
    @Test
    public void testConvertFCR2R09Response() throws Exception {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdfDateOnly = new SimpleDateFormat("yyyy-MM-dd");
        FindCandidatesResponse response = new FindCandidatesResponse();
        
        //prepare entity content whcih needed for MSH segment conversion
        response.setCreationTime(sdf.parse("2017-06-17 11:55:00"));
        
        response.setMessageId("1234abcd");
        
        //prepare entity content whcih needed for MSA and ERR segment conversion
        SystemResponse systemResponse = new SystemResponse();
        response.setAcceptAckCode("NE");
        systemResponse.setCode("BCHCIM.FC.0.0012");
        response.getSystemResponse().add(systemResponse);
        
        //prepare entity content whcih needed for ZTL segment conversion
        ProbabalisticPersonSearchResult ppsr1 = new ProbabalisticPersonSearchResult();
        Person person1 = ppsr1.getPerson();
        //set PID required fields
        IdentifierAttribute ia1 = new IdentifierAttribute();
        ia1.setType(IdentifierTypes.BCPHN);
        ia1.setValue("9848222111");
        List<IdentifierAttribute> iaList1 = new ArrayList<>();
        iaList1.add(ia1);
        person1.setIdentifier(iaList1);
        DateAttribute da1 = new DateAttribute();
        da1.setValue(sdfDateOnly.parse("2017-06-17"));
        person1.setBirthDate(da1);
        DateAttribute deathDate = new DateAttribute();
        deathDate.setValue(sdfDateOnly.parse("2017-09-09"));
        person1.setDeathDate(deathDate);
        GenderAttribute ga1 = new GenderAttribute();
        ga1.setValue(GenderValues.Male);
        person1.setGender(ga1);
        //set ZIA required fields
        PersonNameAttribute pna1 = new PersonNameAttribute();
        pna1.setLastName("Publician-Caesar");
        pna1.setFirstName("Joseph-Randallian");
        pna1.setMiddleName("Middletonsqire-Hamish");
        pna1.setTitle("Jerome-Henri-Mathieu");
        pna1.setType(PersonNameTypes.Declared);
        List<PersonNameAttribute> personNameList1 = person1.getName();
        personNameList1.add(pna1);
        
        AddressAttribute aa1 = new AddressAttribute();
        aa1.setCity("Vancouver");
        aa1.setProvince("BC");
        aa1.setPostalCode("V8N 8KU");
        List<String> streetAddressLineList = new ArrayList<>();
        streetAddressLineList.add("SKINNER HOLLOW ROAD blah blah blah blah this is to test string truncating at 50 chars");
        aa1.setStreetAddressLines(streetAddressLineList);
        List<AddressAttribute> addressList1 = person1.getAddress();
        addressList1.add(aa1);
        
        ProbabalisticPersonSearchResult ppsr2 = new ProbabalisticPersonSearchResult();
        Person person2 = ppsr2.getPerson();
        //set PID required fields
        IdentifierAttribute ia2 = new IdentifierAttribute();
        ia2.setType(IdentifierTypes.BCPHN);
        ia2.setValue("9848333444");
        List<IdentifierAttribute> iaList2 = new ArrayList<>();
        iaList2.add(ia2);
        person2.setIdentifier(iaList2);
        DateAttribute da2 = new DateAttribute();
        da2.setValue(sdfDateOnly.parse("2017-06-18"));
        person2.setBirthDate(da2);
        GenderAttribute ga2 = new GenderAttribute();
        ga2.setValue(GenderValues.Female);
        person2.setGender(ga2);
        //set ZIA required fields
        
        PersonNameAttribute pna2 = new PersonNameAttribute();
        pna2.setLastName("Brandon");
        pna2.setFirstName("James");
        pna2.setMiddleName("Middle");
        pna2.setType(PersonNameTypes.Declared);
        List<PersonNameAttribute> personNameList2 = person2.getName();
        personNameList2.add(pna2);
        AddressAttribute pa2 = new AddressAttribute();
        pa2.setCity("Victoria");
        pa2.setProvince("BC");
        pa2.setPostalCode("V8N 2W1");        
        List<String> streetAddressLineList2 = new ArrayList<>();
        streetAddressLineList2.add("222 Douglas street");
        pa2.setStreetAddressLines(streetAddressLineList2);
        List<AddressAttribute> addressList2 = person2.getAddress();
        addressList2.add(pa2);
        
        ProbabalisticPersonSearchResult ppsr = new ProbabalisticPersonSearchResult();
        List<ProbabalisticPersonSearchResult> ppsrList = new ArrayList<>();
        ppsrList.add(ppsr1);
        ppsrList.add(ppsr2);
        response.setSearchResults(ppsrList);
        
        exchange.getIn().setHeader(V2ServiceConstants.strMessageIDProperty, "19980915000031");
        exchange.getIn().setHeader(V2ServiceConstants.messageid, "19980915000031");
        exchange.getIn().setHeader(V2ServiceConstants.messageSecurity, "Kuan.Fan");
        exchange.getIn().setHeader(V2ServiceConstants.messageProcessId, "D");
        exchange.getIn().setHeader(V2ServiceConstants.senderApplication, "HNWEB");
        exchange.getIn().setHeader(V2ServiceConstants.senderFacility, "BC01000106");
        exchange.getIn().setHeader(V2ServiceConstants.receiverApplication, "RAIPRSN-NM-SRCH");
        exchange.getIn().setHeader(V2ServiceConstants.receiverFacility, "BC00002041");
        exchange.getIn().setHeader(V2ServiceConstants.VERSION_ID, "unitTestFCMsh12");
        
        R09Response r09Resonse = FindCandidatesEntityToHapiTypeConverter.convertFCR2R09Response(response, exchange);
        assertEquals(r09Resonse.getMSH().encode(), "MSH|^~\\&|RAIPRSN-NM-SRCH|BC00002041|HNWEB|BC01000106|20170617115500|Kuan.Fan|R09|1234abcd|D|unitTestFCMsh12");
        assertEquals(r09Resonse.getMSA().encode(), "MSA|AA|19980915000031|HJMB001ISUCCESSFULLY COMPLETED");
        assertEquals(r09Resonse.getERR().encode(), "ERR|^^^HJMB001I&SUCCESSFULLY COMPLETED");
        assertEquals(r09Resonse.getZTL().encode(), "ZTL|2^RD");
        assertEquals(r09Resonse.getPersonFound(0).getPID().encode(), "PID|001|9848222111^^^BC^PH^MOH|||||20170617|M|||||||||||||||||||||20170909");
        assertEquals("ZIA|||||||||||||||Publician-Caesar^Joseph-Randalli^Middletonsqire-^Jerome-Henri-Ma^^^L|||SKINNER HOLLOW ROAD blah blah blah blah this is to||||001",r09Resonse.getPersonFound(0).getZIA().encode());
        assertEquals(r09Resonse.getPersonFound(1).getPID().encode(), "PID|002|9848333444^^^BC^PH^MOH|||||20170618|F");
        assertEquals(r09Resonse.getPersonFound(1).getZIA().encode(), "ZIA|||||||||||||||Brandon^James^Middle^^^^L|||222 Douglas street Victoria BC V8N 2W1||||002");
        
    }
}
