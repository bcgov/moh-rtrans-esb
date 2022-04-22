/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author trevor.schiavone
 */
public class HnSecureStripLeadingBytes implements Processor{
    
    @Override
    public void process(Exchange exchange) {
        String messageString = parseIncomingMessage(exchange);
        exchange.getIn().setBody(messageString);
    }
    
    /**
     * @param exchange 
     * @return messageString
     * 
     * Strips the first 8 bytes from the exchange message body 
     * so that it can be read by the Hl7v2 parser
     */
    private String parseIncomingMessage(Exchange exchange) {

        String messageString;
        messageString = exchange.getIn().getBody(String.class).substring(8);
        return messageString;
    }
    
}
