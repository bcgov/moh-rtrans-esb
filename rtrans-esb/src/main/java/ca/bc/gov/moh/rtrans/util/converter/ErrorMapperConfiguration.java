/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.util.converter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author trevor.schiavone
 */

public class ErrorMapperConfiguration {
    
    private static final Properties errorMapProperties = new Properties();
    private static ClassLoader classloader;
    private static InputStream is;
    
    public ErrorMapperConfiguration() throws IOException {
        classloader = Thread.currentThread().getContextClassLoader();
        is = classloader.getResourceAsStream("errorMap.properties");
        errorMapProperties.load(is);
    }
    
    public String getLookupValue(String inputMsg) { 
        return errorMapProperties.getProperty(inputMsg);
    }
    
    public Properties getErrorMapProperties() {
        return errorMapProperties;
    }
    
}
