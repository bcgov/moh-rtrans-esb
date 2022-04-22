/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.DateAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderValues;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonNameTypes;
import ca.bc.gov.moh.rtrans.entity.PhoneAttribute;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographics;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R03Response;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.uhn.hl7v2.model.v24.datatype.CX;
import ca.uhn.hl7v2.model.v24.datatype.SAD;
import ca.uhn.hl7v2.model.v24.datatype.XAD;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.datatype.ST;
import ca.uhn.hl7v2.model.v24.datatype.IS;
import ca.uhn.hl7v2.model.v24.datatype.ID;
import ca.uhn.hl7v2.model.v24.datatype.HD;
import ca.uhn.hl7v2.model.v24.datatype.DT;
import ca.uhn.hl7v2.model.v24.datatype.TS;
import ca.uhn.hl7v2.model.v24.datatype.TN;
import ca.uhn.hl7v2.model.v24.datatype.NM;
import ca.uhn.hl7v2.model.v24.datatype.XPN;
import ca.uhn.hl7v2.model.v24.datatype.XTN;
import ca.uhn.hl7v2.model.v24.datatype.FN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.mockito.Mockito.*;

/**
 * 
 * @author killian.faussart
 */
public class HapiEntityConverterV24Test {

    private static final PID pid = mock(PID.class);
    private static final XAD xad = mock(XAD.class);
    private static final SAD sad = mock(SAD.class);
    private static final ST  st = mock(ST.class);
    private static final IS  is = mock(IS.class);
    private static final ID  id = mock(ID.class);
    private static final ID  idIypeIdentifier = mock(ID.class);
    private static final ID  idAliasTypeCode = mock(ID.class);
    private static final ID  idNameTypeCode = mock(ID.class);
    private static final ID  idAddressType = mock(ID.class);
    private static final ID  idComType = mock(ID.class);
    private static final CX  cx = mock(CX.class);
    private static final HD  hd = mock(HD.class);
    private static final DT  dt = mock(DT.class);
    private static final TS  ts = mock(TS.class);
    private static final TN  tn = mock(TN.class);
    private static final NM  nm = mock(NM.class);
    private static final FN  fn = mock(FN.class);
    private static final XPN xpn = mock(XPN.class);
    private static final XPN xpn2 = mock(XPN.class);
    private static final XTN xtn = mock(XTN.class);
    
    Properties testProperties = new Properties();
    
