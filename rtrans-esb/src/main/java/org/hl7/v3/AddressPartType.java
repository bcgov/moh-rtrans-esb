
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressPartType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AddressPartType">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ADL"/>
 *     &lt;enumeration value="AL"/>
 *     &lt;enumeration value="BNN"/>
 *     &lt;enumeration value="BNR"/>
 *     &lt;enumeration value="BNS"/>
 *     &lt;enumeration value="CAR"/>
 *     &lt;enumeration value="CEN"/>
 *     &lt;enumeration value="CNT"/>
 *     &lt;enumeration value="CPA"/>
 *     &lt;enumeration value="CTY"/>
 *     &lt;enumeration value="DAL"/>
 *     &lt;enumeration value="DEL"/>
 *     &lt;enumeration value="DINST"/>
 *     &lt;enumeration value="DINSTA"/>
 *     &lt;enumeration value="DINSTQ"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="DMOD"/>
 *     &lt;enumeration value="DMODID"/>
 *     &lt;enumeration value="INT"/>
 *     &lt;enumeration value="POB"/>
 *     &lt;enumeration value="PRE"/>
 *     &lt;enumeration value="SAL"/>
 *     &lt;enumeration value="STA"/>
 *     &lt;enumeration value="STB"/>
 *     &lt;enumeration value="STR"/>
 *     &lt;enumeration value="STTYP"/>
 *     &lt;enumeration value="UNID"/>
 *     &lt;enumeration value="UNIT"/>
 *     &lt;enumeration value="ZIP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AddressPartType")
@XmlEnum
public enum AddressPartType {

    ADL,
    AL,
    BNN,
    BNR,
    BNS,
    CAR,
    CEN,
    CNT,
    CPA,
    CTY,
    DAL,
    DEL,
    DINST,
    DINSTA,
    DINSTQ,
    DIR,
    DMOD,
    DMODID,
    INT,
    POB,
    PRE,
    SAL,
    STA,
    STB,
    STR,
    STTYP,
    UNID,
    UNIT,
    ZIP;

    public String value() {
        return name();
    }

    public static AddressPartType fromValue(String v) {
        return valueOf(v);
    }

}
