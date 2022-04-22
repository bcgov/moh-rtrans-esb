
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Other.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Other">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ASKU"/>
 *     &lt;enumeration value="DER"/>
 *     &lt;enumeration value="INV"/>
 *     &lt;enumeration value="MSK"/>
 *     &lt;enumeration value="NA"/>
 *     &lt;enumeration value="NASK"/>
 *     &lt;enumeration value="NAV"/>
 *     &lt;enumeration value="NI"/>
 *     &lt;enumeration value="NINF"/>
 *     &lt;enumeration value="OTH"/>
 *     &lt;enumeration value="PINF"/>
 *     &lt;enumeration value="QS"/>
 *     &lt;enumeration value="TRC"/>
 *     &lt;enumeration value="UNC"/>
 *     &lt;enumeration value="UNK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Other")
@XmlEnum
public enum Other {

    ASKU,
    DER,
    INV,
    MSK,
    NA,
    NASK,
    NAV,
    NI,
    NINF,
    OTH,
    PINF,
    QS,
    TRC,
    UNC,
    UNK;

    public String value() {
        return name();
    }

    public static Other fromValue(String v) {
        return valueOf(v);
    }

}
