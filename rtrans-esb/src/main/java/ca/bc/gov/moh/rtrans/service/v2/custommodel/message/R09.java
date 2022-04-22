/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2.custommodel.message;

import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.QRD;

/**
 * R09 Message.
 * @author kuan.fan
 */
public class R09 extends AbstractMessage{
    /**
     * Creates a new R09 message with DefaultModelClassFactory. 
     */ 
    public R09() { 
       this(new DefaultModelClassFactory());
    }

    /** 
     * Creates a new R09 message with custom ModelClassFactory.
     * @param factory
     */
    public R09(ModelClassFactory factory) {
       super(factory);
       init();
    }

    /**
     * Initiate the object.
     */
    private void init() {
        try {
            
            this.add(MSH.class, true, false);                        
            this.add(ZHD.class, true, false);
            this.add(QRD.class, true, false);
            this.add(PID.class, true, false);
            this.add(ZIA.class, true, false);
            
        } catch(HL7Exception e) {
            log.error("Unexpected error creating R09 - this is probably a bug in the source code generator.", e);
       }
    }

    /** 
     * Returns "2.4"
     */
    public String getVersion() {
       return "2.4";
    }
    /**
     * Returns MSH segment
     * @return MSH
     */
    public MSH getMSH() { 
       return getTyped("MSH", MSH.class);
    }   
    /**
     * Returns ZHD segment
     * @return ZHD
     */
    public ZHD getZHD() { 
       return getTyped("ZHD", ZHD.class);
    }
    /**
     * Returns PID segment
     * @return PID
     */
    public PID getPID() { 
       return getTyped("PID", PID.class);
    }
    /**
     * Returns QRD segment
     * @return QRD
     */
    public QRD getQRD() { 
       return getTyped("QRD", QRD.class);
    }
    /**
     * Returns ZIA segment
     * @return ZIA
     */
    public ZIA getZIA() { 
       return getTyped("ZIA", ZIA.class);
    }

}
