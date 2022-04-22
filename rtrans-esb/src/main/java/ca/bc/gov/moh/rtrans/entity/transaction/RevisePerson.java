/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.entity.transaction;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonRelationship;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import ca.bc.gov.moh.esb.util.audit.AuditableResponse;
import ca.bc.gov.moh.esb.util.audit.AuditableResponseMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Patrick.Weckermann
 */
public class RevisePerson extends RequestMessage implements EventMessage, Serializable, AuditableResponse {
    
    private static final long serialVersionUID = 7526472295622776147L;
    
    @Valid
    private Person person;
    
    private Date eventTime;
    
    // This is only here to capture error messages
    private final List<AuditableResponseMessage> auditableResponseMessages = new ArrayList<>();

    public RevisePerson() {
        person = new Person();
            List<IdentifierAttribute> identifierList = new ArrayList<>();
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

    @Override
    public Date getEventTime() {
        return eventTime;
    }

    @Override
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getMessageGroupId() {
        List<IdentifierAttribute> identifierList = getPerson().getIdentifier();
        for (IdentifierAttribute identifier : identifierList) {
            if (identifier.isMRN()) {
                return identifier.getValue();
            }
        }
        return null;
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
        return auditableResponseMessages;
    }
    
    public boolean isAddNewborn() {
        if (person == null) {
            return false;
        }
        
        boolean babyFirstNameFound = false;
        List<PersonNameAttribute> nameList = person.getName();
        for (PersonNameAttribute name : nameList) {
            String firstName = name.getFirstName();
            if (!StringUtils.isEmpty(firstName) && firstName.toUpperCase().startsWith("BABY")) {
                babyFirstNameFound = true;
                break;
            }
        }
        if (!babyFirstNameFound) {
            return false;
        }
        
        final List<PersonRelationship> relationships = person.getRelationship();
        if (relationships == null || person.getRelationship().isEmpty()) {
            return false;
        }
        
        boolean motherPHNFound = false;
        for (PersonRelationship relationship : relationships) {
            List<IdentifierAttribute> identifierList = relationship.getRelationshipHolder().getIdentifier();
            for (IdentifierAttribute identifier : identifierList) {
                if (identifier.isBCPHN()) {
                    motherPHNFound = true;
                    break;
                }
            }
        }
        if (!motherPHNFound) {
            return false;
        }
        
        return true;
    }
}
