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
public class HnSecureAddLeadingBytes implements Processor{
    
    static final int LEADING_BYTES = 8;
   
    @Override
    public void process(Exchange exchange) {
        String messageString = parseIncomingMessage(exchange);
        exchange.getIn().setBody(messageString);
    }
    
    /**
     * @param exchange 
     * @return messageString
     * 
     * Adds the message length (padded with 0 to the left to a total of 8 bytes)
     * to the start of the message
     */
    private String parseIncomingMessage(Exchange exchange) {

        String messageString;
        String leadingBytes;
        int messageLength; 
        
        messageString = exchange.getIn().getBody(String.class);    
        messageLength = messageString.length();          
        leadingBytes = String.format("%0" + LEADING_BYTES + "d", messageLength);    
        messageString = leadingBytes + messageString;

        return messageString;
    }
}
