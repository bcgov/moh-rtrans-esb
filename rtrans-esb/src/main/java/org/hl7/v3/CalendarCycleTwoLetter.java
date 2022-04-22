
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CalendarCycleTwoLetter.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CalendarCycleTwoLetter">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="CW"/>
 *     &lt;enumeration value="W"/>
 *     &lt;enumeration value="CY"/>
 *     &lt;enumeration value="Y"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="DM"/>
 *     &lt;enumeration value="DW"/>
 *     &lt;enumeration value="J"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="HD"/>
 *     &lt;enumeration value="M"/>
 *     &lt;enumeration value="MY"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="NH"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="SN"/>
 *     &lt;enumeration value="CD"/>
 *     &lt;enumeration value="CH"/>
 *     &lt;enumeration value="CM"/>
 *     &lt;enumeration value="CN"/>
 *     &lt;enumeration value="CS"/>
 *     &lt;enumeration value="DY"/>
 *     &lt;enumeration value="WY"/>
 *     &lt;enumeration value="CD"/>
 *     &lt;enumeration value="CH"/>
 *     &lt;enumeration value="CM"/>
 *     &lt;enumeration value="CN"/>
 *     &lt;enumeration value="CS"/>
 *     &lt;enumeration value="CW"/>
 *     &lt;enumeration value="CY"/>
 *     &lt;enumeration value="DM"/>
 *     &lt;enumeration value="DW"/>
 *     &lt;enumeration value="DY"/>
 *     &lt;enumeration value="HD"/>
 *     &lt;enumeration value="MY"/>
 *     &lt;enumeration value="NH"/>
 *     &lt;enumeration value="SN"/>
 *     &lt;enumeration value="WY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CalendarCycleTwoLetter")
@XmlEnum
public enum CalendarCycleTwoLetter {

    CW,
    W,
    CY,
    Y,
    D,
    DM,
    DW,
    J,
    H,
    HD,
    M,
    MY,
    N,
    NH,
    S,
    SN,
    CD,
    CH,
    CM,
    CN,
    CS,
    DY,
    WY;

    public String value() {
        return name();
    }

    public static CalendarCycleTwoLetter fromValue(String v) {
        return valueOf(v);
    }

}
