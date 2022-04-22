/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;

import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographicsResponse;
import ca.bc.gov.moh.rtrans.service.v2.V2ServiceConstants;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R03Response;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.segment.ERR;
import ca.uhn.hl7v2.model.v24.segment.MSA;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import java.io.IOException;
import java.text.ParseException;
import javax.xml.bind.JAXBException;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.hl7.v3.HCIMINGetDemographicsResponse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author trevor.schiavone
 */
public class GetDemographicsEntityToHapiTypeConverterTest {
    
    CamelContext ctx;
    Exchange exchange;
    
    @Test
    public void testEntitytoR03() throws JAXBException, HL7Exception, IOException, ParseException {
    
        HCIMINGetDemographicsResponse getDemoResponseV3 =  GetDemoUtils.createValidV3GetDemoResponse(); 
        GetDemographicsResponse getDemoResponseEntity = GetDemographicsJaxbToEntityTypeConverter.convert(getDemoResponseV3);
        GetDemographicsEntityToHapiTypeConverter converter = new GetDemographicsEntityToHapiTypeConverter();
        
        ctx = new DefaultCamelContext();
        exchange = new DefaultExchange(ctx);
        exchange.getIn().setHeader(V2ServiceConstants.strMessageIDProperty, "19980915000031");
        exchange.getIn().setHeader(V2ServiceConstants.messageSecurity, "Train96");
        exchange.getIn().setHeader(V2ServiceConstants.messageProcessId, "ABC");
        exchange.getIn().setHeader(V2ServiceConstants.senderApplication, "BCHCIM");
        exchange.getIn().setHeader(V2ServiceConstants.senderFacility, "VIHA");
        exchange.getIn().setHeader(V2ServiceConstants.receiverApplication, "RAIGT-PRSN-DMGR");
        exchange.getIn().setHeader(V2ServiceConstants.receiverFacility, "BC0003000");
        exchange.getIn().setHeader(V2ServiceConstants.VERSION_ID, "unitTestDMMsh12");
        
        R03Response r03Response = converter.convert(getDemoResponseEntity, exchange);
                   
        MSH msh = r03Response.getMSH();
        MSA msa = r03Response.getMSA();
        ERR err = r03Response.getERR();
        PID pid = r03Response.getPID();
        ZIA zia = r03Response.getZIA();

        assertEquals("unitTestDMMsh12", msh.getMsh12_VersionID().encode());
        assertEquals("RAIGT-PRSN-DMGR", msh.getMsh3_SendingApplication().encode());
        assertEquals("BC0003000", msh.getMsh4_SendingFacility().encode());
        assertEquals("BCHCIM", msh.getMsh5_ReceivingApplication().encode());
        assertEquals("VIHA", msh.getMsh6_ReceivingFacility().encode());
        assertEquals("20170125122125", msh.getMsh7_DateTimeOfMessage().encode());
        assertEquals("Train96", msh.getMsh8_Security().encode());
        assertEquals("R03", msh.getMsh9_MessageType().encode());
        assertEquals("b3a12f0c-a332-4ec9-94b9-d8539a02df48", msh.getMsh10_MessageControlID().encode());
        assertEquals("ABC", msh.getMsh11_ProcessingID().encode());
        
        assertEquals("AA", msa.getMsa1_AcknowledgementCode().encode());
        assertEquals("19980915000031", msa.getMsa2_MessageControlID().encode());
        assertEquals("HJMB001ISUCCESSFULLY COMPLETED", msa.getMsa3_TextMessage().encode());
        
        assertEquals("HJMB001I", err.getErrorCodeAndLocation(0)
                .getEld4_CodeIdentifyingError()
                .getCe1_Identifier()
                .encode());
        assertEquals("SUCCESSFULLY COMPLETED", err.getErrorCodeAndLocation(0)
                .getEld4_CodeIdentifyingError()
                .getCe2_Text()
                .encode());
        
        assertEquals("1234567890", pid.getPid2_PatientID().getCx1_ID().encode());
        assertEquals("BC", pid.getPid2_PatientID().getCx4_AssigningAuthority().getHd1_NamespaceID().encode());
        assertEquals("PH", pid.getPid2_PatientID().getCx5_IdentifierTypeCode().encode());
        
        assertEquals("19721016", pid.getDateTimeOfBirth().getTs1_TimeOfAnEvent().encode());
        assertEquals(true, pid.getPatientDeathDateAndTime().getTs1_TimeOfAnEvent().encode().isEmpty());
        
        ///Family name trimmed to 35 characters
        assertEquals("PNEUMONOULTRAMICROSCOPICSILICOVOLCA", zia.getZIA15_ExtendedPersonName().getXpn1_FamilyName().encode());
        ///Given names trimmed to 15 characters
        assertEquals("JEAN-RENE-BAPTI", zia.getZIA15_ExtendedPersonName().getXpn2_GivenName().encode());
        assertEquals("DAWN-SHAWN-BROW", zia.getZIA15_ExtendedPersonName().getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof().encode());
        assertEquals("ANGELICA-MONTAN", zia.getZIA15_ExtendedPersonName().getXpn6_DegreeEgMD().encode());
        assertEquals("L", zia.getZIA15_ExtendedPersonName().getXpn7_NameTypeCode().encode());
        
        assertEquals("PRN", zia.getZIA17_ExtendedTelephoneNumber().getXtn2_TelecommunicationUseCode().encode());
        assertEquals("PH", zia.getZIA17_ExtendedTelephoneNumber().getXtn3_TelecommunicationEquipmentType().encode());
        assertEquals("604", zia.getZIA17_ExtendedTelephoneNumber().getXtn6_AreaCityCode().encode());
        assertEquals("8883322", zia.getZIA17_ExtendedTelephoneNumber().getXtn7_PhoneNumber().encode());
        
        assertEquals("321 SUNSHINE PL", zia.getZIA16_ExtendedAddress().getZAD1_AddressLine1().encode());
        assertEquals("NEW LINE 2", zia.getZIA16_ExtendedAddress().getZAD2_AddressLine2().encode());
        assertEquals("NEW LINE 3", zia.getZIA16_ExtendedAddress().getZAD3_AddressLine3().encode());
        assertEquals("VANCOUVER", zia.getZIA16_ExtendedAddress().getZAD20_City().encode());
        assertEquals("BC", zia.getZIA16_ExtendedAddress().getZAD21_Province().encode());
        assertEquals("V9L 4H2", zia.getZIA16_ExtendedAddress().getZAD22_PostalCode().encode());
        assertEquals("CAN", zia.getZIA16_ExtendedAddress().getZAD23_Country().encode());
        assertEquals("H", zia.getZIA16_ExtendedAddress().getZAD24_AddressType().encode());
        assertEquals("Y", zia.getZIA16_ExtendedAddress().getZAD28_ValidAddressIndicator().encode());
               
        System.out.println(exchange.getIn().getBody(String.class));
        
    }
    
