/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hl7.v3.api;

import ca.bc.gov.moh.rtrans.entity.SystemResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.hl7.v3.CS;

/**
 *
 * @author conrad.gustafson
 */
public class AckSystemResponseProvider {

    /**
     * Create the list of SystemResponse based on input JaxbQueryAck
     * @param queryAck the HL7v3 JaxbQueryAck
     * @return the list of SystemResponse entity
     */
    public static List<SystemResponse> getSystemResponses(JaxbQueryAck queryAck) {
        CS queryResponseCode = queryAck.getQueryResponseCode();
        return createSystemResponse(queryResponseCode);
    }

    /**
     * Create the list of SystemResponse based on input CS 
     * @param queryResponseCode HL7v3 CS
     * @return the list of SystemResponse entity
     */
    private static List<SystemResponse> createSystemResponse(CS queryResponseCode) {

        List<SystemResponse> systemResponseList = new ArrayList<>();

        if (queryResponseCode == null) {
            return systemResponseList;
        }

        String code = queryResponseCode.getCode();
        if (StringUtils.isEmpty(code)) {
            return systemResponseList;
        }

        String[] parts = code.split(Pattern.quote("|"));
        final String responseCodeValue = parts[0].trim();
        final String responseCodeText = parts[1].trim();

        SystemResponse individualSystemResponse = new SystemResponse();
        individualSystemResponse.setCode(responseCodeValue);
        individualSystemResponse.setCodeText(responseCodeText);

        systemResponseList.add(individualSystemResponse);
        return systemResponseList;
    }

    public static SystemResponse createSystemResponse(final String responseCodeValue, final String responseCodeText) {

        SystemResponse systemResponse = new SystemResponse();
        
        if (StringUtils.isEmpty(responseCodeValue)) {
            return null;
        }

        systemResponse.setCode(responseCodeValue);
        systemResponse.setCodeText(responseCodeText);

        return systemResponse;
    }


}
