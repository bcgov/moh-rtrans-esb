/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2.custommodel.message;

/**
 *
 * @author trevor.schiavone
 */

import ca.uhn.hl7v2.model.v24.group.*;
import ca.uhn.hl7v2.model.v24.segment.*;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.model.*;

public class R03 extends AbstractMessage{
    /**
     * Creates a new R03 message with DefaultModelClassFactory. 
     */ 
    public R03() { 
       this(new DefaultModelClassFactory());
    }

    /** 
     * Creates a new R03 message with custom ModelClassFactory.
     * @param factory
     */
    public R03(ModelClassFactory factory) {
       super(factory);
       init();
    }

    private void init() {
        try {

            this.add(MSH.class, true, false);                        
            this.add(ZHD.class, true, false);
            this.add(PID.class, true, false);
            this.add(ZIA.class, false, false);
            
        } catch(HL7Exception e) {
            log.error("Unexpected error creating R03 - this is probably a bug in the source code generator.", e);
       }
    }

    /** 
     * Returns "2.4"
     */
    public String getVersion() {
       return "2.4";
    }
    
    public MSH getMSH() { 
       return getTyped("MSH", MSH.class);
    }   
    public ZHD getZHD() { 
       return getTyped("ZHD", ZHD.class);
    }
    public PID getPID() { 
       return getTyped("PID", PID.class);
    }
    public ZIA getZIA() { 
       return getTyped("ZIA", ZIA.class);
    }

}
