/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.entity.transaction.converter.reviseperson;

import ca.bc.gov.moh.rtrans.entity.AttributeStatus;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.transaction.RevisePersonResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.JaxbToEntityTypeConverter;
import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Converter;
import org.hl7.v3.HCIMINPersonRevisedResult;
import org.hl7.v3.II;
import org.slf4j.LoggerFactory;

/**
 *
 * @author conrad.gustafson
 */
@Converter
public class RevisePersonJaxbToEntityTypeConverter extends JaxbToEntityTypeConverter  {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RevisePersonJaxbToEntityTypeConverter.class);

    @Converter
    public static RevisePersonResponse convert(HCIMINPersonRevisedResult jaxb) {

        RevisePersonResponse revisePersonResponse = new RevisePersonResponse();
        
        convertCommonFields(revisePersonResponse, jaxb);
        
        List<IdentifierAttribute> identifiers = revisePersonResponse.getIdentifiers();
        if (identifiers == null) {
            identifiers = new ArrayList<IdentifierAttribute>();
            revisePersonResponse.setIdentifiers(identifiers);
        }
        List<II> idList = null;
        
        try {
        idList = jaxb.getControlActProcess().getSubject().getRegistrationEvent().getSubject1().getIdentifiedPerson().getIdentifiedPerson().getId();
        } catch (NullPointerException npe) {
            // no problem. This response does not have any IDs
        }
        
        if (idList != null && !idList.isEmpty()) {
            for (II ii : idList) {
                IdentifierAttribute identifierAttribute = new IdentifierAttribute();
                identifierAttribute.setSource(ii.getAssigningAuthorityName());
                String id = ii.getExtension(); // DEBUG
                identifierAttribute.setValue(ii.getExtension());
                IdentifierTypes type = IdentifierTypes.fromOIDtoType(ii.getRoot());
                identifierAttribute.setType(type);
                if (ii.isDisplayable() != null) {
                    identifierAttribute.setStatus(ii.isDisplayable() ? AttributeStatus.Active : AttributeStatus.Merged);
                }else{
                    identifierAttribute.setStatus(AttributeStatus.Active);
                }
                identifiers.add(identifierAttribute);
            }
        }

        return revisePersonResponse;
    }
}