/*
 * *********************************************************************************************************************
 *  Copyright (c) 2018, Ministry of Health, BC.                                                 *
 *                                                                                                                     *
 *  All rights reserved.                                                                                               *
 *    This information contained herein may not be used in whole                                                       *
 *    or in part without the express written consent of the                                                            *
 *    Government of British Columbia, Canada.                                                                          *
 *                                                                                                                     *
 *  Revision Control Information                                                                                       *
 *  File:                $Id::                                                                                       $ *
 *  Date of Last Commit: $Date::                                                                                     $ *
 *  Revision Number:     $Rev::                                                                                      $ *
 *  Last Commit by:      $Author::                                                                                   $ *
 *                                                                                                                     *
 * *********************************************************************************************************************
 */
package ca.bc.gov.moh.rtrans.service.audit;

import ca.bc.gov.moh.esb.util.filedrop.FileDropProcessor;
import static ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessorConfig.TRANSACTION_ID_HEADER_KEY;
import static ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessorConfig.TRANSACTION_MESSAGE_TYPE_HEADER_KEY;
import java.util.Properties;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class RTransFileDropProcessor implements Processor {
    
    private String messageType;
    private Properties filedropProperties;
    
    public RTransFileDropProcessor() {
    }
    
    public RTransFileDropProcessor(String messageType) {
        this.messageType = messageType;
        
    }
    
    @Override
    public void process(Exchange exchange) {
        
        filedropProperties = exchange.getContext().getPropertiesComponent().loadProperties(property -> property.startsWith("filedrop"));
        
        String path = filedropProperties.getProperty("filedrop.path");
        
        String transactionType = (String) exchange.getIn().getHeader(TRANSACTION_MESSAGE_TYPE_HEADER_KEY);
        String transactionId = exchange.getIn().getHeader(TRANSACTION_ID_HEADER_KEY, String.class);
        
        FileDropProcessor fdp = new FileDropProcessor(path, transactionType, transactionId, filedropProperties);
        
        String messageBody = exchange.getIn().getBody(String.class);
        fdp.dropFile(messageBody, messageType);
    }
}
