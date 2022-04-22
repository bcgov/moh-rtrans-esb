/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.esb.util.audit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author conrad.gustafson
 */
@Entity
@Table(name = "TRANSACTION_EVENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionEvent.findAll", query = "SELECT t FROM TransactionEvent t"),
    @NamedQuery(name = "TransactionEvent.findByTransactionEventId", query = "SELECT t FROM TransactionEvent t WHERE t.transactionEventId = :transactionEventId"),
    @NamedQuery(name = "TransactionEvent.findByType", query = "SELECT t FROM TransactionEvent t WHERE t.type = :type"),
    @NamedQuery(name = "TransactionEvent.findByEventTime", query = "SELECT t FROM TransactionEvent t WHERE t.eventTime = :eventTime"),
    @NamedQuery(name = "TransactionEvent.findByMessageId", query = "SELECT t FROM TransactionEvent t WHERE t.messageId = :messageId")})
public class TransactionEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "TRANSACTION_EVENT_ID")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="Xn_Evnt_Xn_Evnt_ID_seq")
    @SequenceGenerator(name="Xn_Evnt_Xn_Evnt_ID_seq", sequenceName="Xn_Evnt_Xn_Evnt_ID_seq")
    private Integer transactionEventId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EVENT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventTime = new Date(System.currentTimeMillis());
    @Size(max = 50)
    @Column(name = "MESSAGE_ID")
    private String messageId;
    @Size(min = 1, max = 36)
    @Column(name = "TRANSACTION_ID")
    private String transactionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionEventId")
    private List<EventMessage> eventMessageList = new CopyOnWriteArrayList<>();

    public TransactionEvent() {
    }

    public TransactionEvent(Integer transactionEventId) {
        this.transactionEventId = transactionEventId;
    }

    public TransactionEvent(Integer transactionEventId, String type, Date eventTime) {
        this.transactionEventId = transactionEventId;
        this.type = type;
        this.eventTime = eventTime;
    }

    public Integer getTransactionEventId() {
        return transactionEventId;
    }

    public void setTransactionEventId(Integer transactionEventId) {
        this.transactionEventId = transactionEventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @XmlTransient
    public List<EventMessage> getEventMessageList() {
        return eventMessageList;
    }

    public void setEventMessageList(List<EventMessage> eventMessageList) {
        this.eventMessageList = new CopyOnWriteArrayList<>();
        this.eventMessageList.addAll(eventMessageList);
        
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.eventTime);
        hash = 97 * hash + Objects.hashCode(this.messageId);
        hash = 97 * hash + Objects.hashCode(this.transactionId);
        return hash;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransactionEvent other = (TransactionEvent) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.eventTime, other.eventTime)) {
            return false;
        }
        if (!Objects.equals(this.messageId, other.messageId)) {
            return false;
        }
        if (!Objects.equals(this.transactionId, other.transactionId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.bc.gov.moh.esb.util.audit.entity.TransactionEvent[ transactionEventId=" + transactionEventId + " ]";
    } 
}
