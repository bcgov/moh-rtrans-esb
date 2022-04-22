/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2.custommodel.message;

/**
 * The ZTL Segment
 * @author Kuan Fan
 */

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.model.v24.datatype.CQ;

/**
 * Represents an HL7 ZTL segment. 
 * This segment has CQ and ID fields.
 */
public class ZTL extends AbstractSegment {
    
    /** 
     * Creates a new ZTL segment
     * @param parent used to call parent constructor
     */
    public ZTL(Group parent, ModelClassFactory factory) {
       super(parent, factory);
       init();
    }
    
    /**
     * Initialize ZTL segment.
     */
    private void init() {
        try {   
            this.add(CQ.class, true, 1, 10, new Object[]{ getMessage() }, "Transaction Segment Count"); 
       } catch(HL7Exception e) {
            log.error("Unexpected error creating ZTL - this is probably a bug in the source code generator.", e);
       }
    }
    
    /**
     * Return CQ Segment
     * @return CQ
     */
    public CQ getTransactionSegmentCount() {
        CQ cq =this.getTypedField(1, 0);
        return cq;
    }

}
