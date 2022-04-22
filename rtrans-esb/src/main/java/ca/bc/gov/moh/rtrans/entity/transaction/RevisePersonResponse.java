/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.entity.transaction;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.ResponseMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patrick.Weckermann
 */
public class RevisePersonResponse extends ResponseMessage {
    private List<IdentifierAttribute> identifiers;

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
        if (this.getIdentifiers() != null) {
            identifierList.addAll(this.getIdentifiers());
        }
        return identifierList;
    }
}
