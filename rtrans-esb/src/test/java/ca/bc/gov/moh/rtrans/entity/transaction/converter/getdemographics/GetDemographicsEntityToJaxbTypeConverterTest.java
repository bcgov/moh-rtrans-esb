/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;

import ca.bc.gov.moh.rtrans.entity.Author;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographics;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.EntityToJaxbTypeConverter;
import ca.bc.gov.moh.rtrans.entity.User;
import static ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter.convertToDate;
import java.text.ParseException;
import java.util.Date;
import org.hl7.v3.HCIMINGetDemographics;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author trevor.schiavone
 */
public class GetDemographicsEntityToJaxbTypeConverterTest extends EntityToJaxbTypeConverter {
    
    @Test
    public void testGDEntityToV3() throws ParseException{
        GetDemographics gd = new GetDemographics();
        HCIMINGetDemographics getDemographicsV3;
        
        gd.setMessageId("20170125122125");
        Date creationTime = DataTypeConverter.DATE_TIME_FORMAT.parse("20170125122125");
        gd.setCreationTime(creationTime);
        gd.getReceiver().get(0).setSystemName("RAIGT-PRSN-DMGR");
        gd.getReceiver().get(0).setOrganization("BC00001013");
        gd.getSender().setSystemName("HNWEB");
        gd.getSender().setOrganization("BC01000030");
        
        Author author = new Author();
        User user = new User();
    
        String userId = "HNAIADMINISTRATION";
        user.setUserId(userId);
        author.setUser(user);
        gd.setAuthor(author);

        gd.getIdentifier().setValue("1234567890");
        gd.getIdentifier().setSource("BC");
        gd.getIdentifier().setType(IdentifierTypes.BCPHN);
        
        getDemographicsV3 = GetDemographicsEntityToJaxbTypeConverter.convert(gd);
        
        assertEquals("20170125122125", getDemographicsV3.getId().getExtension());
        assertEquals(creationTime, convertToDate(getDemographicsV3.getCreationTime()));
        assertEquals("RAIGT-PRSN-DMGR", getDemographicsV3.getReceiver().getDevice().getId().getExtension());
        assertEquals("BC00001013", getDemographicsV3.getReceiver().getDevice().getAsAgent().getValue()
                .getRepresentedOrganization().getValue().getId().getExtension());
        assertEquals("HNWEB", getDemographicsV3.getSender().getDevice().getId().getExtension());
        assertEquals("BC01000030", getDemographicsV3.getSender().getDevice().getAsAgent().getValue()
                .getRepresentedOrganization().getValue().getId().getExtension());
        assertEquals("HNAIADMINISTRATION", getDemographicsV3.getControlActProcess().getDataEnterer().getAssignedPerson()
                .getId().getExtension());
        assertEquals("1234567890", getDemographicsV3.getControlActProcess().getQueryByParameter()
                .getValue().getQueryByParameterPayload().getPersonId().getValue().getExtension());
    }
}
