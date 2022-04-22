package ca.bc.gov.moh.rtrans.service.audit;

import ca.bc.gov.moh.esb.util.audit.AuditProcessor;

/**
 *
 * @author joshua.burton
 */
public class AuditProcessorMock extends AuditProcessor {
    
    @Override
    public <T> T insert(T record) {
        return record;
    }
}
