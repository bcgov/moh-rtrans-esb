//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.05 at 09:56:19 AM PDT 
//
package org.hl7.v3;

import ca.bc.gov.moh.rtrans.entity.SystemResponse;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.hl7.v3.api.AckSystemResponseProvider;
import org.hl7.v3.api.ResponseJaxbMessage;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}PRPA_IN101205CA.MCCI_MT000300BC.Message">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "HCIM_IN_PersonRevisedResult")
public class HCIMINPersonRevisedResult
        extends PRPAIN101205CAMCCIMT000300BCMessage
        implements ResponseJaxbMessage {

    @Override
    public List<SystemResponse> getSystemResponses() {
        List<SystemResponse> systemResponses = new ArrayList<SystemResponse>();

        List<CE> reasonCode = getControlActProcess().getReasonCode();
        for (CE ce : reasonCode) {
            String code = ce.getCode();
            String text = null;

            ED originalText = ce.getOriginalText();
            if (originalText != null) {
                text = originalText.getText();
            }

            SystemResponse systemResponse = AckSystemResponseProvider.createSystemResponse(code, text);

            if (systemResponse != null) {
                systemResponses.add(systemResponse);
            }
        }

        return systemResponses;
    }
}