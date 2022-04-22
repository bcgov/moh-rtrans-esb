/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.ha.service;

import static ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessorConfig.TRANSACTION_HEADER_KEY;
import static ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessorConfig.TRANSACTION_ID_HEADER_KEY;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.camel.attachment.AttachmentMessage;;
import org.apache.camel.component.spring.ws.SpringWebserviceConstants;
import org.apache.camel.component.spring.ws.filter.impl.BasicMessageFilter;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

/**
 *
 * @author conrad.gustafson
 * 
 * Because we pass our transaction audit object around as a header, if you call
 * a web service with Spring-WS it will propogate it to the web service. This 
 * component can be passed to Spring-WS as an option
 * messageFilter=#transactionIgnoringMessageFilter
 */
@Component
public class TransactionIgnoringMessageFilter extends BasicMessageFilter {

    private static final String LOWERCASE_BREADCRUMB_ID = "breadcrumbid";
    
    protected List<String> getListOfHeaderKeysToIgnore() {
        List<String> listOfHeaderKeysToIgnore = new ArrayList();
        listOfHeaderKeysToIgnore.add(TRANSACTION_HEADER_KEY.toLowerCase());
        listOfHeaderKeysToIgnore.add(TRANSACTION_ID_HEADER_KEY.toLowerCase());
        listOfHeaderKeysToIgnore.add("camelfilenameproduced");
        listOfHeaderKeysToIgnore.add("messagetype");
        return listOfHeaderKeysToIgnore;
    }

    @Override
    protected void doProcessSoapHeader(AttachmentMessage inOrOut, SoapMessage soapMessage) {

        SoapHeader soapHeader = soapMessage.getSoapHeader();

        Map<String, Object> headers = inOrOut.getHeaders();

        HashSet<String> headerKeySet = new HashSet<String>(headers.keySet());

        headerKeySet.remove(SpringWebserviceConstants.SPRING_WS_SOAP_ACTION.toLowerCase());
        headerKeySet.remove(SpringWebserviceConstants.SPRING_WS_ENDPOINT_URI.toLowerCase());
        headerKeySet.remove(SpringWebserviceConstants.SPRING_WS_ADDRESSING_ACTION.toLowerCase());
        headerKeySet.remove(SpringWebserviceConstants.SPRING_WS_ADDRESSING_PRODUCER_FAULT_TO.toLowerCase());
        headerKeySet.remove(SpringWebserviceConstants.SPRING_WS_ADDRESSING_PRODUCER_REPLY_TO.toLowerCase());
        headerKeySet.remove(SpringWebserviceConstants.SPRING_WS_ADDRESSING_CONSUMER_FAULT_ACTION.toLowerCase());
        headerKeySet.remove(SpringWebserviceConstants.SPRING_WS_ADDRESSING_CONSUMER_OUTPUT_ACTION.toLowerCase());

        headerKeySet.remove(LOWERCASE_BREADCRUMB_ID);

        for (String name : headerKeySet) {

            if (getListOfHeaderKeysToIgnore().contains(name)) {
                continue;
            }

            Object value = headers.get(name);

            if (value instanceof QName) {
                soapHeader.addHeaderElement((QName) value);
            } else {
                if (value instanceof String) {
                    soapHeader.addAttribute(new QName(name), value + "");
                }
            }
        }

    }
}