
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActMoodAppointment.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActMoodAppointment">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="APT"/>
 *     &lt;enumeration value="ARQ"/>
 *     &lt;enumeration value="CRT"/>
 *     &lt;enumeration value="DEF"/>
 *     &lt;enumeration value="EVN"/>
 *     &lt;enumeration value="EVN.CRT"/>
 *     &lt;enumeration value="EXPEC"/>
 *     &lt;enumeration value="GOL"/>
 *     &lt;enumeration value="INT"/>
 *     &lt;enumeration value="OPT"/>
 *     &lt;enumeration value="PERM"/>
 *     &lt;enumeration value="PERMRQ"/>
 *     &lt;enumeration value="PRMS"/>
 *     &lt;enumeration value="PRP"/>
 *     &lt;enumeration value="RMD"/>
 *     &lt;enumeration value="RQO"/>
 *     &lt;enumeration value="RSK"/>
 *     &lt;enumeration value="SLOT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActMoodAppointment")
@XmlEnum
public enum ActMoodAppointment {

    APT("APT"),
    ARQ("ARQ"),
    CRT("CRT"),
    DEF("DEF"),
    EVN("EVN"),
    @XmlEnumValue("EVN.CRT")
    EVN_CRT("EVN.CRT"),
    EXPEC("EXPEC"),
    GOL("GOL"),
    INT("INT"),
    OPT("OPT"),
    PERM("PERM"),
    PERMRQ("PERMRQ"),
    PRMS("PRMS"),
    PRP("PRP"),
    RMD("RMD"),
    RQO("RQO"),
    RSK("RSK"),
    SLOT("SLOT");
    private final String value;

    ActMoodAppointment(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActMoodAppointment fromValue(String v) {
        for (ActMoodAppointment c: ActMoodAppointment.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
