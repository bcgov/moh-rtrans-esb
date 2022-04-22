
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActStatusCompleted.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActStatusCompleted">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="aborted"/>
 *     &lt;enumeration value="active"/>
 *     &lt;enumeration value="cancelled"/>
 *     &lt;enumeration value="completed"/>
 *     &lt;enumeration value="held"/>
 *     &lt;enumeration value="new"/>
 *     &lt;enumeration value="normal"/>
 *     &lt;enumeration value="nullified"/>
 *     &lt;enumeration value="obsolete"/>
 *     &lt;enumeration value="suspended"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActStatusCompleted")
@XmlEnum
public enum ActStatusCompleted {

    @XmlEnumValue("aborted")
    ABORTED("aborted"),
    @XmlEnumValue("active")
    ACTIVE("active"),
    @XmlEnumValue("cancelled")
    CANCELLED("cancelled"),
    @XmlEnumValue("completed")
    COMPLETED("completed"),
    @XmlEnumValue("held")
    HELD("held"),
    @XmlEnumValue("new")
    NEW("new"),
    @XmlEnumValue("normal")
    NORMAL("normal"),
    @XmlEnumValue("nullified")
    NULLIFIED("nullified"),
    @XmlEnumValue("obsolete")
    OBSOLETE("obsolete"),
    @XmlEnumValue("suspended")
    SUSPENDED("suspended");
    private final String value;

    ActStatusCompleted(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActStatusCompleted fromValue(String v) {
        for (ActStatusCompleted c: ActStatusCompleted.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
