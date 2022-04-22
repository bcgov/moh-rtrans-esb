
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleStatusTerminated.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleStatusTerminated">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="active"/>
 *     &lt;enumeration value="cancelled"/>
 *     &lt;enumeration value="normal"/>
 *     &lt;enumeration value="nullified"/>
 *     &lt;enumeration value="pending"/>
 *     &lt;enumeration value="suspended"/>
 *     &lt;enumeration value="terminated"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleStatusTerminated")
@XmlEnum
public enum RoleStatusTerminated {

    @XmlEnumValue("active")
    ACTIVE("active"),
    @XmlEnumValue("cancelled")
    CANCELLED("cancelled"),
    @XmlEnumValue("normal")
    NORMAL("normal"),
    @XmlEnumValue("nullified")
    NULLIFIED("nullified"),
    @XmlEnumValue("pending")
    PENDING("pending"),
    @XmlEnumValue("suspended")
    SUSPENDED("suspended"),
    @XmlEnumValue("terminated")
    TERMINATED("terminated");
    private final String value;

    RoleStatusTerminated(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RoleStatusTerminated fromValue(String v) {
        for (RoleStatusTerminated c: RoleStatusTerminated.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