    @BeforeClass
    //Not super clear I admit that
    public static void setUpMock() throws Exception{
       
        //Random values
        when(sad.encode()).thenReturn("testSAD");
        when(st.encode()).thenReturn("testST");
        when(hd.encode()).thenReturn("testHD");
        when(fn.encode()).thenReturn("testFN");
        when(id.encode()).thenReturn("testID");
        when(tn.getValue()).thenReturn("testTN");
        when(tn.encode()).thenReturn("testTN");
        
        //Has to be a number
        when(nm.getValue()).thenReturn("1");
        when(nm.encode()).thenReturn("1");
        
        //Have to be dates
        when(dt.encode()).thenReturn("20170728");
        when(ts.encode()).thenReturn("19890514");
        
        //Important value set to meet the different conditions
        when(idIypeIdentifier.encode()).thenReturn("MRN");
        when(idAliasTypeCode.encode()).thenReturn("PREFERRED");
        when(idNameTypeCode.encode()).thenReturn("L");
        when(idAddressType.encode()).thenReturn("Home");
        when(idComType.encode()).thenReturn("BUS");
        when(is.encode()).thenReturn("F");
        
        when(cx.getCx1_ID()).thenReturn(st);
        when(cx.getCx4_AssigningAuthority()).thenReturn(hd);
        when(cx.getCx8_ExpirationDate()).thenReturn(dt);
        when(cx.getCx5_IdentifierTypeCode()).thenReturn(idIypeIdentifier);
        
        when(xpn.getNameTypeCode()).thenReturn(idAliasTypeCode);
        when(xpn.getGivenName()).thenReturn(st);
        when(xpn.getFamilyName()).thenReturn(fn);
        when(xpn.getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof()).thenReturn(st);
        when(xpn.getXpn5_PrefixEgDR()).thenReturn(st);
        when(xpn.getXpn4_SuffixEgJRorIII()).thenReturn(st);
        when(xpn.getXpn6_DegreeEgMD()).thenReturn(is);
        
        when(xpn2.getNameTypeCode()).thenReturn(idNameTypeCode);
        when(xpn2.getGivenName()).thenReturn(st);
        when(xpn2.getFamilyName()).thenReturn(fn);
        when(xpn2.getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof()).thenReturn(st);
        when(xpn2.getXpn5_PrefixEgDR()).thenReturn(st);
        when(xpn2.getXpn4_SuffixEgJRorIII()).thenReturn(st);
        when(xpn2.getXpn6_DegreeEgMD()).thenReturn(is);
        
        when(xtn.getEmailAddress()).thenReturn(st);
        when(xtn.getTelecommunicationUseCode()).thenReturn(idComType);
        when(xtn.getAreaCityCode()).thenReturn(nm);
        when(xtn.getPhoneNumber()).thenReturn(nm);
        when(xtn.getCountryCode()).thenReturn(nm);
        when(xtn.getExtension()).thenReturn(nm);
        when(xtn.get9999999X99999CAnyText()).thenReturn(tn);
        
        when(xad.getXad1_StreetAddress()).thenReturn(sad);
        when(xad.getXad2_OtherDesignation()).thenReturn(st);
        when(xad.getCity()).thenReturn(st);
        when(xad.getStateOrProvince()).thenReturn(st);
        when(xad.getCountry()).thenReturn(id);
        when(xad.getZipOrPostalCode()).thenReturn(st);
        when(xad.getAddressType()).thenReturn(idAddressType);
        
        when(pid.getPid3_PatientIdentifierList()).thenReturn(new CX[]{cx});
        when(pid.getPid4_AlternatePatientIDPID()).thenReturn(new CX[]{cx});
        when(pid.getDateTimeOfBirth()).thenReturn(ts);
        when(pid.getPatientDeathDateAndTime()).thenReturn(ts);
        when(pid.getAdministrativeSex()).thenReturn(is);
        when(pid.getBirthOrder()).thenReturn(nm);
        when(pid.getPatientName()).thenReturn(new XPN[]{xpn,xpn2});
        when(pid.getPatientAlias()).thenReturn(new XPN[]{xpn});
        when(pid.getPid11_PatientAddress()).thenReturn(new XAD[]{xad});
        when(pid.getPhoneNumberHome()).thenReturn(new XTN[]{xtn});
        when(pid.getPhoneNumberBusiness()).thenReturn(new XTN[]{xtn});
    }
    
    @Before
    public void reset() throws Exception {
        when(st.getValue()).thenReturn(null);
        when(idNameTypeCode.encode()).thenReturn("L");
    }
    
    
    /**
     * Test of mapPIDToPerson method, of class HapiEntityConverterV24.
     * @throws java.lang.Exception
     */
    @Test
    public void testMapPIDToPerson_PID_Person() throws Exception {
        
        System.out.println("mapPIDToPerson:Valid");
        Person person = new Person();
        HapiEntityConverterV24 instance = new HapiEntityConverterV24(testProperties);
        instance.mapPIDToPerson(pid, person, false);
                
        //General
        assertEquals("Sun May 14 00:00:00 PDT 1989",person.getBirthDate().getValue().toString());
        assertEquals("Sun May 14 00:00:00 PDT 1989",person.getDeathDate().getValue().toString());
        assertEquals(1,person.getBirthOrder().getValue());
        assertEquals(GenderValues.Female,person.getGender().getValue());
        assertEquals(0,person.getEmail().size());
        
        //Identifiers
        assertEquals(1,person.getIdentifier().size());
        assertEquals("testHD",person.getIdentifier().get(0).getSource());
        assertEquals("testST",person.getIdentifier().get(0).getValue());
        assertEquals(IdentifierTypes.BCPHN ,person.getIdentifier().get(0).getType());
        
        //Names
        assertEquals(1,person.getName().size());
        assertEquals("F",person.getName().get(0).getDegree());
        assertEquals("TestST",person.getName().get(0).getFirstName());
        assertEquals("TestFN",person.getName().get(0).getLastName());
        assertEquals("TestST",person.getName().get(0).getMiddleName());
        assertEquals("testST",person.getName().get(0).getPrefix());
        assertEquals("testST",person.getName().get(0).getSuffix());
        assertEquals("TestST",person.getName().get(0).getPreferredGivenName());
        assertEquals(PersonNameTypes.Declared,person.getName().get(0).getType());
              
    }

