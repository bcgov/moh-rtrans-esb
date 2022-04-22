/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.camel.Exchange;

/**
 *
 * @author trevor.schiavone
 */
public class ErrorCodeValueMapper {
    
    public static final String CODE = ".CODE";
    public static final String MESSAGE = ".MESSAGE";
    public static final String ACK = ".ACK";
    
    private static final String V2_SUCCESS_CODE = "HJMB001I";
    private static final String V2_SUCCESS_MESSAGE = "SUCCESSFULLY COMPLETED";
    private static final String V2_SUCCESS_ACK = "AA";
    
    private static final String V2_ERROR_RESPONSE_MESSAGE = "SEVERE SYSTEM ERROR";
    private static final String GD_ERROR_RESPONSE_CODE = "HNHR509E";
    private static final String FC_ERROR_RESPONSE_CODE = "HNHR524E";
    private static final String RP_ERROR_RESPONSE_CODE = "HNHR529E";
    private static final String V2_ERROR_RESPONSE_ACK = "AR";

    private static final int V3_CODE_MINIMUM_SEGMENTS = 3;
    
    private Properties errorMapProperties;
    
    public ErrorCodeValueMapper() {
    }
    
    public ErrorCodeValueMapper(Exchange exchange) throws IOException {
       errorMapProperties = exchange.getContext().getPropertiesComponent().loadProperties();
    }

    /**
     * Strip anything after the 4th segment of the v3 code
     * @param v3Code
     * @return String 
     */
    public String stripExtraErrorCodeInfo(String v3Code) {
        
        if(v3Code == null)
            return null;
        
        Pattern pattern = Pattern.compile("BCHCIM\\.[a-zA-Z]{2}\\.[0-9]\\.[0-9]{4}");
        Matcher matcher = pattern.matcher(v3Code);
        if (matcher.find()) {
            v3Code = matcher.group(0);
        }
        return v3Code;
    }
    /**
     * Takes a v3 GetDemo response code and returns v2 r03 specific message
     * @param v3Code
     * @return
     */
    public String mapCodeToMessage(String v3Code) {
       
        String[] v3CodeSegments;
        v3Code = stripExtraErrorCodeInfo(v3Code);
        
        if (StringUtils.isBlank(v3Code)) {
            return V2_ERROR_RESPONSE_MESSAGE;
        }     
        if (errorMapProperties.getProperty(v3Code.concat(MESSAGE)) != null) {           
            
            return  errorMapProperties.getProperty(v3Code.concat(MESSAGE));         
            
        } else {
            v3CodeSegments = v3Code.split("\\.");
            if (v3CodeSegments.length >= V3_CODE_MINIMUM_SEGMENTS && (v3CodeSegments[2].equals("0") || v3CodeSegments[2].equals("1"))) {
                return V2_SUCCESS_MESSAGE;
            } else {
                return V2_ERROR_RESPONSE_MESSAGE;
            }          
        }
    }
    
    /**
     * Takes a v3 GetDemo response code and returns v2 r03 specific code
     * @param v3Code
     * @return
     */
    public String mapGDErrorCodeToCode(String v3Code) {

        String[] v3CodeSegments;
        v3Code = stripExtraErrorCodeInfo(v3Code);
        
        if (StringUtils.isBlank(v3Code)) {
            return GD_ERROR_RESPONSE_CODE;
        }     
        if (errorMapProperties.getProperty(v3Code.concat(CODE)) != null) {          
           
            return errorMapProperties.getProperty(v3Code.concat(CODE));
                  
        } else {
            v3CodeSegments = v3Code.split("\\.");
            if (v3CodeSegments.length >= V3_CODE_MINIMUM_SEGMENTS && (v3CodeSegments[2].equals("0") || v3CodeSegments[2].equals("1"))) {
                return V2_SUCCESS_CODE;
            } else {
                return GD_ERROR_RESPONSE_CODE;
            }          
        }
    }

    /**
     * Map Find Candidates v3 error code to v2 error code.
     * @param v3Code String the v3 error code
     * @return String the v2 error code
     */
    public String mapFCErrorCode(String v3Code) {
        String[] v3CodeSegments;
        v3Code = stripExtraErrorCodeInfo(v3Code);
        if (StringUtils.isBlank(v3Code)) {
            return FC_ERROR_RESPONSE_CODE;
        }
        
        if (errorMapProperties.getProperty(v3Code.concat(CODE)) != null) {
            return errorMapProperties.getProperty(v3Code.concat(CODE));
        } else {
            v3CodeSegments = v3Code.split("\\.");
            if (v3CodeSegments.length >= V3_CODE_MINIMUM_SEGMENTS && (v3CodeSegments[2].equals("0") || v3CodeSegments[2].equals("1"))) {
                return V2_SUCCESS_CODE;
            } else {
                return FC_ERROR_RESPONSE_CODE;
            }          
        }
        
    }
    
    /**
     * Map Find Candidates v3 error code and message to v2 error code and message.
     * @param v3Code String the v3 error message
     * @return String the v2 error code and message
     */
    public String mapFCErrorCodeMessage(String v3Code) {
        return mapFCErrorCode(v3Code) + mapCodeToMessage(v3Code);
    }
    
    /**
     * Takes a v3 GetDemo response code and returns v2 r03 specific code
     * @param v3Code
     * @return
     */
    public String mapRPErrorCodeToCode(String v3Code) {

        String[] v3CodeSegments;
        v3Code = stripExtraErrorCodeInfo(v3Code);
        
        if (StringUtils.isBlank(v3Code)) {
            return RP_ERROR_RESPONSE_CODE;
        }     
        if (errorMapProperties.getProperty(v3Code.concat(CODE)) != null) {          
           
            return errorMapProperties.getProperty(v3Code.concat(CODE));
                  
        } else {
            v3CodeSegments = v3Code.split("\\.");
            if (v3CodeSegments.length >= V3_CODE_MINIMUM_SEGMENTS && (v3CodeSegments[2].equals("0") || v3CodeSegments[2].equals("1"))) {
                return V2_SUCCESS_CODE;
            } else {
                return RP_ERROR_RESPONSE_CODE;
            }          
        }
    }
    
    /**
     * Takes a v3 response code and returns a v2 response ACK
     * @param v3Code
     * @return
     */
    public String mapErrorCodeToAck(String v3Code) {
        
        String[] v3CodeSegments;
        v3Code = stripExtraErrorCodeInfo(v3Code);
        
        if (StringUtils.isBlank(v3Code)) {
            return V2_ERROR_RESPONSE_ACK;
        }
        if (errorMapProperties.getProperty(v3Code.concat(ACK)) != null) {        
           
            return errorMapProperties.getProperty(v3Code.concat(ACK));         
            
        } else {
            v3CodeSegments = v3Code.split("\\.");
            if (v3CodeSegments.length >= V3_CODE_MINIMUM_SEGMENTS && (v3CodeSegments[2].equals("0") || v3CodeSegments[2].equals("1"))) {
                return V2_SUCCESS_ACK;
            } else {
                return V2_ERROR_RESPONSE_ACK;
            }          
        }
    }
}