    @Test
    public void testEntitytoR03NoHomePhone() throws JAXBException, HL7Exception, IOException, ParseException {
    
        HCIMINGetDemographicsResponse getDemoResponseV3 =  GetDemoUtils.createValidV3GetDemoResponse(GetDemoUtils.getNoHomePhoneResponse()); 
        GetDemographicsResponse getDemoResponseEntity = GetDemographicsJaxbToEntityTypeConverter.convert(getDemoResponseV3);
        GetDemographicsEntityToHapiTypeConverter converter = new GetDemographicsEntityToHapiTypeConverter();
        
        ctx = new DefaultCamelContext();
        exchange = new DefaultExchange(ctx);
        exchange.getIn().setHeader(V2ServiceConstants.strMessageIDProperty, "19980915000031");
        exchange.getIn().setHeader(V2ServiceConstants.messageSecurity, "Train96");
        exchange.getIn().setHeader(V2ServiceConstants.messageProcessId, "ABC");
        exchange.getIn().setHeader(V2ServiceConstants.senderApplication, "BCHCIM");
        exchange.getIn().setHeader(V2ServiceConstants.senderFacility, "VIHA");
        exchange.getIn().setHeader(V2ServiceConstants.receiverApplication, "RAIGT-PRSN-DMGR");
        exchange.getIn().setHeader(V2ServiceConstants.receiverFacility, "BC0003000");
        exchange.getIn().setHeader(V2ServiceConstants.VERSION_ID, "unitTestDMMsh12");
        
        R03Response r03Response = converter.convert(getDemoResponseEntity, exchange);                
        ZIA zia = r03Response.getZIA();
        
        assertEquals("", zia.getZIA17_ExtendedTelephoneNumber().getXtn2_TelecommunicationUseCode().encode());
        assertEquals("", zia.getZIA17_ExtendedTelephoneNumber().getXtn3_TelecommunicationEquipmentType().encode());
        assertEquals("", zia.getZIA17_ExtendedTelephoneNumber().getXtn6_AreaCityCode().encode());
        assertEquals("", zia.getZIA17_ExtendedTelephoneNumber().getXtn7_PhoneNumber().encode());    
               
        System.out.println(exchange.getIn().getBody(String.class));
        
    }
}
