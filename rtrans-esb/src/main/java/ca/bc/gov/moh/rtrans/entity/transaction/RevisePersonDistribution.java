/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import ca.bc.gov.moh.esb.util.audit.AffectedParties;
import ca.bc.gov.moh.esb.util.audit.AuditableMessage;
import ca.bc.gov.moh.esb.util.audit.AuditableParty;
import ca.bc.gov.moh.esb.util.audit.AuditableResponse;
import ca.bc.gov.moh.esb.util.audit.AuditableResponseMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author conrad.gustafson
 */
public class RevisePersonDistribution extends RequestMessage
    implements AuditableMessage, AuditableResponse, Serializable {
    
    private static final long serialVersionUID = 7526472295622776147L;
    
    private Person person;    
    private Date eventTime;
    private List<IdentifierAttribute> identifiers;
    private final List<AuditableResponseMessage> auditableResponseMessageList = new ArrayList<>();

    public RevisePersonDistribution() {
        person = new Person();
        List<IdentifierAttribute> identifierList = new ArrayList<IdentifierAttribute>();
        person.setIdentifier(identifierList);
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    
    /**
     * @return the identifiers
     */
    public List<IdentifierAttribute> getIdentifiers() {
        return identifiers;
    }

    /**
     * @param identifiers the identifiers to set
     */
    public void setIdentifiers(List<IdentifierAttribute> identifiers) {
        this.identifiers = identifiers;
    }
    
    @Override
    public List<IdentifierAttribute> getAuditableIdentifiers() {
        List<IdentifierAttribute> identifierList = new ArrayList<>();
        if (this.getPerson() != null) {
            identifierList.addAll(this.getPerson().getAllIdentifiers());
        }
        return identifierList;
    }

    @Override
    public List<AuditableResponseMessage> getAuditableResponseMessageList() {
        return auditableResponseMessageList;
    }

}
