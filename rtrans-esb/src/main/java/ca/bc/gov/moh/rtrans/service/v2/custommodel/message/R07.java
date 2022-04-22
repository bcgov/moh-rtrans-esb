/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2.custommodel.message;

import ca.uhn.hl7v2.model.v24.segment.*;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.model.*;

/**
 * An R07 message.
 * 
 * Note that this has the same structure as the {@link R03} message.
 * 
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
public class R07 extends AbstractMessage{
    /**
     * Creates a new R07 message with DefaultModelClassFactory. 
     */ 
    public R07() { 
       this(new DefaultModelClassFactory());
    }

    /** 
     * Creates a new R07 message with custom ModelClassFactory.
     * @param factory
     */
    public R07(ModelClassFactory factory) {
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
            throw new RuntimeException(e);
       }
    }

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
