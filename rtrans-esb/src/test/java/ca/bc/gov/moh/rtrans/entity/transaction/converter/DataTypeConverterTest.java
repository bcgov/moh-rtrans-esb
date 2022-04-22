/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.GenderAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderValues;
import ca.bc.gov.moh.rtrans.entity.PersonNameTypes;
import ca.uhn.hl7v2.HL7Exception;
import static org.mockito.Mockito.*;
import ca.uhn.hl7v2.model.v24.datatype.IS;
import java.util.Calendar;
import java.util.Date;
import org.hl7.v3.TS;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author killian.faussart
 */
public class DataTypeConverterTest {

    /**
     * Test of convertIsAddressValid method, of class DataTypeConverter.
     */
    @Test
    public void testConvertIsAddressValid() {
        System.out.println("convertIsAddressValid - N");
        boolean isValid = false;
        String expResult = "N";
        String result = DataTypeConverter.convertIsAddressValid(isValid);
        assertEquals(expResult, result);
        
        System.out.println("convertIsAddressValid - Y");
        isValid = true;
        expResult = "Y";
        result = DataTypeConverter.convertIsAddressValid(isValid);
        assertEquals(expResult, result);
    }

    /**
     * Test of convertToDate method, of class DataTypeConverter.
     */
    @Test
    public void testConvertToDate_String() {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH,1);
        cal.set(Calendar.DAY_OF_YEAR,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        
        System.out.println("convertToDate: yyyyMMdd");
        String value = "20170101";
        Date expResult = cal.getTime();
        Date result = DataTypeConverter.convertToDate(value);
        assertEquals(expResult.toString(), result.toString());
        
        System.out.println("convertToDate: yyyyMMddHH");
        cal.set(Calendar.HOUR_OF_DAY,14);
        value = "2017010114";
        expResult = cal.getTime();
        result = DataTypeConverter.convertToDate(value);
        assertEquals(expResult.toString(), result.toString());
        
        System.out.println("convertToDate: yyyyMMddHHmm");
        cal.set(Calendar.HOUR_OF_DAY,14);
        cal.set(Calendar.MINUTE, 12);
        value = "201701011412";
        expResult = cal.getTime();
        result = DataTypeConverter.convertToDate(value);
        assertEquals(expResult.toString(), result.toString());
        
        System.out.println("convertToDate: yyyyMMddHHmmss.S");
        cal.set(Calendar.HOUR_OF_DAY,14);
        cal.set(Calendar.MINUTE, 12);
        cal.set(Calendar.SECOND,54);
        cal.set(Calendar.MILLISECOND,5);
        value = "20170101141254.5";
        expResult = cal.getTime();
        result = DataTypeConverter.convertToDate(value);
        assertEquals(expResult.toString(), result.toString());
        
        System.out.println("convertToDate: unparseable");
        value = "20175487";
        boolean exception = false;
        try{
            DataTypeConverter.convertToDate(value);
        }catch(RuntimeException ex){
            exception = true;
        }
        assertTrue(exception);
        
        System.out.println("convertToDate: yyyy not accepted");
        value = "2017";
        exception = false;
        try{
            DataTypeConverter.convertToDate(value);
        }catch(RuntimeException ex){
            exception = true;
            assertEquals("The date format for 2017 is not accepted", ex.getMessage());
        }
        assertTrue(exception);
        
        System.out.println("convertToDate: yyyyMM not accepted");
        value = "201701";
        exception = false;
        try{
            DataTypeConverter.convertToDate(value);
        }catch(RuntimeException ex){
            exception = true;
            assertEquals("The date format for 201701 is not accepted", ex.getMessage());
        }
        
        assertTrue(exception);
    }

