/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;


import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.GenderValues;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.ProbabalisticPersonSearchResult;
import ca.bc.gov.moh.rtrans.entity.SystemResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidatesResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.JaxbToEntityTypeConverter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.hl7.v3.HCIMINFindCandidatesResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test  FindCandidatesJaxbToEntityTypeConverter.
 * @author Kuan Fan
 */
public class FindCandidatesJaxbToEntityTypeConverterTest extends JaxbToEntityTypeConverter {
    
    @Test
    public void testFCV3toEntity() throws ParseException, JAXBException {
        HCIMINFindCandidatesResponse findCandidatesV3 = FindCandidatesUtils.createValidV3FindCandidatesResponse();
        FindCandidatesResponse findCandidatesResponseEntity = FindCandidatesJaxbToEntityTypeConverter.convert(findCandidatesV3);
        Date creationTime = DataTypeConverter.DATE_TIME_FORMAT.parse("20170621115133");
        
        //ID and creation time
        assertNotNull(findCandidatesResponseEntity);
        assertEquals("6018a3e4-7175-4b64-9012-c680102807ae", findCandidatesResponseEntity.getMessageId());
        assertEquals(creationTime, findCandidatesResponseEntity.getCreationTime()); 
        
        //Sender Info
        assertNotNull(findCandidatesResponseEntity.getSender());
        assertEquals("RAIPRSN-NM-SRCH", findCandidatesResponseEntity.getSender().getSystemName());
        assertEquals("BC00002041", findCandidatesResponseEntity.getSender().getOrganization());        
        
        //Receiver Info
        assertNotNull(findCandidatesResponseEntity.getReceiver());
        assertEquals(1, findCandidatesResponseEntity.getReceiver().size());
        assertEquals("BCHCIM", findCandidatesResponseEntity.getReceiver().get(0).getSystemName());
        assertEquals("VIHA", findCandidatesResponseEntity.getReceiver().get(0).getOrganization());        
    
        //System Response (queryAck)
        List<SystemResponse> systemResponses = findCandidatesResponseEntity.getSystemResponse();
        assertNotNull(systemResponses);
        assertEquals(1, systemResponses.size());
        SystemResponse systemResponse = systemResponses.get(0);       
        assertEquals("BCHCIM.FC.0.0012", systemResponse.getCode());
        assertEquals("The search completed successfully.", systemResponse.getCodeText());
        
        //Person 1
        //Search Result 
        List<ProbabalisticPersonSearchResult> probabalisticPersonSearchResultList = findCandidatesResponseEntity.getSearchResults();
        Person person = probabalisticPersonSearchResultList.get(0).getPerson();
        assertEquals("BRANTON-JAMES-HOWARD", person.getName().get(0).getLastName());
        assertEquals("EDWARD-BARNESIUS", person.getName().get(0).getFirstName());
        assertEquals("TIMOTHY-JAMES-BOB", person.getName().get(0).getMiddleName());
        assertEquals(GenderValues.Male, person.getGender().getValue());
                
        //Search Result - Phone
        assertEquals("250", person.getPhone().get(0).getAreaCode());
        assertEquals("7778868", person.getPhone().get(0).getNumber());
        assertEquals(CommunicationTypes.Home, person.getPhone().get(0).getType());
        
        //Search Result - Birth Date
        Date birthDate = DataTypeConverter.YEAR_MONTH_DAY_FORMAT.parse("19780924");
        assertEquals(birthDate, person.getBirthDate().getValue());
    
        
        //Search Result - Address 1
        AddressAttribute personFirstAddress = person.getAddress().get(0);
        assertEquals(AddressTypes.Home, personFirstAddress.getType());
        assertEquals("1102 HILDA ST", personFirstAddress.getStreetAddressLines().get(0));
        assertEquals("VICTORIA", personFirstAddress.getCity());
        assertEquals("BC", personFirstAddress.getProvince());
        assertNull(personFirstAddress.getCountry());
        assertEquals("V8V2Z3", personFirstAddress.getPostalCode());
        assertEquals(false, personFirstAddress.getIsVerified());       
        
        AddressAttribute personSecondAddress = person.getAddress().get(1);
        assertEquals(AddressTypes.Mail, personSecondAddress.getType());
        assertEquals("10038 NE 197TH ST", personSecondAddress.getStreetAddressLines().get(0));
        assertEquals("BOTHELL", personSecondAddress.getCity());
        assertEquals("WA", personSecondAddress.getProvince());
        assertNull(personSecondAddress.getCountry());
        assertEquals("98011", personSecondAddress.getPostalCode());
        assertEquals(true, personSecondAddress.getIsVerified());        

        //Person 2
        //Search Result 
        probabalisticPersonSearchResultList = findCandidatesResponseEntity.getSearchResults();
        person = probabalisticPersonSearchResultList.get(1).getPerson();
        assertEquals(person.getName().get(0).getLastName(), "Dekeyzer");
        assertEquals(person.getName().get(0).getFirstName(), "Eward");
        assertEquals(person.getName().get(0).getMiddleName(), "Jimmy");
        assertEquals(person.getName().get(0).getTitle(), "Jr");
        assertEquals(person.getGender().getValue(), GenderValues.Male);
                
        //Search Result - Phone
        assertEquals("812", person.getPhone().get(0).getAreaCode());
        assertEquals("2365405", person.getPhone().get(0).getNumber());
        assertEquals(CommunicationTypes.Home, person.getPhone().get(0).getType());
        
        //Search Result - Birth Date
        birthDate = DataTypeConverter.YEAR_MONTH_DAY_FORMAT.parse("19280124");
        assertEquals(birthDate, person.getBirthDate().getValue());
        
        //Search Result - Address 1
        personFirstAddress = person.getAddress().get(0);
        assertEquals(AddressTypes.Home, personFirstAddress.getType());
        assertEquals("SKINNER HOLLOW ROAD blah blah blah blah this is to test string truncating at 50 chars", personFirstAddress.getStreetAddressLines().get(0));
        assertEquals("Eau claire", personFirstAddress.getCity());
        assertEquals("BC", personFirstAddress.getProvince());
        assertEquals("CA", personFirstAddress.getCountry());
        assertEquals("V2T6D7", personFirstAddress.getPostalCode());
        assertEquals(false, personFirstAddress.getIsVerified());       

    }
    
}
