package ca.bc.gov.moh.rtrans.entity.transaction;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

/**
 * A request to perform a probabilistic (by various attributes) search on a person.
 * 
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public class FindCandidates extends RequestMessage implements EventMessage, Serializable {

    private int maxResults;
    private int maxRows;
    private int minScore;

    @Valid
    private Person person;        
    private Date eventTime;

    public FindCandidates() {
        person = new Person();
        List<IdentifierAttribute> identifierList = new ArrayList<>();
        person.setIdentifier(identifierList);
    }

    /**
     * @return the maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * @return the maxRows
     */
    public int getMaxRows() {
        return maxRows;
    }

    /**
     * @param maxRows the maxRows to set
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    /**
     * @return the minScore
     */
    public int getMinScore() {
        return minScore;
    }

    /**
     * @param minScore the minScore to set
     */
    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public List<IdentifierAttribute> getAuditableIdentifiers() {
        List<IdentifierAttribute> identifierList = null;
        if (person != null) {
            identifierList = person.getAllIdentifiers();
        }
        return identifierList;
    }
    
    @Override
    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    
}//end FindCandidates