    /**
     * Test of convertToDate method, of class DataTypeConverter.
     */
    @Test
    //TODO add the unit test where TS is null
    public void testConvertToDate_TS() {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH,1);
        cal.set(Calendar.DAY_OF_YEAR,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        
        System.out.println("convertToDate");
        TS ts = new TS();
        ts.setValue("20170101");
        Date expResult = cal.getTime();
        Date result = DataTypeConverter.convertToDate(ts);
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of convertToTS method, of class DataTypeConverter.
     */
    @Test
    public void testConvertToTS() {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH,1);
        cal.set(Calendar.DAY_OF_YEAR,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        
        System.out.println("convertToTS");
        Date creationTime = cal.getTime();
        String expResult = "20170101000000";
        String result = DataTypeConverter.convertToTS(creationTime);
        assertEquals(expResult, result);
    }

    /**
     * Test of convertToDateTS method, of class DataTypeConverter.
     */
    @Test
    public void testConvertToDateTS() {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH,1);
        cal.set(Calendar.DAY_OF_YEAR,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        
        System.out.println("convertToDateTS");
        Date birthTime = cal.getTime();
        String expResult = "20170101";
        String result = DataTypeConverter.convertToDateTS(birthTime);
        assertEquals(expResult, result);
    }

    /**
     * Test of convertV2ValueToGender method, of class DataTypeConverter.
     * @throws ca.uhn.hl7v2.HL7Exception
     */
    @Test
    public void testConvertV2ValueToGender_IS1() throws HL7Exception {
        
        System.out.println("convertV2ValueToGender:Female");
        IS v2Value = mock(IS.class); 
        when(v2Value.encode()).thenReturn("F");
        GenderAttribute expResult = new GenderAttribute(GenderValues.Female);
        GenderAttribute result = DataTypeConverter.convertV2ValueToGender(v2Value);
        assertEquals(expResult.getValue(), result.getValue());
        
        System.out.println("convertV2ValueToGender:Male");
        when(v2Value.encode()).thenReturn("M");
        expResult = new GenderAttribute(GenderValues.Male);
        result = DataTypeConverter.convertV2ValueToGender(v2Value);
        assertEquals(expResult.getValue(), result.getValue());
        
        System.out.println("convertV2ValueToGender:Unknown");
        when(v2Value.encode()).thenReturn("U");
        expResult = new GenderAttribute(GenderValues.Unknown);
        result = DataTypeConverter.convertV2ValueToGender(v2Value);
        assertEquals(expResult.getValue(), result.getValue());
        
        System.out.println("convertV2ValueToGender:NotUniquelyIdentified");
        when(v2Value.encode()).thenReturn("O");
        expResult = null;
        result = DataTypeConverter.convertV2ValueToGender(v2Value);
        assertEquals(expResult, result);
        
        System.out.println("convertV2ValueToGender:Null");
        when(v2Value.encode()).thenReturn(null);
        result = DataTypeConverter.convertV2ValueToGender(v2Value);
        assertNull(result);
        
        System.out.println("convertV2ValueToGender:null");
        when(v2Value.encode()).thenReturn("T");
        result = DataTypeConverter.convertV2ValueToGender(v2Value);
        assertNull(result);
    }

    /**
     * Test of convertPersonNameTypeValue method, of class DataTypeConverter.
     */
    @Test
    public void testConvertPersonNameTypeValue() {
        System.out.println("convertPersonNameTypeValue:Declared");
        PersonNameTypes personNameTypeValue = PersonNameTypes.Declared;
        String expResult = "L";
        String result = DataTypeConverter.convertPersonNameTypeValue(personNameTypeValue);
        assertEquals(expResult, result);
        
        System.out.println("convertPersonNameTypeValue:Documented");
        personNameTypeValue = PersonNameTypes.Documented;
        expResult = "C";
        result = DataTypeConverter.convertPersonNameTypeValue(personNameTypeValue);
        assertEquals(expResult, result);
        
        System.out.println("convertPersonNameTypeValue:Null");
        personNameTypeValue = null;
        result = DataTypeConverter.convertPersonNameTypeValue(personNameTypeValue);
        assertNull(result);
    }

    /**
     * Test of convertCommunicationType method, of class DataTypeConverter.
     */
    @Test
    public void testConvertCommunicationTypeToV2() {
        System.out.println("convertCommunicationType:Home");
        CommunicationTypes communicationType = CommunicationTypes.Home;
        String expResult = "PH";
        String result = DataTypeConverter.convertCommunicationTypeToV2(communicationType);
        assertEquals(expResult, result);
       
        
        System.out.println("convertCommunicationType:Null");
        communicationType = null;
        result = DataTypeConverter.convertCommunicationTypeToV2(communicationType);
        assertNull(result);
    }

    /**
     * Test of convertAddressTypeValue method, of class DataTypeConverter.
     */
    @Test
    public void testConvertAddressTypeValue() {
        System.out.println("convertAddressTypeValue:Home");
        AddressTypes type = AddressTypes.Home;
        String expResult = "PHYS";
        String result = DataTypeConverter.convertAddressTypeValue(type);
        assertEquals(expResult, result);
        
        System.out.println("convertAddressTypeValue:Mail");
        type = AddressTypes.Mail;
        expResult = "PST";
        result = DataTypeConverter.convertAddressTypeValue(type);
        assertEquals(expResult, result);
        
        System.out.println("convertAddressTypeValue:Work");
        type = AddressTypes.Work;
        result = DataTypeConverter.convertAddressTypeValue(type);
        assertNull(result);
        
        System.out.println("convertAddressTypeValue:Null");
        type = null;
        result = DataTypeConverter.convertAddressTypeValue(type);
        assertNull(result);
    }

    /**
     * Test of convertAddressTypeValueToV2 method, of class DataTypeConverter.
     */
    @Test
    public void testConvertAddressTypeValueToV2() {
        System.out.println("convertAddressTypeValueToV2:Home");
        AddressTypes type = AddressTypes.Home;
        String expResult = "H";
        String result = DataTypeConverter.convertAddressTypeValueToV2(type);
        assertEquals(expResult, result);
        
        System.out.println("convertAddressTypeValueToV2:Mail");
        type = AddressTypes.Mail;
        expResult = "M";
        result = DataTypeConverter.convertAddressTypeValueToV2(type);
        assertEquals(expResult, result);
        
        System.out.println("convertAddressTypeValueToV2:Work");
        type = AddressTypes.Work;
        result = DataTypeConverter.convertAddressTypeValueToV2(type);
        assertNull(result);
        
        System.out.println("convertAddressTypeValueToV2:Null");
        type = null;
        result = DataTypeConverter.convertAddressTypeValueToV2(type);
        assertNull(result);
    }

    /**
     * Test of convertStringToDate method, of class DataTypeConverter.
     */
    @Test
    public void testConvertStringToDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH,1);
        cal.set(Calendar.DAY_OF_YEAR,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        
        System.out.println("convertStringToDate: yyyyMMdd");
        String value = "20170101";
        Date expResult = cal.getTime();
        Date result = DataTypeConverter.convertStringToDate(value);
        assertEquals(expResult.toString(), result.toString());
        
        System.out.println("convertStringToDate: yyyyMMddHH");
        cal.set(Calendar.HOUR_OF_DAY,14);
        value = "2017010114";
        expResult = cal.getTime();
        result = DataTypeConverter.convertStringToDate(value);
        assertEquals(expResult.toString(), result.toString());
        
        System.out.println("convertStringToDate: yyyyMMddHHmm");
        cal.set(Calendar.HOUR_OF_DAY,14);
        cal.set(Calendar.MINUTE, 12);
        value = "201701011412";
        expResult = cal.getTime();
        result = DataTypeConverter.convertStringToDate(value);
        assertEquals(expResult.toString(), result.toString());
        
        System.out.println("convertStringToDate: yyyyMMddHHmmss.S");
        cal.set(Calendar.HOUR_OF_DAY,14);
        cal.set(Calendar.MINUTE, 12);
        cal.set(Calendar.SECOND,54);
        cal.set(Calendar.MILLISECOND,5);
        value = "20170101141254.5";
        expResult = cal.getTime();
        result = DataTypeConverter.convertStringToDate(value);
        assertEquals(expResult.toString(), result.toString());
        
        System.out.println("convertStringToDate: unparseable");
        value = "20175487";
        boolean exception = false;
        try{
            DataTypeConverter.convertStringToDate(value);
        }catch(RuntimeException ex){
            exception = true;
        }
        
        assertTrue(exception);
        
        System.out.println("convertStringToDate: yyyy unparseable");
        value = "2017";
        exception = false;
        try{
            DataTypeConverter.convertStringToDate(value);
        }catch(RuntimeException ex){
            exception = true;
        }
        
        assertTrue(exception);
        
        System.out.println("convertStringToDate: yyyyMM unparseable");
        value = "201701";
        exception = false;
        try{
            DataTypeConverter.convertStringToDate(value);
        }catch(RuntimeException ex){
            exception = true;
        }
        
        assertTrue(exception);
    }
    
