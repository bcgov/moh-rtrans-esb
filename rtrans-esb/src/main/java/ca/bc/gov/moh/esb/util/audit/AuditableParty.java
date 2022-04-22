/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.esb.util.audit;

/**
 * Interface for providing Affected Party information to the auditing framework
 * @author greg.perkins
 */
public class AuditableParty {
    

    private String identifierSource;
    private String identifier;
    private String identifierType;
    private String status;

    public String getIdentifierSource() {
        return identifierSource;
    }

    public void setIdentifierSource(String identifierSource) {
        this.identifierSource = identifierSource;
    }
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
