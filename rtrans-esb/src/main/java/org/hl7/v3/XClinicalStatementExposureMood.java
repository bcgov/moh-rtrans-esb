//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.05 at 09:56:19 AM PDT 
//


package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_ClinicalStatementExposureMood.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_ClinicalStatementExposureMood">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="CRT"/>
 *     &lt;enumeration value="DEF"/>
 *     &lt;enumeration value="EVN"/>
 *     &lt;enumeration value="EVN.CRT"/>
 *     &lt;enumeration value="RSK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "x_ClinicalStatementExposureMood")
@XmlEnum
public enum XClinicalStatementExposureMood {

    CRT("CRT"),
    DEF("DEF"),
    EVN("EVN"),
    @XmlEnumValue("EVN.CRT")
    EVN_CRT("EVN.CRT"),
    RSK("RSK");
    private final String value;

    XClinicalStatementExposureMood(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static XClinicalStatementExposureMood fromValue(String v) {
        for (XClinicalStatementExposureMood c: XClinicalStatementExposureMood.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
