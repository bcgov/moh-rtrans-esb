/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.ha.service;

import ca.bc.gov.moh.rtrans.service.v2.V2ServiceConstants;
import java.util.List;

/**
 *
 * @author conrad.gustafson
 */
public class HealthAuthorityMessageFilter extends TransactionIgnoringMessageFilter {
    
    @Override
    protected List<String> getListOfHeaderKeysToIgnore() {
        List<String> listOfHeaderKeysToIgnore = super.getListOfHeaderKeysToIgnore();
        listOfHeaderKeysToIgnore.add(V2ServiceConstants.strAckProperty.toLowerCase());
        listOfHeaderKeysToIgnore.add(V2ServiceConstants.messageid.toLowerCase());
        listOfHeaderKeysToIgnore.add("ACK");
        listOfHeaderKeysToIgnore.add("HSA_TRANSACTION");
        return listOfHeaderKeysToIgnore;
    }

}
