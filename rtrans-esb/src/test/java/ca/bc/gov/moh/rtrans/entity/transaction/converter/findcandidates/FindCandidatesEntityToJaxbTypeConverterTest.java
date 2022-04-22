/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;

import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.EntityToJaxbTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidates;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.hl7.v3.EnFamily;
import org.hl7.v3.EnGiven;
import org.hl7.v3.HCIMINFindCandidates;
import org.hl7.v3.QUPAMT101103BCQueryByParameterPayload;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test FindCandidatesEntityToJaxbTypeConverter.
 * @author Kuan Fan
 */
public class FindCandidatesEntityToJaxbTypeConverterTest extends EntityToJaxbTypeConverter {
    
    @Test
    public void testFCEntityToV3() throws Exception{
        
        FindCandidates fc = FindCandidatesHapiToEntityTypeConverterTest.createFindCandidatesRequestEntity();
        
        HCIMINFindCandidates findCandidatesV3 = FindCandidatesEntityToJaxbTypeConverter.convert(fc);
        
        assertEquals("20170530161528",findCandidatesV3.getId().getExtension());
        assertEquals("20170530161528",findCandidatesV3.getCreationTime().getValue()); 
        assertEquals("RAIPRSN-NM-SRCH", findCandidatesV3.getReceiver().getDevice().getId().getExtension());
        assertEquals("BC00002041", findCandidatesV3.getReceiver().getDevice().getAsAgent().getValue()
                .getRepresentedOrganization().getValue().getId().getExtension());
        assertEquals("HNWEB", findCandidatesV3.getSender().getDevice().getId().getExtension());
        assertEquals("BC01000106", findCandidatesV3.getSender().getDevice().getAsAgent().getValue()
                .getRepresentedOrganization().getValue().getId().getExtension());
        
        QUPAMT101103BCQueryByParameterPayload payLoad = 
                findCandidatesV3.getControlActProcess().getQueryByParameter().getValue().getQueryByParameterPayload();
        //verify person Gender
        String genderV3 = payLoad.getPersonAdministrativeGender().getValue().getValue().getCode();
        assertEquals(genderV3, "M");
        
        //verify person Birthday
        String birthDateV3 = payLoad.getPersonBirthTime().getValue().getValue().getValue();
        assertEquals(birthDateV3, "19780924");
        
        //verify person name
        List<Serializable> nameList = payLoad.getPersonName().getValue().getValue().getContent();
        
        JAXBElement lastNameJAXB = (JAXBElement)nameList.get(0);
        EnFamily lastNameEnFamily = (EnFamily)lastNameJAXB.getValue();
        String lastName = lastNameEnFamily.getText();
        assertEquals(lastName, "Branton");
        
        JAXBElement firstNameJAXB = (JAXBElement)nameList.get(1);
        EnGiven firstNameEnGiven = (EnGiven)firstNameJAXB.getValue();
        String firstName = firstNameEnGiven.getText();
        assertEquals(firstName, "Eward");
        
        JAXBElement middleNameJAXB = (JAXBElement)nameList.get(2);
        EnGiven middleNameEnGiven = (EnGiven)middleNameJAXB.getValue();
        String middleName = middleNameEnGiven.getText();
        assertEquals("METHIONYLTHREONYL", middleName);
        
    }    
    
}
