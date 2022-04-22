
package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MCCI_MT200100BC.Batch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MCCI_MT200100BC.Batch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" form="qualified"/>
 *         &lt;element name="creationTime" type="{urn:hl7-org:v3}TS" form="qualified"/>
 *         &lt;element name="responseModeCode" type="{urn:hl7-org:v3}CS" form="qualified"/>
 *         &lt;element name="versionCode" type="{urn:hl7-org:v3}CS" form="qualified"/>
 *         &lt;element name="interactionId" type="{urn:hl7-org:v3}II" form="qualified"/>
 *         &lt;element name="profileId" type="{urn:hl7-org:v3}II" minOccurs="0" form="qualified"/>
 *         &lt;element name="transmissionQuantity" type="{urn:hl7-org:v3}INT" minOccurs="0" form="qualified"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="HCIM_IN_GetDemographics" type="{http://www.w3.org/2001/XMLSchema}anyType" form="qualified"/>
 *           &lt;element name="QUCR_IN200101BC" type="{http://www.w3.org/2001/XMLSchema}anyType" form="qualified"/>
 *         &lt;/choice>
 *         &lt;element name="receiver" type="{urn:hl7-org:v3}MCCI_MT200100BC.Receiver" form="qualified"/>
 *         &lt;element name="sender" type="{urn:hl7-org:v3}MCCI_MT200100BC.Sender" form="qualified"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MCCI_MT200100BC.Batch", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "creationTime",
    "responseModeCode",
    "versionCode",
    "interactionId",
    "profileId",
    "transmissionQuantity",
    "hciminGetDemographicsOrQUCRIN200101BC",
    "receiver",
    "sender"
})
@XmlSeeAlso({
    MCCIIN200100BC.class
})
public class MCCIMT200100BCBatch {

    protected List<CS> realmCode;
    protected AllInfrastructureRootTypeId typeId;
    protected List<II> templateId;
    @XmlElement(required = true)
    protected II id;
    @XmlElement(required = true)
    protected TS creationTime;
    @XmlElement(required = true)
    protected CS responseModeCode;
    @XmlElement(required = true)
    protected CS versionCode;
    @XmlElement(required = true)
    protected II interactionId;
    protected II profileId;
    protected INT transmissionQuantity;
    @XmlElementRefs({
        @XmlElementRef(name = "HCIM_IN_GetDemographics", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "QUCR_IN200101BC", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> hciminGetDemographicsOrQUCRIN200101BC;
    @XmlElement(required = true)
    protected MCCIMT200100BCReceiver receiver;
    @XmlElement(required = true)
    protected MCCIMT200100BCSender sender;

    /**
     * Gets the value of the realmCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realmCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealmCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CS }
     * 
     * 
     */
    public List<CS> getRealmCode() {
        if (realmCode == null) {
            realmCode = new ArrayList<CS>();
        }
        return this.realmCode;
    }

    /**
     * Gets the value of the typeId property.
     * 
     * @return
     *     possible object is
     *     {@link AllInfrastructureRootTypeId }
     *     
     */
    public AllInfrastructureRootTypeId getTypeId() {
        return typeId;
    }

    /**
     * Sets the value of the typeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllInfrastructureRootTypeId }
     *     
     */
    public void setTypeId(AllInfrastructureRootTypeId value) {
        this.typeId = value;
    }

    /**
     * Gets the value of the templateId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the templateId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemplateId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link II }
     * 
     * 
     */
    public List<II> getTemplateId() {
        if (templateId == null) {
            templateId = new ArrayList<II>();
        }
        return this.templateId;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setId(II value) {
        this.id = value;
    }

    /**
     * Gets the value of the creationTime property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the value of the creationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setCreationTime(TS value) {
        this.creationTime = value;
    }

    /**
     * Gets the value of the responseModeCode property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getResponseModeCode() {
        return responseModeCode;
    }

    /**
     * Sets the value of the responseModeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setResponseModeCode(CS value) {
        this.responseModeCode = value;
    }

    /**
     * Gets the value of the versionCode property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getVersionCode() {
        return versionCode;
    }

    /**
     * Sets the value of the versionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setVersionCode(CS value) {
        this.versionCode = value;
    }

    /**
     * Gets the value of the interactionId property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getInteractionId() {
        return interactionId;
    }

    /**
     * Sets the value of the interactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setInteractionId(II value) {
        this.interactionId = value;
    }

    /**
     * Gets the value of the profileId property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getProfileId() {
        return profileId;
    }

    /**
     * Sets the value of the profileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setProfileId(II value) {
        this.profileId = value;
    }

    /**
     * Gets the value of the transmissionQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getTransmissionQuantity() {
        return transmissionQuantity;
    }

    /**
     * Sets the value of the transmissionQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setTransmissionQuantity(INT value) {
        this.transmissionQuantity = value;
    }

    /**
     * Gets the value of the hciminGetDemographicsOrQUCRIN200101BC property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hciminGetDemographicsOrQUCRIN200101BC property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHCIMINGetDemographicsOrQUCRIN200101BC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getHCIMINGetDemographicsOrQUCRIN200101BC() {
        if (hciminGetDemographicsOrQUCRIN200101BC == null) {
            hciminGetDemographicsOrQUCRIN200101BC = new ArrayList<JAXBElement<?>>();
        }
        return this.hciminGetDemographicsOrQUCRIN200101BC;
    }

    /**
     * Gets the value of the receiver property.
     * 
     * @return
     *     possible object is
     *     {@link MCCIMT200100BCReceiver }
     *     
     */
    public MCCIMT200100BCReceiver getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link MCCIMT200100BCReceiver }
     *     
     */
    public void setReceiver(MCCIMT200100BCReceiver value) {
        this.receiver = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link MCCIMT200100BCSender }
     *     
     */
    public MCCIMT200100BCSender getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link MCCIMT200100BCSender }
     *     
     */
    public void setSender(MCCIMT200100BCSender value) {
        this.sender = value;
    }

}
