package ca.bc.gov.moh.rtrans.entity;

import ca.bc.gov.moh.esb.util.audit.AffectedParties;
import ca.bc.gov.moh.esb.util.audit.AuditableMessage;
import ca.bc.gov.moh.esb.util.audit.AuditableParty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A message that is to be transmitted or processed.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:33 PM
 */
public abstract class Message implements Serializable, AuditableMessage, AffectedParties {

    private static final long serialVersionUID = 7526472295622776147L;
    
    private Author author;
    private Date creationTime;
    private String messageId;
    private List<CommunicationFunction> receiver;
    private CommunicationFunction sender;
    private Object messageBody;
    private String interactionId;
    private String acceptAckCode;

    public Message() {
        // Default to always having a sender and at least one receiver
        sender = new CommunicationFunction();
        receiver = new ArrayList<>();
        final CommunicationFunction receiverCommunicationFunction = new CommunicationFunction();
        receiver.add(receiverCommunicationFunction);
    }

    /**
     * @return the author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * @return the creationTime
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return the messageId
     */
    @Override
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the receiver
     */
    public List<CommunicationFunction> getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(List<CommunicationFunction> receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the sender
     */
    public CommunicationFunction getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(CommunicationFunction sender) {
        this.sender = sender;
    }

    /**
     * @return the original message data in its original string format (xml,
     * json, edi, etc).
     */
    public Object getMessageBody() {
        return messageBody;
    }

    /**
     * @param messageBody set the original message data in its original format
     * (xml, json, edi, etc).
     */
    public void setMessageBody(Object messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * @return the interactionId
     */
    public String getInteractionId() {
        return interactionId;
    }

    /**
     * @param interactionId the interactionId to set
     */
    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    /**
     * Getter of accepteAckCode
     * @return String the acceptAckCode
     */
    public String getAcceptAckCode() {
        return acceptAckCode;
    }

    /**
     * Setter of acceptAckCode
     * @param acceptAckCode String the acceptAckCode
     */
    public void setAcceptAckCode(String acceptAckCode) {
        this.acceptAckCode = acceptAckCode;
    }

    
    
    @Override
    public List<AuditableParty> getAuditableParties() {

        ArrayList<AuditableParty> auditableParties = new ArrayList<>();

        List<IdentifierAttribute> auditableIdentifiers = getAuditableIdentifiers();

        if (auditableIdentifiers != null) {
            for (IdentifierAttribute identifier : auditableIdentifiers) {
                String identifierValue = identifier.getValue();
                String identifierSource = identifier.getSource();
                String identifierType = null;
                if (identifier.getType() != null) {
                    identifierType = identifier.getType().toString();
                }
                String identifierStatus = null;
                if (identifier.getStatus() != null) {
                    identifierStatus = identifier.getStatus().toString();
                }
                AuditableParty auditableParty = new AuditableParty();
                auditableParty.setIdentifier(identifierValue);
                auditableParty.setIdentifierSource(identifierSource);
                auditableParty.setIdentifierType(identifierType);
                auditableParty.setStatus(identifierStatus);
                auditableParties.add(auditableParty);
            }
        }
        return auditableParties;
    }

    public abstract List<IdentifierAttribute> getAuditableIdentifiers();

}//end Message
