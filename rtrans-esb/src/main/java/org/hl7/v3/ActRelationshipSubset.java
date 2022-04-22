
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActRelationshipSubset.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActRelationshipSubset">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ActRelationshipExpectedSubset"/>
 *     &lt;enumeration value="ActRelationshipPastSubset"/>
 *     &lt;enumeration value="FIRST"/>
 *     &lt;enumeration value="FUTSUM"/>
 *     &lt;enumeration value="FUTURE"/>
 *     &lt;enumeration value="LAST"/>
 *     &lt;enumeration value="MAX"/>
 *     &lt;enumeration value="MIN"/>
 *     &lt;enumeration value="NEXT"/>
 *     &lt;enumeration value="PAST"/>
 *     &lt;enumeration value="PREVSUM"/>
 *     &lt;enumeration value="RECENT"/>
 *     &lt;enumeration value="SUM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActRelationshipSubset")
@XmlEnum
public enum ActRelationshipSubset {

    @XmlEnumValue("ActRelationshipExpectedSubset")
    ACT_RELATIONSHIP_EXPECTED_SUBSET("ActRelationshipExpectedSubset"),
    @XmlEnumValue("ActRelationshipPastSubset")
    ACT_RELATIONSHIP_PAST_SUBSET("ActRelationshipPastSubset"),
    FIRST("FIRST"),
    FUTSUM("FUTSUM"),
    FUTURE("FUTURE"),
    LAST("LAST"),
    MAX("MAX"),
    MIN("MIN"),
    NEXT("NEXT"),
    PAST("PAST"),
    PREVSUM("PREVSUM"),
    RECENT("RECENT"),
    SUM("SUM");
    private final String value;

    ActRelationshipSubset(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActRelationshipSubset fromValue(String v) {
        for (ActRelationshipSubset c: ActRelationshipSubset.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