    /**
     * Test of mapPIDToPerson method, of class HapiEntityConverterV24.
     * @throws java.lang.Exception
     */
    @Test
    public void testMapPIDToPerson_3args() throws Exception {
        System.out.println("mapPIDToPerson:Valid");
        
        //Setting this to PREFERRED to enter the tested 
        //condition only because mapAllNameTypes=true
        when(idNameTypeCode.encode()).thenReturn("PREFERRED");
        
        Person person = new Person();
        boolean mapAllNameTypes = true;
        HapiEntityConverterV24 instance = new HapiEntityConverterV24(new Properties());
        instance.mapPIDToPerson(pid, person, mapAllNameTypes);

        //Names
        assertEquals(2,person.getName().size());
        assertEquals("F",person.getName().get(0).getDegree());
        assertEquals("TestST",person.getName().get(0).getFirstName());
        assertEquals("TestFN",person.getName().get(0).getLastName());
        assertEquals("TestST",person.getName().get(0).getMiddleName());
        assertEquals("testST",person.getName().get(0).getPrefix());
        assertEquals("testST",person.getName().get(0).getSuffix());
        assertEquals("TestST",person.getName().get(0).getPreferredGivenName());
        assertNull(person.getName().get(0).getType());
        
        assertEquals("F",person.getName().get(1).getDegree());
        assertEquals("TestST",person.getName().get(1).getFirstName());
        assertEquals("TestFN",person.getName().get(1).getLastName());
        assertEquals("TestST",person.getName().get(1).getMiddleName());
        assertEquals("testST",person.getName().get(1).getPrefix());
        assertEquals("testST",person.getName().get(1).getSuffix());
        assertEquals("TestST",person.getName().get(1).getPreferredGivenName());
        assertNull(person.getName().get(0).getType());
       
    }

    /**
     * Test of mapPersonToPID method, of class HapiEntityConverterV24.
     * @throws java.lang.Exception
     */
    @Test
    public void testMapPersonToPID() throws Exception {
        System.out.println("mapPersonToPID");
        
        //GIVEN
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1989);
        cal.set(Calendar.MONTH,4);
        cal.set(Calendar.DAY_OF_MONTH,14);
        cal.set(Calendar.HOUR,0);
        
        Person person = new Person();
        person.setGender(new GenderAttribute(GenderValues.Female));
        person.setBirthDate(new DateAttribute(cal.getTime()));
        person.setDeathDate(new DateAttribute(cal.getTime()));
        List<IdentifierAttribute> ident = new ArrayList<>();
        ident.add(new IdentifierAttribute("9145678923", null, IdentifierTypes.BCPHN));
        person.setIdentifier(ident);
        
        R03Response r03 = new R03Response();
        r03.initQuickstart("R03", null, "T");
        PID pID = r03.getPID();
        
        //WHEN
        HapiEntityConverterV24 instance = new HapiEntityConverterV24(testProperties);
        instance.mapPersonToPID(person, pID);
        
