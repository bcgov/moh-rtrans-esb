
package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}MCCI_MT200101BC.Batch">
 *       &lt;attribute name="ITSVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="XML_1.0" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "MCCI_IN200101BC")
public class MCCIIN200101BC
    extends MCCIMT200101BCBatch
{

    @XmlAttribute(name = "ITSVersion", required = true)
    protected String itsVersion;

    /**
     * Gets the value of the itsVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getITSVersion() {
        if (itsVersion == null) {
            return "XML_1.0";
        } else {
            return itsVersion;
        }
    }

    /**
     * Sets the value of the itsVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setITSVersion(String value) {
        this.itsVersion = value;
    }

}
