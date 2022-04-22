
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HL7UpdateMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HL7UpdateMode">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="K"/>
 *     &lt;enumeration value="R"/>
 *     &lt;enumeration value="REF"/>
 *     &lt;enumeration value="U"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HL7UpdateMode")
@XmlEnum
public enum HL7UpdateMode {

    A,
    D,
    K,
    R,
    REF,
    U;

    public String value() {
        return name();
    }

    public static HL7UpdateMode fromValue(String v) {
        return valueOf(v);
    }

}
