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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



public class HL7QueryByParameterPayload {

   
    protected IdentifierAttribute personId;

    public IdentifierAttribute getPersonId() {
        return personId;
    }

    public void setPersonId(IdentifierAttribute value) {
        this.personId = value;
    }

}