        //THEN
        assertEquals("F",pID.getPid8_AdministrativeSex().getValue());
        assertEquals("19890514",pID.getDateTimeOfBirth().getTs1_TimeOfAnEvent().getValue());
        assertEquals("19890514",pID.getPatientDeathDateAndTime().getTs1_TimeOfAnEvent().getValue());
        assertEquals(null ,pID.getPid1_SetIDPID().getValue());
        assertEquals("9145678923",pID.getPid2_PatientID().getCx1_ID().getValue());
        assertEquals("BC",pID.getPid2_PatientID().getCx4_AssigningAuthority().getHd1_NamespaceID().getValue());
        assertEquals("PH",pID.getPid2_PatientID().getCx5_IdentifierTypeCode().getValue());
    }

    /**
     * Test of mapPersonToZIA method, of class HapiEntityConverterV24.
     * @throws java.lang.Exception
     */
    @Test
    public void testMapPersonToZIA() throws Exception {
        System.out.println("mapPersonToZIA:Empty");
        
        //GIVEN
        Person person = new Person();
        person.setName(null);
        person.setPhone(null);
        person.setAddress(new ArrayList<>());
        
        R03Response r03 = new R03Response();
        r03.initQuickstart("R03", null, "T");
        ZIA zia = r03.getZIA();
        
        //WHEN
        HapiEntityConverterV24 instance = new HapiEntityConverterV24(testProperties);
        instance.mapPersonToZIA(person, zia);
        
        //THEN
        assertEquals("",zia.getExtendedPersonName().getXpn1_FamilyName().encode());
        assertEquals("",zia.getExtendedPersonName().getXpn2_GivenName().encode());
        assertEquals("",zia.getExtendedPersonName().getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof().encode());
        assertEquals("",zia.getExtendedPersonName().getXpn6_DegreeEgMD().encode());
        assertEquals("",zia.getExtendedPersonName().getXpn7_NameTypeCode().encode());
        
        assertEquals("",zia.getZIA17_ExtendedTelephoneNumber().getXtn2_TelecommunicationUseCode().encode());
        assertEquals("",zia.getZIA17_ExtendedTelephoneNumber().getXtn3_TelecommunicationEquipmentType().encode());
        assertEquals("",zia.getZIA17_ExtendedTelephoneNumber().getXtn6_AreaCityCode().encode());
        assertEquals("",zia.getZIA17_ExtendedTelephoneNumber().getXtn7_PhoneNumber().encode());
        
        
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD1_AddressLine1().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD2_AddressLine2().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD3_AddressLine3().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD20_City().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD21_Province().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD22_PostalCode().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD23_Country().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD24_AddressType().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD28_ValidAddressIndicator().encode());
        
        
        System.out.println("mapPersonToZIA:Valid");
        
        //GIVEN
        person = new Person();
        List<PersonNameAttribute> names = new ArrayList<>();
        PersonNameAttribute name = new PersonNameAttribute();
        name.setFirstName("FirstName");
        name.setLastName("LastName");
        name.setMiddleName("MiddleName");
        name.setTitle("Darling");
        names.add(name);
        person.setName(names);
        
        List<PhoneAttribute> phones = new ArrayList<>();
        PhoneAttribute phone = new PhoneAttribute();
        phone.setAreaCode("AreaCode");
        phone.setNumber("Number");
        phone.setType(CommunicationTypes.Home);
        phones.add(phone);
        person.setPhone(phones);
        
        List<AddressAttribute> addresses = new ArrayList<>();
        AddressAttribute address = new AddressAttribute();
        address.setCity("City");
        address.setCountry("CA");
        address.setPostalCode("PostalCode");
        address.setProvince("Province");
        address.setStreetAddressLines(Arrays.asList("Street Address Line 1"));
        address.setIsVerified(true);
        address.setType(AddressTypes.Home);
        addresses.add(address);
        person.setAddress(addresses);
        
        r03 = new R03Response();
        r03.initQuickstart("R03", null, "T");
        zia = r03.getZIA();
        
        //WHEN
        instance.mapPersonToZIA(person, zia);
        
        //THEN
        assertEquals("LastName",zia.getExtendedPersonName().getXpn1_FamilyName().encode());
        assertEquals("FirstName",zia.getExtendedPersonName().getXpn2_GivenName().encode());
        assertEquals("MiddleName",zia.getExtendedPersonName().getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof().encode());
        assertEquals("Darling",zia.getExtendedPersonName().getXpn6_DegreeEgMD().encode());
        assertEquals("L",zia.getExtendedPersonName().getXpn7_NameTypeCode().encode());
        
        assertEquals("PRN",zia.getZIA17_ExtendedTelephoneNumber().getXtn2_TelecommunicationUseCode().encode());
        assertEquals("PH",zia.getZIA17_ExtendedTelephoneNumber().getXtn3_TelecommunicationEquipmentType().encode());
        assertEquals("AreaCode",zia.getZIA17_ExtendedTelephoneNumber().getXtn6_AreaCityCode().encode());
        assertEquals("Number",zia.getZIA17_ExtendedTelephoneNumber().getXtn7_PhoneNumber().encode());
        
        
        assertEquals("Street Address Line 1",zia.getZIA16_ExtendedAddress().getZAD1_AddressLine1().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD2_AddressLine2().encode());
        assertEquals("",zia.getZIA16_ExtendedAddress().getZAD3_AddressLine3().encode());
        assertEquals("City",zia.getZIA16_ExtendedAddress().getZAD20_City().encode());
        assertEquals("Province",zia.getZIA16_ExtendedAddress().getZAD21_Province().encode());
        assertEquals("PostalCode",zia.getZIA16_ExtendedAddress().getZAD22_PostalCode().encode());
        assertEquals("CAN",zia.getZIA16_ExtendedAddress().getZAD23_Country().encode());
        assertEquals("H",zia.getZIA16_ExtendedAddress().getZAD24_AddressType().encode());
        assertEquals("Y",zia.getZIA16_ExtendedAddress().getZAD28_ValidAddressIndicator().encode());
        
    }

    /**
     * Test of mapMSHToRequestMessage method, of class HapiEntityConverterV24.
     * @throws java.lang.Exception
     */
    @Test
    public void testMapMSHToRequestMessage() throws Exception {
        System.out.println("mapMSHToRequestMessage:ValidDifferentDateFormat");
        //GIVEN        
        R03Response r03 = new R03Response();
        r03.initQuickstart("R03", null, "T");
        MSH msh = r03.getMSH();
        msh.getMsh10_MessageControlID().parse("19980915000013");
        msh.getMsh3_SendingApplication().parse("HNCLIENT");
        msh.getMsh4_SendingFacility().parse("BC01000088");
        msh.getMsh5_ReceivingApplication().parse("RAIGT-PRSN-DMGR");
        msh.getMsh6_ReceivingFacility().parse("BC000010000");
        msh.getMsh7_DateTimeOfMessage().parse("20120816140512.1234");
        msh.getMsh8_Security().parse("some.test.user");
        //WHEN
        RequestMessage message = new GetDemographics();
        HapiEntityConverterV24 instance = new HapiEntityConverterV24(testProperties);
        instance.mapMSHToRequestMessage(msh, message);
        //THEN
        assertEquals("19980915000013",message.getMessageId());
        assertEquals("RAIGT-PRSN-DMGR",message.getReceiver().get(0).getSystemName());
        assertEquals("BC000010000",message.getReceiver().get(0).getOrganization());
        assertEquals("HNCLIENT",message.getSender().getSystemName());
        assertEquals("BC01000088",message.getSender().getOrganization());
        assertEquals("Thu Aug 16 14:05:12 PDT 2012",message.getCreationTime().toString());
        assertEquals("some.test.user",message.getAuthor().getUser().getUserId());
        
        
        //GIVEN
        msh.getMsh7_DateTimeOfMessage().parse("20120816");    
        //WHEN
        message = new GetDemographics();
        instance.mapMSHToRequestMessage(msh, message);
        //THEN
        assertEquals("Thu Aug 16 00:00:00 PDT 2012",message.getCreationTime().toString());
        
        //GIVEN
        msh.getMsh7_DateTimeOfMessage().parse("2012081614");    
        //WHEN
        message = new GetDemographics();
        instance.mapMSHToRequestMessage(msh, message);
        //THEN
        assertEquals("Thu Aug 16 14:00:00 PDT 2012",message.getCreationTime().toString());
        
        //GIVEN
        msh.getMsh7_DateTimeOfMessage().parse("201208161405");    
        //WHEN
        message = new GetDemographics();
        instance.mapMSHToRequestMessage(msh, message);
        //THEN
        assertEquals("Thu Aug 16 14:05:00 PDT 2012",message.getCreationTime().toString());
        
        //GIVEN
        msh.getMsh7_DateTimeOfMessage().parse("20120816140512");    
        //WHEN
        message = new GetDemographics();
        instance.mapMSHToRequestMessage(msh, message);
        //THEN
        assertEquals("Thu Aug 16 14:05:12 PDT 2012",message.getCreationTime().toString());
        
        //GIVEN
        msh.getMsh7_DateTimeOfMessage().parse("120816");    
        //WHEN
        message = new GetDemographics();
        boolean exception = false;
        try{
        instance.mapMSHToRequestMessage(msh, message);
        }catch(RuntimeException ex){
            exception = true;
            assertEquals("The date format for 120816 is not accepted", ex.getMessage());
        }
        
        //THEN
        assertTrue(exception);

    }
    
}
