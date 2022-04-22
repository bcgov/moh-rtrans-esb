/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.util.converter;

import java.util.Properties;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dan.Stepanov
 */
@Component
public class HaValueMapperConfiguration {

    @Resource(name = "haValueMapConfigProperties")
    private Properties haValueMapConfigProperties;
    private String lookupValue;

    public String getLookupValue(String inputMsg) {
        lookupValue = haValueMapConfigProperties.getProperty(inputMsg);    
        return lookupValue;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }
    
    public Properties getHaValueMapConfigProperties() {
        return haValueMapConfigProperties;
    }

    public void setHaValueMapConfigProperties(Properties haValueMapConfigProperties) {
        this.haValueMapConfigProperties = haValueMapConfigProperties;
    }
    

    
}
