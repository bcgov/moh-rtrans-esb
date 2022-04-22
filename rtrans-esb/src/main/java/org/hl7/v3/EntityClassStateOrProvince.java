
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityClassStateOrProvince.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntityClassStateOrProvince">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ANM"/>
 *     &lt;enumeration value="CER"/>
 *     &lt;enumeration value="CHEM"/>
 *     &lt;enumeration value="CITY"/>
 *     &lt;enumeration value="CONT"/>
 *     &lt;enumeration value="COUNTRY"/>
 *     &lt;enumeration value="COUNTY"/>
 *     &lt;enumeration value="DEV"/>
 *     &lt;enumeration value="ENT"/>
 *     &lt;enumeration value="FOOD"/>
 *     &lt;enumeration value="HCE"/>
 *     &lt;enumeration value="HOLD"/>
 *     &lt;enumeration value="LIV"/>
 *     &lt;enumeration value="MAT"/>
 *     &lt;enumeration value="MIC"/>
 *     &lt;enumeration value="MMAT"/>
 *     &lt;enumeration value="MODDV"/>
 *     &lt;enumeration value="NAT"/>
 *     &lt;enumeration value="NLIV"/>
 *     &lt;enumeration value="ORG"/>
 *     &lt;enumeration value="PLC"/>
 *     &lt;enumeration value="PLNT"/>
 *     &lt;enumeration value="PROVINCE"/>
 *     &lt;enumeration value="PSN"/>
 *     &lt;enumeration value="PUB"/>
 *     &lt;enumeration value="RGRP"/>
 *     &lt;enumeration value="STATE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntityClassStateOrProvince")
@XmlEnum
public enum EntityClassStateOrProvince {

    ANM,
    CER,
    CHEM,
    CITY,
    CONT,
    COUNTRY,
    COUNTY,
    DEV,
    ENT,
    FOOD,
    HCE,
    HOLD,
    LIV,
    MAT,
    MIC,
    MMAT,
    MODDV,
    NAT,
    NLIV,
    ORG,
    PLC,
    PLNT,
    PROVINCE,
    PSN,
    PUB,
    RGRP,
    STATE;

    public String value() {
        return name();
    }

    public static EntityClassStateOrProvince fromValue(String v) {
        return valueOf(v);
    }

}
