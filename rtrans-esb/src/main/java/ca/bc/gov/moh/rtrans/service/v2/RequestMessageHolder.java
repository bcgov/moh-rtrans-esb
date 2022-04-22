/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import java.util.List;

/**
 *
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
public class RequestMessageHolder extends RequestMessage {

    @Override
    public List<IdentifierAttribute> getAuditableIdentifiers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
