/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import ca.bc.gov.moh.rtrans.entity.ResponseMessage;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R03;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R07;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZHD;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.datatype.CX;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.parser.CustomModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.validation.ValidationContext;
import ca.uhn.hl7v2.validation.impl.ValidationContextFactory;
import java.io.IOException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Use a model class factory called the CanonicalModelClassFactory. This class
 * forces a specific version of HL7 to be used. Because HL7 v2.x is a backwards
 * compatible standard, you can choose the highest version you need to support,
 * and the model classes will be compatible with messages from previous
 * versions.
 *
 * Use v 2.3 as the highest version supported
 *
 * @author kai.du
 */
public class Hl7v2Parser implements Processor {

    private static final Boolean VALIDATE_HL7V2 = false;

    private static final String MSH9_MESSAGE_TYPE_R03 = "|R03|";
    private static final String MSH9_MESSAGE_TYPE_R07 = "|R07|";
    private static final String MSH9_MESSAGE_TYPE_R09 = "|R09|";

    @Override
    public void process(Exchange exchange) throws Exception {
        Message vMessage = parseIncomingMessage(exchange);
        cacheGenericAck(vMessage, exchange);
        cacheMessageControlId(vMessage, exchange);
        cacheMessageType(vMessage, exchange);
        cacheMessageSecurity(vMessage, exchange);
        cacheMessageProcessId(vMessage, exchange);
        cacheMessageDateTime(vMessage, exchange);
        cacheEventDateTime(vMessage, exchange);
        cacheSenderAndReceiver(vMessage, exchange);
        cacheZIA(vMessage, exchange);
        cacheVersionId(vMessage, exchange);
        exchange.getIn().setBody(vMessage);
    }

    private void cacheZIA(Message message, Exchange exchange) throws HL7Exception {
        if (exchange.getIn().getHeader(V2ServiceConstants.messageType, String.class).equals("R07")) {
            ZIA zia = (ZIA) message.get("ZIA");
            if (zia != null) {
                
                if (zia.getExtendedTelephoneNumber() != null && !zia.getExtendedTelephoneNumber().isEmpty()) {
                    exchange.getIn().setHeader(V2ServiceConstants.ziaSegmentXTN, zia.getExtendedTelephoneNumber().encode());
                }
                
                if (zia.getExtendedAddress() != null && !zia.getExtendedAddress().isEmpty()) {                  
                    exchange.getIn().setHeader(V2ServiceConstants.ziaSegmentZAD, zia.getExtendedAddress().encode());
                }
            }
            PID pid = (PID) message.get("PID");
            if(pid != null){
                CX pi__ = pid.getPatientID();
                // String pi_ = "";
                String pi_ = pid.getPid2_PatientID().getCx1_ID().encode();
                exchange.getIn().setHeader(V2ServiceConstants.pidSegmentPHN, pi_);
            }
        }
    }

    //This is also called Author in some places
    private void cacheMessageSecurity(Message message, Exchange exchange) throws HL7Exception {
        MSH msh = (MSH) message.get("MSH");
        String messageSecurity;

        if (msh != null) {
            // enforce 20 character limit on message user id
            if (msh.getMsh8_Security().getValue().length() < 21) {
                messageSecurity = msh.getMsh8_Security().encode();
                exchange.getIn().setHeader(V2ServiceConstants.messageSecurity, messageSecurity);
            } else {
                throw new HL7Exception("Invalid Message User ID");
            }
        }
    }

    private void cacheVersionId(Message message, Exchange exchange) throws HL7Exception {
        MSH msh = (MSH) message.get("MSH");
        String versionId;
        if (msh != null) {
            versionId = msh.getMsh12_VersionID().encode();
            exchange.getIn().setHeader(V2ServiceConstants.VERSION_ID, versionId);
        }
    }

    private void cacheMessageControlId(Message vMessage, Exchange exchange) throws HL7Exception {
        // Set the message control ID
        MSH msh = (MSH) vMessage.get("MSH");
        String messageControlID;
        if (msh != null) {
            messageControlID = msh.getMsh10_MessageControlID().encode();
            exchange.getIn().setHeader(V2ServiceConstants.strMessageIDProperty, messageControlID);
        }
    }

    private void cacheMessageDateTime(Message vMessage, Exchange exchange) throws HL7Exception {
        // Set the message control ID
        MSH msh = (MSH) vMessage.get("MSH");
        String messageDateTime;
        if (msh != null) {
            messageDateTime = msh.getMsh7_DateTimeOfMessage().encode();
            exchange.getIn().setHeader(V2ServiceConstants.messageCreationTime, messageDateTime);
        }
    }

    private void cacheEventDateTime(Message vMessage, Exchange exchange) throws HL7Exception {

        ZHD zhd = (ZHD) vMessage.get("ZHD");
        String eventDateTime;
        if (zhd != null) {
            eventDateTime = zhd.getZHD1_EventDateTime().encode();
            exchange.getIn().setHeader(V2ServiceConstants.eventDateTime, eventDateTime);

        }
    }

