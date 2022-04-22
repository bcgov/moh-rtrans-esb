/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.esb.util.audit.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author conrad.gustafson
 */
@Entity
@Table(name = "EVENT_MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventMessage.findAll", query = "SELECT e FROM EventMessage e"),
    @NamedQuery(name = "EventMessage.findByEventMessageId", query = "SELECT e FROM EventMessage e WHERE e.eventMessageId = :eventMessageId"),
    @NamedQuery(name = "EventMessage.findByErrorCode", query = "SELECT e FROM EventMessage e WHERE e.errorCode = :errorCode"),
    @NamedQuery(name = "EventMessage.findByMessageText", query = "SELECT e FROM EventMessage e WHERE e.messageText = :messageText"),
    @NamedQuery(name = "EventMessage.findByLevel", query = "SELECT e FROM EventMessage e WHERE e.level = :level")})
public class EventMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "EVENT_MESSAGE_ID")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="Event_Msg_Event_Msg_ID_seq")
    @SequenceGenerator(name="Event_Msg_Event_Msg_ID_seq", sequenceName="Event_Msg_Event_Msg_ID_seq")
    private Integer eventMessageId;
    @Size(max = 50)
    @Column(name = "ERROR_CODE")
    private String errorCode;
    @Size(max = 500)
    @Column(name = "MESSAGE_TEXT", length = 500)
    private String messageText;
    @Size(max = 25)
    @Column(name = "ERROR_LEVEL")
    private String level;
    @JoinColumn(name = "TRANSACTION_EVENT_ID", referencedColumnName = "TRANSACTION_EVENT_ID")
    @ManyToOne(optional = false)
    private TransactionEvent transactionEventId;

    public EventMessage() {
    }

    public EventMessage(Integer eventMessageId) {
        this.eventMessageId = eventMessageId;
    }

    public Integer getEventMessageId() {
        return eventMessageId;
    }

    public void setEventMessageId(Integer eventMessageId) {
        this.eventMessageId = eventMessageId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public TransactionEvent getTransactionEventId() {
        return transactionEventId;
    }

    public void setTransactionEventId(TransactionEvent transactionEventId) {
        this.transactionEventId = transactionEventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventMessageId != null ? eventMessageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventMessage)) {
            return false;
        }
        EventMessage other = (EventMessage) object;
        if ((this.eventMessageId == null && other.eventMessageId != null) || (this.eventMessageId != null && !this.eventMessageId.equals(other.eventMessageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.bc.gov.moh.esb.util.audit.entity.EventMessage[ eventMessageId=" + eventMessageId + " ]";
    }
    
}