    /**
     * Test of convertGenderValueToV2 method, of class DataTypeConverter.
     */
    @Test
    public void testConvertGenderValueToV2() {
        {
            System.out.println("convertGenderValueToV2:Male");
            GenderAttribute genderAttribute = new GenderAttribute(GenderValues.Male);
            String expResult = DataTypeConverter.GENDER_MALE_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:Female");
            GenderAttribute genderAttribute = new GenderAttribute(GenderValues.Female);
            String expResult = DataTypeConverter.GENDER_FEMALE_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:NotUniquelyIdentified");
            GenderAttribute genderAttribute = new GenderAttribute(GenderValues.NotUniquelyIdentified);
            String expResult = DataTypeConverter.GENDER_UNKNOWN_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:Unknown");
            GenderAttribute genderAttribute = new GenderAttribute(GenderValues.Unknown);
            String expResult = DataTypeConverter.GENDER_UNKNOWN_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:NULL");
            GenderAttribute genderAttribute = null;
            String expResult = null;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:Masked");
            GenderAttribute genderAttribute = new GenderAttribute(GenderValues.Unknown);
            genderAttribute.setMasked(Boolean.TRUE);
            String expResult = DataTypeConverter.GENDER_UNKNOWN_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:Masked");
            GenderAttribute genderAttribute = new GenderAttribute(GenderValues.Male);
            genderAttribute.setMasked(Boolean.TRUE);
            String expResult = DataTypeConverter.GENDER_UNKNOWN_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:Masked");
            GenderAttribute genderAttribute = new GenderAttribute(GenderValues.Female);
            genderAttribute.setMasked(Boolean.TRUE);
            String expResult = DataTypeConverter.GENDER_UNKNOWN_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:Masked");
            GenderAttribute genderAttribute = new GenderAttribute(GenderValues.NotUniquelyIdentified);
            genderAttribute.setMasked(Boolean.TRUE);
            String expResult = DataTypeConverter.GENDER_UNKNOWN_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
        {
            System.out.println("convertGenderValueToV2:Masked");
            GenderAttribute genderAttribute = new GenderAttribute(null);
            genderAttribute.setMasked(Boolean.TRUE);
            String expResult = DataTypeConverter.GENDER_UNKNOWN_V2;
            String result = DataTypeConverter.convertGenderValueToV2(genderAttribute);
            assertEquals(expResult, result);
        }
    }
    
}
