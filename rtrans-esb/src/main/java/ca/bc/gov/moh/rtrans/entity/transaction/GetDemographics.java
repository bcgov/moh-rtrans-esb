package ca.bc.gov.moh.rtrans.entity.transaction;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * A request message to perform a deterministic (by identifier) search for a
 * person.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:31 PM
 */
public class GetDemographics extends RequestMessage {

    private static final String DEFAULT_IDENTIFIER_SOURCE = "MOH_CRS";

    private IdentifierAttribute identifier;

    public GetDemographics() {
        identifier = new IdentifierAttribute();
        identifier.setSource(DEFAULT_IDENTIFIER_SOURCE);
    }

    /**
     * @return the identifier
     */
    public IdentifierAttribute getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to search by
     */
    public void setIdentifier(IdentifierAttribute identifier) {
        this.identifier = identifier;
    }

    @Override
    public List<IdentifierAttribute> getAuditableIdentifiers() {
        List<IdentifierAttribute> identifierList = new ArrayList<>();
        if (this.getIdentifier() != null) {
            identifierList.add(this.getIdentifier());
        }
        return identifierList;
    }

}//end GetDemographics
