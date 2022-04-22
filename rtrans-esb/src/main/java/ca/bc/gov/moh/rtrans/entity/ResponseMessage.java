package ca.bc.gov.moh.rtrans.entity;

import ca.bc.gov.moh.esb.util.audit.AffectedParties;
import ca.bc.gov.moh.esb.util.audit.AuditableParty;
import ca.bc.gov.moh.esb.util.audit.AuditableResponse;
import ca.bc.gov.moh.esb.util.audit.AuditableResponseMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 * Response informaiton containing any output data and status information about
 * the processing of the requested action.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public abstract class ResponseMessage extends Message implements Serializable, AuditableResponse, AffectedParties {

    private static final long serialVersionUID = 7526472295622776147L;

    private RequestMessage request;
    private List<SystemResponse> systemResponse = new ArrayList<>();

    public ResponseMessage() {

    }

    /**
     * @return the request
     */
    public RequestMessage getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(RequestMessage request) {
        this.request = request;
    }

    /**
     * @return the systemResponse
     */
    public List<SystemResponse> getSystemResponse() {
        return systemResponse;
    }

    /**
     * @param systemResponse the systemResponse to set
     */
    public void setSystemResponse(List<SystemResponse> systemResponse) {
        this.systemResponse = systemResponse;
    }

    @Override
    public List<AuditableResponseMessage> getAuditableResponseMessageList() {
        List<AuditableResponseMessage> auditableResponseMessages = new ArrayList<AuditableResponseMessage>();
        CollectionUtils.addAll(auditableResponseMessages, systemResponse.iterator());
        return auditableResponseMessages;
    }

    @Override
    public List<AuditableParty> getAuditableParties() {

        ArrayList<AuditableParty> auditableParties = new ArrayList<AuditableParty>();

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


}//end ResponseMessage
