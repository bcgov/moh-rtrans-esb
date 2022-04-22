//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.05 at 09:56:19 AM PDT 
//


package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_URIURLScheme.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_URIURLScheme">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="mailto"/>
 *     &lt;enumeration value="file"/>
 *     &lt;enumeration value="http"/>
 *     &lt;enumeration value="https"/>
 *     &lt;enumeration value="nfs"/>
 *     &lt;enumeration value="ftp"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "x_URIURLScheme")
@XmlEnum
public enum XURIURLScheme {

    @XmlEnumValue("mailto")
    MAILTO("mailto"),
    @XmlEnumValue("file")
    FILE("file"),
    @XmlEnumValue("http")
    HTTP("http"),
    @XmlEnumValue("https")
    HTTPS("https"),
    @XmlEnumValue("nfs")
    NFS("nfs"),
    @XmlEnumValue("ftp")
    FTP("ftp");
    private final String value;

    XURIURLScheme(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static XURIURLScheme fromValue(String v) {
        for (XURIURLScheme c: XURIURLScheme.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
