/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;


import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.GenderValues;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonNameTypes;
import ca.bc.gov.moh.rtrans.entity.SystemResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographicsResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.JaxbToEntityTypeConverter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.hl7.v3.HCIMINGetDemographicsResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 *
 * @author trevor.schiavone
 */
public class GetDemographicsJaxbToEntityTypeConverterTest extends JaxbToEntityTypeConverter {
    
    @Test
    public void testGDV3toEntity() throws ParseException, JAXBException {
        
        HCIMINGetDemographicsResponse getDemoResponseV3 =  GetDemoUtils.createValidV3GetDemoResponse();
        
        GetDemographicsResponse getDemoResponseEntity = GetDemographicsJaxbToEntityTypeConverter.convert(getDemoResponseV3);
        Date creationTime = DataTypeConverter.DATE_TIME_FORMAT.parse("20170125122125");
        
        
        //ID and creation time
        assertNotNull(getDemoResponseEntity);
        assertEquals("b3a12f0c-a332-4ec9-94b9-d8539a02df48", getDemoResponseEntity.getMessageId());
        assertEquals(creationTime, getDemoResponseEntity.getCreationTime());
        
        //Sender Info
        assertNotNull(getDemoResponseEntity.getSender());
        assertEquals("RAIGT-PRSN-DMGR", getDemoResponseEntity.getSender().getSystemName());
        assertEquals("BC00001013", getDemoResponseEntity.getSender().getOrganization());
        
        //Receiver Info
        assertNotNull(getDemoResponseEntity.getReceiver());
        assertEquals(1, getDemoResponseEntity.getReceiver().size());
        assertEquals("BCHCIM", getDemoResponseEntity.getReceiver().get(0).getSystemName());
        assertEquals("VIHA", getDemoResponseEntity.getReceiver().get(0).getOrganization());
        
        //System Response (queryAck)
        List<SystemResponse> systemResponses = getDemoResponseEntity.getSystemResponse();
        assertNotNull(systemResponses);
        assertEquals(1, systemResponses.size());
        SystemResponse systemResponse = systemResponses.get(0);       
        assertEquals("BCHCIM.GD.0.0013", systemResponse.getCode());
        assertEquals("The get demographics query completed successfully.", systemResponse.getCodeText());
        
        //Search Result 
        Person person = getDemoResponseEntity.getSearchResult();
        assertEquals("1234567890", person.getPHN());
        assertEquals("MOH_CRS", person.getIdentifier().get(0).getSource());
        
        //Search Result - Name
        PersonNameAttribute personName = person.getName().get(0);
        assertEquals(PersonNameTypes.Declared, personName.getType());
        assertEquals("JEAN-RENE-BAPTISTE", personName.getFirstName());
        assertEquals("DAWN-SHAWN-BROWNE", personName.getMiddleName());
        assertEquals("ANGELICA-MONTANA", personName.getTitle());
        assertEquals("PNEUMONOULTRAMICROSCOPICSILICOVOLCANOCONIOSIS", personName.getLastName());
        assertEquals("JILL", personName.getPreferredGivenName());     
        
        //Search Result - Gender
        assertEquals(GenderValues.Female, person.getGender().getValue());
        
        //Search Result - Phone
        assertEquals("604", person.getPhone().get(0).getAreaCode());
        assertEquals("8883322", person.getPhone().get(0).getNumber());
        assertEquals(CommunicationTypes.Home, person.getPhone().get(0).getType());
        
        //Search Result - Birth Date
        Date birthDate = DataTypeConverter.YEAR_MONTH_DAY_FORMAT.parse("19721016");
        assertEquals(birthDate, person.getBirthDate().getValue());
        
        //Search Result - Address 1
        AddressAttribute personFirstAddress = person.getAddress().get(0);
        assertEquals(AddressTypes.Home, personFirstAddress.getType());
        assertEquals("321 SUNSHINE PL", personFirstAddress.getStreetAddressLines().get(0));
        assertEquals("NEW LINE 2", personFirstAddress.getStreetAddressLines().get(1));
        assertEquals("NEW LINE 3", personFirstAddress.getStreetAddressLines().get(2));
        assertEquals("VANCOUVER", personFirstAddress.getCity());
        assertEquals("BC", personFirstAddress.getProvince());
        assertEquals("CA", personFirstAddress.getCountry());
        assertEquals("V9L 4H2", personFirstAddress.getPostalCode());
        assertEquals(true, personFirstAddress.getIsVerified());
        
        AddressAttribute personSecondAddress = person.getAddress().get(1);
        assertEquals(AddressTypes.Mail, personSecondAddress.getType());
        assertEquals("1824 CROSS RD", personSecondAddress.getStreetAddressLines().get(0));
        assertEquals("LINE 2", personSecondAddress.getStreetAddressLines().get(1));
        assertEquals("LINE 3", personSecondAddress.getStreetAddressLines().get(2));
        assertEquals("VANCOUVER", personSecondAddress.getCity());
        assertEquals("BC", personSecondAddress.getProvince());
        assertNull(personSecondAddress.getCountry());
        assertEquals("V9L 4H2", personSecondAddress.getPostalCode());
        assertEquals(false, personSecondAddress.getIsVerified());
        
        
        

    }
    
}
