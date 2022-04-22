/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.service.v2.custommodel.message;

import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.model.v24.segment.*;
import ca.uhn.hl7v2.HL7Exception;
/**
 * The class represents the R09 response based on Hapi v2 model. 
 * @author Kuan Fan
 */
public class R09Response extends AbstractMessage {

    /**
     * Constructor.
     */
    public R09Response() { 
       this(new DefaultModelClassFactory());
    }
    
    /**
     * Constructor.
     * @param theFactory ModelClassFactory is used to call parent constructor.
     */
    public R09Response(ModelClassFactory theFactory) {
        super(theFactory);
        init();
    }
    
    /**
     * Initialize R09 response Hapi v2 model by filling in R09 response required segments
     */
    private void init() {
        try {
            super.add(MSH.class, true, false);
            super.add(MSA.class, true, false);
            super.add(ERR.class, true, false);
            super.add(ZTL.class, true, false);
            super.add(R09Response_PersonFound.class, true, true);
	} catch(HL7Exception e) {
          log.error("Unexpected error creating R09_RESPONSE - this is probably a bug in the source code generator.", e);
       }
    }
    
    
    /**
     * Return version 2.4 as the implementation is based on Hapi v2 2.4 model
     * @return String 2.4
     */
    public String getVersion() {
       return "2.4";
    }

    /**
     * Return MSG segment
     * @return MSG segment
     */
    public MSH getMSH() { 
       return getTyped("MSH", MSH.class);
    }

    /**
     * Return MSA segment.
     * @return MSA segment
     */
    public MSA getMSA() { 
       return getTyped("MSA", MSA.class);
    }

    /**
     * Return ERR segment.
     * @return ERR segment.
     */
    public ERR getERR() { 
       return getTyped("ERR", ERR.class);
    }

    /**
     * Return ZTL Segment.
     * @return ZTL Segment
     */
    public ZTL getZTL() {
        return getTyped("ZTL", ZTL.class); 
    }

    /**
     * Return specified R09Response_PersonFound
     * @param repNumber the rep number
     * @return R09Response_PersonFound
     */
    public R09Response_PersonFound getPersonFound(int repNumber) {
        return getTyped("PersonFound", repNumber, R09Response_PersonFound.class); 
    }

}
