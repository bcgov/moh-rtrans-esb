/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.hl7.v3.service.dto;

import ca.bc.gov.moh.rtrans.entity.DateAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import java.util.List;

/**
 *
 * @author kai.du
 */
public class HCIMINGetDemographicsResponse {
     
    
    protected IdentifierAttribute messageId;
    
    protected DateAttribute creationTime;
    //protected ST securityText;
    
    protected String versionCode;
    
    protected IdentifierAttribute interactionId;
    
    protected String processingCode;
    
    protected String processingModeCode;
    
    protected String acceptAckCode;
    
    protected HL7Receiver receiver;
    
    protected HL7Sender sender;
    
    protected HL7ControlActProcess controlActProcess;
    
    public IdentifierAttribute getMessageId(){
        return this.messageId;
    }
    
    public void setMessageId(IdentifierAttribute value){
        this.messageId=value;
    }
    
    public DateAttribute getCreationTime(){
        return this.creationTime;
    }
    
    public void setCreationTime(DateAttribute value){
        this.creationTime=value;
    }
    
    public String getVersionCode(){
        return this.versionCode;
    }
    
    public void setVersionCode(String value){
        this.versionCode=value;
    }
    
    public IdentifierAttribute getInteractionId(){
        return this.interactionId;
    }
    
    public void setInteractionId(IdentifierAttribute value){
        this.interactionId=value;
    }
    
    public String getProcessingCode(){
        return this.processingCode;
    }
    
    public void setProcessingCode(String value){
        this.processingCode=value;
    }
    
    
    public String getProcessingModeCode(){
        return this.processingModeCode;
    }
    
    public void setProcessingModeCode(String value){
        this.processingModeCode=value;
    }
    
    
    
    public String getAcceptAckCode(){
        return this.acceptAckCode;
    }
    
    public void setAcceptAckCode(String value){
        this.acceptAckCode=value;
    }
    public HL7Receiver getReceiver(){
        return this.receiver;
    }
    public void setReceiver(HL7Receiver value){
        this.receiver=value;
    }
    
    public HL7Sender getSender(){
        return this.sender;
    }
    
    public void setSender(HL7Sender value){
        this.sender=value;
    }
    
    public HL7ControlActProcess getControlActProcess(){
        return this.controlActProcess;
    }
    
    public void setControlActProcess(HL7ControlActProcess value){
        this.controlActProcess=value;
    }
            

}
