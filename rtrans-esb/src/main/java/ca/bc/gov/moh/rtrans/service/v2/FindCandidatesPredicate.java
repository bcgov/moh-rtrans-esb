/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

/**
 *
 * @author Conrad.Gustafson
 */
public class FindCandidatesPredicate implements Predicate {

    public static final String FIND_CANDIDATES_MESSAGE_TYPE = "R09";

    @Override
    public boolean matches(Exchange exchange) {
        return check(exchange);
    }

    public static boolean check(Exchange exchange) {
        String messageType = (String) exchange.getIn().getHeader(V2ServiceConstants.messageType);
        return (FIND_CANDIDATES_MESSAGE_TYPE.equals(messageType));
    }

}
