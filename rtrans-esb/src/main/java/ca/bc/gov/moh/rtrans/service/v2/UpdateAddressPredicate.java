package ca.bc.gov.moh.rtrans.service.v2;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

/**
 * 
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
public class UpdateAddressPredicate implements Predicate {

    private static final String UPDATE_ADDRESS_MESSAGE_TYPE = "R07";

    @Override
    public boolean matches(Exchange exchange) {
        String messageType = (String) exchange.getIn().getHeader(V2ServiceConstants.messageType);
        return UPDATE_ADDRESS_MESSAGE_TYPE.equals(messageType);
    }

}
