
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParticipationSubset.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParticipationSubset">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="FUTURE"/>
 *     &lt;enumeration value="FUTSUM"/>
 *     &lt;enumeration value="LAST"/>
 *     &lt;enumeration value="NEXT"/>
 *     &lt;enumeration value="PAST"/>
 *     &lt;enumeration value="FIRST"/>
 *     &lt;enumeration value="PREVSUM"/>
 *     &lt;enumeration value="RECENT"/>
 *     &lt;enumeration value="SUM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParticipationSubset")
@XmlEnum
public enum ParticipationSubset {

    FUTURE,
    FUTSUM,
    LAST,
    NEXT,
    PAST,
    FIRST,
    PREVSUM,
    RECENT,
    SUM;

    public String value() {
        return name();
    }

    public static ParticipationSubset fromValue(String v) {
        return valueOf(v);
    }

}
