/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2.custommodel.message;

/**
 *
 * @author Kuan Fan
 */

import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.components.ZAD;
import ca.uhn.hl7v2.model.v24.datatype.*;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.AbstractSegment;

/**
 * Represents an HL7 ZIA message segment. 
 */
@SuppressWarnings("unused")
public class ZIA extends AbstractSegment {
    
    /** 
     * Creates a new ZIA segment
     * @param parent
     * @param factory
     */
    public ZIA(Group parent, ModelClassFactory factory) {
       super(parent, factory);
       init(factory);
    }
    
    /**
     * Initialize ZIA
     * @param factory ModelClassFactory
     */
    //FIXME parameter should be removed ?
    private void init(ModelClassFactory factory) {
        try {   
            this.add(ST.class, true, 1, 1, new Object[]{ getMessage() }, "Client Research Code"); 
            this.add(DT.class, true, 1, 8, new Object[]{ getMessage() }, "BC Residency Date "); 
            this.add(NM.class, true, 1, 2, new Object[]{ getMessage() }, "Family Unit Size"); 
            this.add(TS.class, true, 1, 26, new Object[]{ getMessage() }, "Last Change Timestamp"); 
            this.add(ST.class, true, 1, 20, new Object[]{ getMessage() }, "Last Change Id"); 
            this.add(DT.class, true, 1, 8, new Object[]{ getMessage() }, "Latest Assessment Effective Date"); 
            this.add(NM.class, true, 1, 1, new Object[]{ getMessage() }, "Adult Day Care Count"); 
            this.add(NM.class, true, 1, 1, new Object[]{ getMessage() }, "Homemaker Care Count "); 
            this.add(NM.class, true, 1, 1, new Object[]{ getMessage() }, "Group Home Count"); 
            this.add(ID.class, true, 1, 1, new Object[]{ getMessage() }, "Home Nursing Care Indicator"); 
            this.add(ID.class, true, 1, 1, new Object[]{ getMessage() }, "Physiotherapy Indicator"); 
            this.add(ID.class, true, 1, 1, new Object[]{ getMessage() }, "Occupational Therapy Indicator"); 
            this.add(ID.class, true, 1, 1, new Object[]{ getMessage() }, "PHN Verified Flag"); 
            this.add(IS.class, true, 1, 1, new Object[]{ getMessage() }, "BC Residency Flag"); 
            this.add(XPN.class, true, 1, 162, new Object[]{ getMessage() }, "Extended Person Name"); 
            this.add(ZAD.class, true, 1, 532, new Object[]{ getMessage() }, "Extended Address"); 
            this.add(XTN.class, true, 1, 233, new Object[]{ getMessage() }, "Extended Telephone Number"); 
            this.add(ST.class, true, 1, 50, new Object[]{ getMessage() }, "Patient Display Address"); 
            this.add(XAD.class, true, 1, 137, new Object[]{ getMessage() }, "Birth Location"); 
            this.add(XAD.class, true, 1, 137, new Object[]{ getMessage() }, "Death Location"); 
            this.add(ST.class, true, 1, 4, new Object[]{ getMessage() }, "Death Event Source"); 
            this.add(SI.class, true, 1, 4, new Object[]{ getMessage() }, "Set ID – ZIA"); 
            this.add(ID.class, true, 1, 1, new Object[]{ getMessage() }, "Address Validation Override Indicator"); 
            this.add(ID.class, true, 1, 1, new Object[]{ getMessage() }, "Immigration or Visa Code"); 
            this.add(ID.class, true, 1, 2, new Object[]{ getMessage() }, "Prior Residence Code"); 

       } catch(HL7Exception e) {
            log.error("Unexpected error creating ZIA - this is probably a bug in the source code generator.", e);
       }
    }
    
    /**
     * ZIA-15 - Create if necessary
     * @return retVal
     */
    public XPN getExtendedPersonName() {
        XPN retVal = this.getTypedField(15, 0);
        return retVal;
    }
    public XPN getZIA15_ExtendedPersonName() {
        XPN retVal = this.getTypedField(15, 0);
        return retVal;
    }
    
    /**
     * ZIA-16 - Create if necessary
     * @return retVal
     */
    public ZAD getZIA16_ExtendedAddress() {
        ZAD retVal = this.getTypedField(16, 0);
        return retVal;
    }
    public ZAD getExtendedAddress() {
        ZAD retVal = this.getTypedField(16, 0);
        return retVal;
    }
    
    /**
     * ZIA-17 - Create if necessary
     * @return retVal
     */
    public XTN getExtendedTelephoneNumber() {
        XTN retVal = this.getTypedField(17, 0);
        return retVal;
    }
    public XTN getZIA17_ExtendedTelephoneNumber() {
        XTN retVal = this.getTypedField(17, 0);
        return retVal;
    }
    
    public ST getPatientDisplayAddress() {
        ST retVal = this.getTypedField(18, 0);
        return retVal;
    }
    public ST getZIA18_PatientDisplayAddress() {
        ST retVal = this.getTypedField(18, 0);
        return retVal;
    }
    
    public SI getSetIdZIA() {
        SI retVal = this.getTypedField(22, 0);
        return retVal;
    }
    
}
