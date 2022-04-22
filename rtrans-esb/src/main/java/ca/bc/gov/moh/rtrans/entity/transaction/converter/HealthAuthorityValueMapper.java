package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.util.converter.HAValueMapperConstants;
import java.util.Properties;
import org.apache.camel.Exchange;
import org.apache.camel.component.file.FileComponent;
import org.apache.commons.lang.StringUtils;


public class HealthAuthorityValueMapper extends FileComponent {
    
    private static HealthAuthorityValueMapper instance = null;
    
    private final Properties healthAuthorityValuemapProperties; 
    
    public HealthAuthorityValueMapper(Properties properties) {
        healthAuthorityValuemapProperties = properties;
    }

    public HealthAuthorityValueMapper(Exchange exchange) {
        healthAuthorityValuemapProperties = exchange.getContext().getPropertiesComponent().loadProperties();
    }
    
    
    public String mapRequestSenderApplication(String sendingApplication, String sendingFacility) {
        if (StringUtils.isEmpty(sendingFacility)) {
            return sendingFacility;
        }
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST
                    .concat(HAValueMapperConstants.SOURCE)
                    .concat(sendingFacility)) != null) {
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST
                    .concat(HAValueMapperConstants.SOURCE)
                    .concat(sendingFacility));
        } else {
            //Only using the sendingFacility if we can map it, otherwise use the sending application in this field
            //TO DO verify this behavior is correct
            //TSS 2017/07/25
            return sendingApplication;
        }
    }

    public String mapRequestSenderFacility(String v2Input) {
        if (StringUtils.isEmpty(v2Input)) {
            return v2Input;
        }      
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST
                    .concat(HAValueMapperConstants.ORG)
                    .concat(v2Input)) != null) {      
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST
                    .concat(HAValueMapperConstants.ORG)
                    .concat(v2Input));
        } else {
            return v2Input;
        }
    }
    
    public String mapRequestReceiverApplication(String v2Input) {
        if (StringUtils.isEmpty(v2Input)) {
            return v2Input;
        }
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST.concat(v2Input)) != null) {
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST.concat(v2Input));
        } else {
            return v2Input;
        }
    }
    public String mapRequestReceiverFacility(String v2Input) {
        if (StringUtils.isEmpty(v2Input)) {
            return v2Input;
        }
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST.concat(v2Input)) != null) {
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST.concat(v2Input));
        } else {
            return v2Input;
        }
    }

    //map response from HCIM to Cerner
    public String mapMSH11Segment(String envInput) {
        if (StringUtils.isEmpty(envInput)) {
            return envInput;
        }
        
        if (healthAuthorityValuemapProperties.getProperty(envInput) != null) {
            return healthAuthorityValuemapProperties.getProperty(envInput);
        } else {
            return envInput;
        }
    }

    public String mapMsa3_TextMessageRP207(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        
        String inputV3ErrorCode = "BCHCIM_RP_2_0007_V3_INPUT";
        String inputV2ErrorText = "BCHCIM_RP_2_0007_V2_TEXT";
        if (input.startsWith(healthAuthorityValuemapProperties.getProperty(inputV3ErrorCode))) {
            return healthAuthorityValuemapProperties.getProperty(inputV2ErrorText);
        } else {
            return input;
        }
    }
    
    public String mapMsa3_TextMessageRP021(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        
        String inputV3ErrorCode = "BCHCIM_RP_0_0021_V3_INPUT";
        String inputV2ErrorText = "BCHCIM_RP_0_0021_V2_TEXT";
        if (input.equals(healthAuthorityValuemapProperties.getProperty(inputV3ErrorCode))) {
            return healthAuthorityValuemapProperties.getProperty(inputV2ErrorText);
        } else {
            return input;
        }
    }
    
    public String mapMsa3_TextMessageRP222(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        
        String inputV3ErrorCode = "BCHCIM_RP_2_0022_V3_INPUT";
        String inputV2ErrorText = "BCHCIM_RP_2_0022_V2_TEXT";
        if (input.equals(healthAuthorityValuemapProperties.getProperty(inputV3ErrorCode))) {
            return healthAuthorityValuemapProperties.getProperty(inputV2ErrorText);
        } else {
            return input;
        }
    }
    
    public String mapPid4_getCx4_AssigningAuthorityMessage(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.RESPONSE.concat(input)) != null) {
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.RESPONSE.concat(input));
        } else {
            return input;
        }
    }
    
    public String mapPid4_getCx5_IdentifierTypeCode(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.RESPONSE.concat(input)) != null) {
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.RESPONSE.concat(input));
        } else {
            return input;
        }
    }
    
    public String mapPid3_getCx4_AssigningAuthorityMessage(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REVISE_PERSON_DISTRIBUTION_REQUEST_MRN.concat(input)) != null) {
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REVISE_PERSON_DISTRIBUTION_REQUEST_MRN.concat(input));
        } else {
            return input;
        }
    }
    
    /**
     * Common lookup method for mapping specific PID4 segment elements
     * (Assigning Authority) from v2 request messages.
     * 
     * @param input String - PID4 segment value
     * @return String
     */
    public String mapPid4_getCx4_AssigningAuthorityRequest(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        // replace whitespaces with '_'
        String inputReq = StringUtils.replace(input, " ", "_");
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST.concat(inputReq)) != null) {
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST.concat(inputReq));
        } else {
            return input;            
        }
    }
    
    /**
     * Common lookup method for mapping specific PID4 segment elements
     * (Identifier Type Code) from v2 request messages.
     * 
     * @param input String - PID4 segment value
     * @return String
     */
    public String mapPid4_getCx5_IdentifierTypeCodeRequest(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        // replace whitespaces with '_'
        String inputReq = StringUtils.replace(input, " ", "_");
        
        if (healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST.concat(inputReq)) != null) {
            return healthAuthorityValuemapProperties.getProperty(HAValueMapperConstants.REQUEST.concat(inputReq));
        } else {
            return input;
        }
    }
}
