package ca.bc.gov.moh.rtrans.service.audit;

import ca.bc.gov.moh.esb.util.SimpleSerializer;
import ca.bc.gov.moh.esb.util.audit.AuditProcessor;
import ca.bc.gov.moh.esb.util.audit.entity.Transaction;
import ca.bc.gov.moh.esb.util.audit.entity.TransactionEvent;
import static ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessorConfig.EVENT_HEADER_KEY;
import static ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessorConfig.TRANSACTION_HEADER_KEY;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author joshua.burton
 */
public class RTransAuditProcessor implements Processor {
    
    /**
     * AuditProcessor object
     */
    protected AuditProcessor ap;
    
    /**
     * Default constructor
     */
    public RTransAuditProcessor() {
        this.ap = new AuditProcessor();
    }
    
    /**
     * Constructor used by InboundRouteBuilderTest to set a dummy AuditProcessor.
     *
     * @param ap AuditProcessor
     */
    public RTransAuditProcessor(AuditProcessor ap) {
        this.ap = ap;
    }
    
    @Override
    @Transactional
    public void process(Exchange exchange) {
        
        String serializedTransaction = exchange.getIn().getHeader(TRANSACTION_HEADER_KEY, String.class);
        String serializedEvent = exchange.getIn().getHeader(EVENT_HEADER_KEY, String.class);
        Transaction transaction = (Transaction) SimpleSerializer.fromString(serializedTransaction);
        TransactionEvent event = (TransactionEvent) SimpleSerializer.fromString(serializedEvent);
        
        try {
            if (transaction.getIsNew()) {
                ap.insert(transaction);
            }
            ap.insert(event);

        } catch (Exception e) {
            Logger.getLogger(RTransAuditProcessor.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
