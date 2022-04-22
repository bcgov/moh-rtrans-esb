//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.25 at 12:54:27 PM PDT 
//


package ca.bc.gov.moh.rtrans.hl7.v3.service.dto;

import java.util.ArrayList;
import java.util.List;





public class HL7ControlActProcess {

    
    
    protected List<HL7Participant> subject;
    
    protected HL7QueryAck queryAck;
    
    protected HL7QueryByParameterPayload queryByParameterPayload;
    
    protected String classCode;
    
    protected List<String> moodCode;

    public List<HL7Participant> getSubject() {
        if (subject == null) {
            subject = new ArrayList<HL7Participant>();
        }
        return this.subject;
    }

    /**
     * Gets the value of the queryAck property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HL7QueryAck }{@code >}
     *     
     */
    public HL7QueryAck getQueryAck() {
        return queryAck;
    }

    /**
     * Sets the value of the queryAck property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HL7QueryAck }{@code >}
     *     
     */
    public void setQueryAck(HL7QueryAck value) {
        this.queryAck = value;
    }

    /**
     * Gets the value of the queryByParameterPayload property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HL7QueryByParameterPayload }{@code >}
     *     
     */
    public HL7QueryByParameterPayload getQueryByParameterPayload() {
        return queryByParameterPayload;
    }

    /**
     * Sets the value of the queryByParameterPayload property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HL7QueryByParameterPayload }{@code >}
     *     
     */
    public void setQueryByParameterPayload(HL7QueryByParameterPayload value) {
        this.queryByParameterPayload = value;
    }

    
    public String getClassCode() {
        return classCode;
    }

    
    public void setClassCode(String value) {
        this.classCode = value;
    }

    public List<String> getMoodCode() {
        if (moodCode == null) {
            moodCode = new ArrayList<String>();
        }
        return this.moodCode;
    }

}
