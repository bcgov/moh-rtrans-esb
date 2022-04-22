/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.esb.util.audit;

import java.util.List;

/**
 *
 * @author greg.perkins
 */
public interface AffectedParties {
    public List<AuditableParty> getAuditableParties();
}
