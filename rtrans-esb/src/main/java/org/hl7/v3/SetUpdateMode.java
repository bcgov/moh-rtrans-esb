
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetUpdateMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SetUpdateMode">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="AR"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="K"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="R"/>
 *     &lt;enumeration value="REF"/>
 *     &lt;enumeration value="U"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SetUpdateMode")
@XmlEnum
public enum SetUpdateMode {

    A,
    AR,
    D,
    K,
    N,
    R,
    REF,
    U;

    public String value() {
        return name();
    }

    public static SetUpdateMode fromValue(String v) {
        return valueOf(v);
    }

}
