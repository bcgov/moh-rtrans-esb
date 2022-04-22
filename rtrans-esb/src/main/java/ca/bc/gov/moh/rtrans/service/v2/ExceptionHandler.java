/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.message.ACK;
import ca.uhn.hl7v2.util.Terser;
import java.io.IOException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * //TODO remove useless code and optimize the rest
 * @author Killian.Faussart
 */
public class ExceptionHandler implements Processor {

    private static final String R03_MESSAGE_TYPE = "R03";
    private static final String R03_ERROR_CODE = "NHR509E";
    private static final String R07_MESSAGE_TYPE = "R07";
    private static final String R07_ERROR_CODE = "NHR524E";
    private static final String R09_MESSAGE_TYPE = "R09";
    private static final String R09_ERROR_CODE = "NHR529E";
    
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    
    @Override
    public void process(Exchange exchange) {

        Throwable caused = (Throwable) exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        Throwable next = caused;
        while (next != null) {
            caused = next;
            next = next.getCause();
        }

        String messageType = (String) exchange.getIn().getHeader(V2ServiceConstants.messageType);
        String errorMessage = buildMessage(messageType);
        logger.warn("A handled exception occured during the translation: "+errorMessage,caused);
        Message ack;
        
        try {
            if (exchange.getIn().getBody() instanceof ca.uhn.hl7v2.model.Message) {
                Message message = (ca.uhn.hl7v2.model.Message) exchange.getIn().getBody();

                ack = generateACKFromRequestMessage(message,
                        V2ServiceConstants.msaCodeAE,
                        errorMessage
                );

            } else if (exchange.getIn().getHeader(V2ServiceConstants.strAckProperty) != null) {

                ACK passedMsg = new ACK();
                passedMsg.parse((String) exchange.getIn().getHeader(V2ServiceConstants.strAckProperty));
               
                if (passedMsg.get("MSH") instanceof ca.uhn.hl7v2.model.v24.segment.MSH) {
                    ack = generateACKFromAckMessageV24(passedMsg,
                            V2ServiceConstants.msaCodeAE,
                            errorMessage);
                } else {
                    ack = generateACKfromTerser(passedMsg, errorMessage);
                }

            } else {
                //anyway, return a ACK with 'AE' by all means 
                ack = generateGenericACK(V2ServiceConstants.msaCodeAE, errorMessage);

            }
            
            exchange.getIn().setBody(ack.toString());
            
        } catch (Exception exception) {
            exchange.getIn().setBody(exception.getMessage());
            logger.error("An error occured during the translation: "+exception.getMessage(),exception);
        }
    }

    public static Message generateGenericACK(String ackCode, String errorMessage) throws HL7Exception, IOException {
        //anyway, genreate a ACK
        ca.bc.gov.moh.rtrans.service.v2.custommodel.message.RSP_Generic vMessage = new ca.bc.gov.moh.rtrans.service.v2.custommodel.message.RSP_Generic();

        vMessage.initQuickstart("RSP", "Generic", "T");
        Terser terser = new Terser(vMessage);
        terser.set("MSH-1", "|");
        terser.set("MSH-2", "^~\\&");
        terser.set("/MSH-3", "HSAESB");
        terser.set("/MSH-4", "ESB");
        terser.set("/MSH-5", "UNKNOWN");
        terser.set("/MSH-6", "UNKNOWN");
        terser.set("/MSA-1", ackCode == null ? "AA" : ackCode);
        if (errorMessage != null) {
            terser.set("/ERR-1-4", errorMessage);
        }
        return vMessage;
    }

    private Message generateACKFromRequestMessage(Message message, String msaCode, String errMessage) throws HL7Exception, IOException {
        Message ack = message.generateACK();
        Terser terser = new Terser(message);
        Terser terserAck = new Terser(ack);
        if ((terser.get("/MSH-9-3") != null) && terser.get("/MSH-9-3").equals("QBP_Z21")) {
            terserAck.set("/MSH-9", "RSP^Z21^RSP_Z21");
        } else if ((terser.get("/MSH-9-3") != null) && terser.get("/MSH-9-3").equals("QBP_Z22")) {
            terserAck.set("/MSH-9", "RSP^Z22^RSP_Z22");
        } else if ((terser.get("/MSH-9-2") != null) && terser.get("/MSH-9-2").equals("A31")) {
            terserAck.set("/MSH-9-1", "ACK");
            terserAck.set("/MSH-9-2", "ZA9");
            terserAck.set("/MSH-9-3", "ACK_ZA9");
        }

        if ((terser.get("/MSH-12") != null) && terser.get("/MSH-12").equals("2.4")) {
            return generateACKFromAckMessageV24(ack, msaCode, errMessage);
        }  else {
            return generateACKfromTerser(ack, errMessage);
        }
    }

    private Message generateACKFromAckMessageV24(Message ack, String msaCode, String errMessage) throws HL7Exception, IOException {

        ca.uhn.hl7v2.model.v24.segment.MSH msh = (ca.uhn.hl7v2.model.v24.segment.MSH) ack.get("MSH");
        ca.uhn.hl7v2.model.v24.segment.MSA msa = (ca.uhn.hl7v2.model.v24.segment.MSA) ack.get("MSA");
        ca.uhn.hl7v2.model.v24.message.ACK ackMessage = new ca.uhn.hl7v2.model.v24.message.ACK();
        ackMessage.initQuickstart(null, null, null);
        ackMessage.getMSH().parse(msh.encode());
        ackMessage.getMSA().getMsa1_AcknowledgementCode().setValue(msaCode);
        if (!msa.isEmpty()) {
            ackMessage.getMSA().getMsa2_MessageControlID().setValue(msa.getMsa2_MessageControlID().encode());
        }
        ackMessage.getMSA().getMsa3_TextMessage().parse(errMessage);
        ackMessage.getERR().insertErr1_ErrorCodeAndLocation(0)
                .getCodeIdentifyingError().parse(errMessage);

        return ackMessage;
    }

    private Message generateACKfromTerser(Message passedMsg, String errMessage) throws HL7Exception {
        Terser terser = new Terser(passedMsg);
        if (terser.get("/MSA-1") != null) {
            terser.set("/MSA-1", "AE");
            terser.set("/MSA-3", errMessage);
        }
        return passedMsg;
    }

    /**
     * Generates an error message based on the exception thrown
     *
     * @param caused Throwable - The exception thrown
     * @return String - Human Readable error
     */
    private String buildMessage(String messageType) {
        String errorCode;
        
        messageType = messageType != null ? messageType : "";
        
        switch(messageType){
            case R03_MESSAGE_TYPE:
                errorCode = R03_ERROR_CODE;
                break;
            case R07_MESSAGE_TYPE:
                errorCode = R07_ERROR_CODE;
                break;
            case R09_MESSAGE_TYPE:
                errorCode = R09_ERROR_CODE;
                break;
            default:
                errorCode = "Unidentified Type";
                break;
            
        }
               
        StringBuilder buff = new StringBuilder();
        buff.append(errorCode);
        buff.append("&");
        buff.append("SEVERE SYSTEM ERROR");
        
        return buff.toString();
    }

}