    private void cacheMessageProcessId(Message message, Exchange exchange) throws HL7Exception {
        MSH msh = (MSH) message.get("MSH");
        String messageProcessId;
        if (msh != null) {
            messageProcessId = msh.getMsh11_ProcessingID().encode();
            exchange.getIn().setHeader(V2ServiceConstants.messageProcessId, messageProcessId);
        }
    }

    private void cacheGenericAck(Message vMessage, Exchange exchange) throws IOException, HL7Exception {
        // Cache a generic Ack
        Message ack = vMessage.generateACK();
        exchange.getIn().setHeader(V2ServiceConstants.strAckProperty, ack.encode());
    }

    private Message parseIncomingMessage(Exchange exchange) throws HL7Exception {
        HapiContext context = new DefaultHapiContext();
        context.setValidationContext((ValidationContext) ValidationContextFactory.defaultValidation());
        //set validator false, because non-standard data of tele number cause validation failure
        context.getParserConfiguration().setValidating(VALIDATE_HL7V2);
        context.setValidationContext((ValidationContext) ValidationContextFactory.noValidation());
        ModelClassFactory cmf = new CustomModelClassFactory("ca.bc.gov.moh.rtrans.service.v2.custommodel");
        context.setModelClassFactory(cmf);
        PipeParser parser = context.getPipeParser();
        String message = (String) exchange.getIn().getBody();
        /*
         The HAPI library PipeParser::getStructure method requires MSH-9
         to be 2-3 components to deteremine message type (e.g. "QBP^Q21^QBP_Z21"),
         but CRS Retirement provides only one (e.g. "R03"). (It is probably not
         standard HL7.) We can force the type to be recognized as R03, R07, R09 by providing
         it to the parser.
         */
        if (message.contains(MSH9_MESSAGE_TYPE_R03)) {
            Message r03 = new R03();
            parser.parse(r03, message);
            return r03;
        } else if (message.contains(MSH9_MESSAGE_TYPE_R07)) {
            Message r07 = new R07();
            parser.parse(r07, message);
            return r07;
        } else if (message.contains(MSH9_MESSAGE_TYPE_R09)) {
            Message r09 = new R09();
            parser.parse(r09, message);
            return r09;
        } else {
            return parser.parse(message);
        }
    }

    private void cacheMessageType(Message vMessage, Exchange exchange) throws HL7Exception {
        MSH msh = (MSH) vMessage.get("MSH");
        String messageType;

        if (msh != null) {
            messageType = msh.getMsh9_MessageType().getMsg1_MessageType().encode();
            exchange.getIn().setHeader(V2ServiceConstants.messageType, messageType);
        }
    }

    private void cacheSenderAndReceiver(Message vMessage, Exchange exchange) throws HL7Exception {
        MSH msh = (MSH) vMessage.get("MSH");

        String senderApplication;
        String senderFacility;
        String receiverApplication;
        String receiverFacility;

        if (msh != null) {

            senderApplication = msh.getMsh3_SendingApplication().encode();
            senderFacility = msh.getMsh4_SendingFacility().encode();
            receiverApplication = msh.getMsh5_ReceivingApplication().encode();
            receiverFacility = msh.getMsh6_ReceivingFacility().encode();

            exchange.getIn().setHeader(V2ServiceConstants.senderApplication, senderApplication);
            exchange.getIn().setHeader(V2ServiceConstants.senderFacility, senderFacility);
            exchange.getIn().setHeader(V2ServiceConstants.receiverApplication, receiverApplication);
            exchange.getIn().setHeader(V2ServiceConstants.receiverFacility, receiverFacility);

        }
    }

    public static void setMSHValues(ResponseMessage response, MSH msh, Exchange exchange) throws HL7Exception {

        msh.getMsh3_SendingApplication().parse(exchange.getIn().getHeader(V2ServiceConstants.receiverApplication, String.class));
        msh.getMsh4_SendingFacility().parse(exchange.getIn().getHeader(V2ServiceConstants.receiverFacility, String.class));
        msh.getMsh5_ReceivingApplication().parse(exchange.getIn().getHeader(V2ServiceConstants.senderApplication, String.class));
        msh.getMsh6_ReceivingFacility().parse(exchange.getIn().getHeader(V2ServiceConstants.senderFacility, String.class));
        msh.getMsh7_DateTimeOfMessage().parse(DataTypeConverter.convertToTS(response.getCreationTime()));
        msh.getMsh8_Security().parse(exchange.getIn().getHeader(V2ServiceConstants.messageSecurity, String.class));
        msh.getMsh10_MessageControlID().parse(response.getMessageId());
        msh.getMsh11_ProcessingID().parse(exchange.getIn().getHeader(V2ServiceConstants.messageProcessId, String.class));
        msh.getMsh12_VersionID().parse(exchange.getIn().getHeader(V2ServiceConstants.VERSION_ID, String.class));
    }
}
