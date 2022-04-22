/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2.custommodel.message;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.v24.segment.ERR;
import ca.uhn.hl7v2.model.v24.segment.MSA;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;

/**
 *
 * @author trevor.schiavone
 */
public class R07Response extends AbstractMessage{
    
    /**
     * Creates a new R03_RESPONSE message with DefaultModelClassFactory. 
     */
    public R07Response() { 
        this(new DefaultModelClassFactory());
    }
    
    /**
     * Constructor.
     * @param theFactory ModelClassFactory is used to call parent constructor.
     */
    public R07Response(ModelClassFactory theFactory) {
        super(theFactory);
        init();
    }
    
    private void init() {
        try {
            this.add(MSH.class, true, false);
            this.add(MSA.class, true, false);
            this.add(ERR.class, true, false);
            this.add(ZIA.class, false, false);           
        } catch(HL7Exception e) {
            log.error("Unexpected error creating R03 - this is probably a bug in the source code generator.", e);
       }
    }
    
    /** 
     * Returns "2.4"
     * @return 
     */
    @Override
    public String getVersion() {
       return "2.4";
    }
    
    public MSH getMSH() { 
       return getTyped("MSH", MSH.class);
    }   
    public MSA getMSA() { 
       return getTyped("MSA", MSA.class);
    }   
    public ERR getERR() { 
       return getTyped("ERR", ERR.class);
    }
    public ZIA getZIA() { 
       return getTyped("ZIA", ZIA.class);
    }
    
    
}
