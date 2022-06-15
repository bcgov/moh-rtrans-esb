//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.25 at 12:54:27 PM PDT 
//


package ca.bc.gov.moh.rtrans.hl7.v3.service.dto;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import java.util.ArrayList;
import java.util.List;



public class HL7Device {

    protected String id;
    protected HL7Agent asAgent;
    protected List<String> classCode;
    protected String determinerCode;

    public HL7Device(){
        
    }
    public HL7Device(HL7Agent agent,String id, String classcode, String determin){
        this.asAgent=agent;
        if (classCode == null) {
            classCode = new ArrayList<String>();
        }
        this.classCode.add(classcode);
        this.id=id;
        this.determinerCode=determin;
    }
    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public HL7Agent getAsAgent() {
        return asAgent;
    }

    public void setAsAgent(HL7Agent value) {
        this.asAgent = value;
    }



    public List<String> getClassCode() {
        if (classCode == null) {
            classCode = new ArrayList<String>();
        }
        return this.classCode;
    }

    public String getDeterminerCode() {
        if (determinerCode == null) {
            return "INSTANCE";
        } else {
            return determinerCode;
        }
    }

    public void setDeterminerCode(String value) {
        this.determinerCode = value;
    }

}