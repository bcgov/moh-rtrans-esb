
package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoleLinkIsBackupFor.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleLinkIsBackupFor">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="BACKUP"/>
 *     &lt;enumeration value="DIRAUTH"/>
 *     &lt;enumeration value="IDENT"/>
 *     &lt;enumeration value="INDAUTH"/>
 *     &lt;enumeration value="PART"/>
 *     &lt;enumeration value="REL"/>
 *     &lt;enumeration value="REPL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleLinkIsBackupFor")
@XmlEnum
public enum RoleLinkIsBackupFor {

    BACKUP,
    DIRAUTH,
    IDENT,
    INDAUTH,
    PART,
    REL,
    REPL;

    public String value() {
        return name();
    }

    public static RoleLinkIsBackupFor fromValue(String v) {
        return valueOf(v);
    }

}
