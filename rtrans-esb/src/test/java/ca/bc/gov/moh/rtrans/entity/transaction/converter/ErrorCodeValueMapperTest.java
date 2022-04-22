/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;


import java.io.IOException;
import java.util.Properties;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author killian.faussart
 */
public class ErrorCodeValueMapperTest {
    
    CamelContext ctx = new DefaultCamelContext(); 
    Exchange ex = new DefaultExchange(ctx);
    
    @Before
    public void setup() throws IOException {
        Properties errorProperties = new Properties();
        errorProperties.load(ErrorCodeValueMapperTest.class.getClassLoader().getResourceAsStream("errorMap.properties"));
        ex.getContext().getPropertiesComponent().setInitialProperties(errorProperties);
    }

    /**
     * Test of stripExtraErrorCodeInfo method, of class ErrorCodeValueMapper.
     * @throws java.io.IOException
     */
    @Test
    public void testStripExtraErrorCodeInfo() throws IOException {
        System.out.println("stripExtraErrorCodeInfo:Valid");
        String v3Code = "TEST.BCHCIM.GD.0.0013.TEST";
        ErrorCodeValueMapper instance = new ErrorCodeValueMapper(ex);
        String expResult = "BCHCIM.GD.0.0013";
        String result = instance.stripExtraErrorCodeInfo(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("stripExtraErrorCodeInfo:Invalid");
        v3Code = "TEST.BCHCIM.GDD.0.0013.TEST";
        expResult = "TEST.BCHCIM.GDD.0.0013.TEST";
        result = instance.stripExtraErrorCodeInfo(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("stripExtraErrorCodeInfo:Null");
        v3Code = null;
        expResult = null;
        result = instance.stripExtraErrorCodeInfo(v3Code);
        assertEquals(expResult, result);
    }

    /**
     * Test of mapCodeToMessage method, of class ErrorCodeValueMapper.
     * @throws java.io.IOException
     */
    @Test
    public void testMapCodeToMessage() throws IOException {
        System.out.println("mapCodeToMessage:Valid");
        String v3Code = "BCHCIM.GD.0.0013";
        ErrorCodeValueMapper instance = new ErrorCodeValueMapper(ex);
        String expResult = "SUCCESSFULLY COMPLETED";
        String result = instance.mapCodeToMessage(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapCodeToMessage:Valid2");
        v3Code = "BCHCIM.RP.2.0007";
        expResult = "DATA INVALID:";
        result = instance.mapCodeToMessage(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapCodeToMessage:Invalid");
        v3Code = "BCHCIM.RPP.25.0007";
        expResult = "SEVERE SYSTEM ERROR";
        result = instance.mapCodeToMessage(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapCodeToMessage:Blank");
        v3Code = "";
        expResult = "SEVERE SYSTEM ERROR";
        result = instance.mapCodeToMessage(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapCodeToMessage:Null");
        v3Code = null;
        expResult = "SEVERE SYSTEM ERROR";
        result = instance.mapCodeToMessage(v3Code);
        assertEquals(expResult, result);
  
    }

    /**
     * Test of mapGDErrorCodeToCode method, of class ErrorCodeValueMapper.
     * @throws java.io.IOException
     */
    @Test
    public void testMapGDErrorCodeToCode() throws IOException {
        System.out.println("mapGDErrorCodeToCode:Valid");
        String v3Code = "BCHCIM.GD.0.0013";
        ErrorCodeValueMapper instance = new ErrorCodeValueMapper(ex);
        String expResult = "HJMB001I";
        String result = instance.mapGDErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapGDErrorCodeToCode:Valid2");
        v3Code = "BCHCIM.GD.0.0018";
        expResult = "HNHR512E";
        result = instance.mapGDErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapGDErrorCodeToCode:Invalid");
        v3Code = "BCHCIM.GDD.00.0013";
        expResult = "HNHR509E";
        result = instance.mapGDErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapGDErrorCodeToCode:Blank");
        v3Code = "";
        expResult = "HNHR509E";
        result = instance.mapGDErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapGDErrorCodeToCode:Null");
        v3Code = null;
        expResult = "HNHR509E";
        result = instance.mapGDErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
    }

    /**
     * Test of mapFCErrorCode method, of class ErrorCodeValueMapper.
     * @throws java.io.IOException
     */
    @Test
    public void testMapFCErrorCode() throws IOException {
        
        

        
        
        System.out.println("mapFCErrorCode:Valid");
        String v3Code = "BCHCIM.FC.0.0051";
        ErrorCodeValueMapper instance = new ErrorCodeValueMapper(ex);
        String expResult = "HJMB001I";
        String result = instance.mapFCErrorCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapFCErrorCode:Valid2");
        v3Code = "BCHCIM.FC.2.0004";
        expResult = "HNHR524E";
        result = instance.mapFCErrorCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapFCErrorCode:Blank");
        v3Code = "";
        expResult = "HNHR524E";
        result = instance.mapFCErrorCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapFCErrorCode:Null");
        v3Code = null;
        expResult = "HNHR524E";
        result = instance.mapFCErrorCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapFCErrorCode:Invalid");
        v3Code = "BCHCIM.FCC.25.0004";
        expResult = "HNHR524E";
        result = instance.mapFCErrorCode(v3Code);
        assertEquals(expResult, result);
      
    }

    /**
     * Test of mapFCErrorCodeMessage method, of class ErrorCodeValueMapper.
     * @throws java.io.IOException
     */
    @Test
    public void testMapFCErrorCodeMessage() throws IOException {
        System.out.println("mapFCErrorCodeMessage:Valid");
        String v3Code = "BCHCIM.FC.0.0052";
        ErrorCodeValueMapper instance = new ErrorCodeValueMapper(ex);
        String expResult = "HJMB001ISUCCESSFULLY COMPLETED";
        String result = instance.mapFCErrorCodeMessage(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapFCErrorCodeMessage:Valid2");
        v3Code = "BCHCIM.FC.0.0018";
        expResult = "HNHR501INOTHING FOUND MATCHING SEARCH PARAMETERS";
        result = instance.mapFCErrorCodeMessage(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapFCErrorCodeMessage:Invalid");
        v3Code = "BCHCIM.FCC.00.0018";
        expResult = "HNHR524ESEVERE SYSTEM ERROR";
        result = instance.mapFCErrorCodeMessage(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapFCErrorCodeMessage:Blank");
        v3Code = "";
        expResult = "HNHR524ESEVERE SYSTEM ERROR";
        result = instance.mapFCErrorCodeMessage(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapFCErrorCodeMessage:Null");
        v3Code = null;
        expResult = "HNHR524ESEVERE SYSTEM ERROR";
        result = instance.mapFCErrorCodeMessage(v3Code);
        assertEquals(expResult, result);
    }

    /**
     * Test of mapRPErrorCodeToCode method, of class ErrorCodeValueMapper.
     */
    @Test
    public void testMapRPErrorCodeToCode() throws IOException {
        System.out.println("mapRPErrorCodeToCode:Valid");
        String v3Code = "BCHCIM.RP.1.0578";
        ErrorCodeValueMapper instance = new ErrorCodeValueMapper(ex);
        String expResult = "HJMB001I";
        String result = instance.mapRPErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapRPErrorCodeToCode:Valid2");
        v3Code = "BCHCIM.RP.1.0031";
        expResult = "HNHR529E";
        result = instance.mapRPErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapRPErrorCodeToCode:Invalid");
        v3Code = "BCHCIM.RPP.15.0031";
        expResult = "HNHR529E";
        result = instance.mapRPErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapRPErrorCodeToCode:Blank");
        v3Code = "";
        expResult = "HNHR529E";
        result = instance.mapRPErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapRPErrorCodeToCode:Null");
        v3Code = null;
        expResult = "HNHR529E";
        result = instance.mapRPErrorCodeToCode(v3Code);
        assertEquals(expResult, result);
    }

    /**
     * Test of mapErrorCodeToAck method, of class ErrorCodeValueMapper.
     * @throws java.io.IOException
     */
    @Test
    public void testMapErrorCodeToAck() throws IOException {
        System.out.println("mapErrorCodeToAck:Valid");
        String v3Code = "BCHCIM.RP.1.0542";
        ErrorCodeValueMapper instance = new ErrorCodeValueMapper(ex);
        String expResult = "AA";
        String result = instance.mapErrorCodeToAck(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapErrorCodeToAck:Valid2");
        v3Code = "BCHCIM.RP.2.0006";
        expResult = "AE";
        result = instance.mapErrorCodeToAck(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapErrorCodeToAck:Invalid");
        v3Code = "BCHCIM.RPP.25.0006";
        expResult = "AR";
        result = instance.mapErrorCodeToAck(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapErrorCodeToAck:Blank");
        v3Code = "";
        expResult = "AR";
        result = instance.mapErrorCodeToAck(v3Code);
        assertEquals(expResult, result);
        
        System.out.println("mapErrorCodeToAck:Null");
        v3Code = null;
        expResult = "AR";
        result = instance.mapErrorCodeToAck(v3Code);
        assertEquals(expResult, result);
    }
    
}
