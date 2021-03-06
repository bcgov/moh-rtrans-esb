//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.25 at 12:54:27 PM PDT 
//


package ca.bc.gov.moh.rtrans.hl7.v3.service.dto;

import java.util.ArrayList;
import java.util.List;




public class HL7Agent {

    
    protected HL7Organization representedOrganization;
    
    protected List<String> classCode;
    
    public HL7Agent(){
        
    }
    
   
    
    public HL7Agent(HL7Organization rep, String code){
        this.representedOrganization=rep;
         if (classCode == null) {
            classCode = new ArrayList<String>();
        }
        this.classCode.add(code);
        
        
    }
    

    public HL7Organization getRepresentedOrganization() {
        return representedOrganization;
    }

    public void setRepresentedOrganization(HL7Organization value) {
        this.representedOrganization = value;
    }

    public List<String> getClassCode() {
        if (classCode == null) {
            classCode = new ArrayList<String>();
        }
        return this.classCode;
    }

}
