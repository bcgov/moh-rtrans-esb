
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContextControlAdditiveNon-propagating.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContextControlAdditiveNon-propagating">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="AN"/>
 *     &lt;enumeration value="AP"/>
 *     &lt;enumeration value="ON"/>
 *     &lt;enumeration value="OP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContextControlAdditiveNon-propagating")
@XmlEnum
public enum ContextControlAdditiveNonPropagating {

    AN,
    AP,
    ON,
    OP;

    public String value() {
        return name();
    }

    public static ContextControlAdditiveNonPropagating fromValue(String v) {
        return valueOf(v);
    }

}
