/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.service.v2.custommodel.message;


import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.model.AbstractGroup;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.v24.segment.PID;

/**
 * The class represents person found in R09 response.
 * It has both PID and ZIA segments.
 */
public class R09Response_PersonFound extends AbstractGroup {

    /** 
     * Constructor, creates a new R09_RESPONSE_PERSON_FOUND
     * @param parent used by parent constructor
     * @param factory used by parent constructor
     */
    public R09Response_PersonFound(Group parent, ModelClassFactory factory) {
       super(parent, factory);
       init();
    }

    /**
     * Initialize R09Response_PersonFound
     */
    private void init() {
        try {
            this.add(PID.class, true, false, false);
            this.add(ZIA.class, true, true, false);
        } catch(HL7Exception e) {
          log.error("Unexpected error creating R09_RESPONSE_PERSON_FOUND - this is probably a bug in the source code generator.", e);
        }
    }
 
    /**
     * Returns PID segment
     * @return PID
     */
    public PID getPID() { 
       PID retVal = getTyped("PID", PID.class);
       return retVal;
    }
    
    /**
     * Returns ZIP segment
     * @return ZIA
     */
    public ZIA getZIA() { 
       ZIA retVal = getTyped("ZIA", ZIA.class);
       return retVal;
    }

}