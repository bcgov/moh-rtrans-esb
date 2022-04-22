/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;

import ca.bc.gov.moh.rtrans.entity.AttributeStatus;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographicsResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.JaxbToEntityTypeConverter;
import java.util.List;
import org.apache.camel.Converter;
import org.hl7.v3.HCIMINGetDemographicsResponse;
import org.hl7.v3.II;
import org.hl7.v3.PRPAIN101102CAMFMIMT700746BCParticipant;
import org.hl7.v3.QUPAMT101102BCIdentifiedPerson;
import org.hl7.v3.QUPAMT101102BCOtherIDs;

/**
 *
 * @author conrad.gustafson
 */
@Converter
public class GetDemographicsJaxbToEntityTypeConverter extends JaxbToEntityTypeConverter {

    @Converter
    public static GetDemographicsResponse convert(HCIMINGetDemographicsResponse jaxb) {

        GetDemographicsResponse getDemographicsResponse = new GetDemographicsResponse();

        convertCommonFields(getDemographicsResponse, jaxb);

        setResult(jaxb, getDemographicsResponse);

        return getDemographicsResponse;
    }

    private static void setResult(HCIMINGetDemographicsResponse jaxb, GetDemographicsResponse demographicsResponse) {
        if (jaxb == null || jaxb.getControlActProcess() == null) {
            return;
        }

        List<PRPAIN101102CAMFMIMT700746BCParticipant> subject = jaxb.getControlActProcess().getSubject();

        if (subject != null && !subject.isEmpty()) {
            QUPAMT101102BCIdentifiedPerson target = subject.get(0).getTarget();

            // map otherIds from JAXB to entity
            List<IdentifierAttribute> identifiers = demographicsResponse.getSearchResult().getIdentifier();

            List<QUPAMT101102BCOtherIDs> otherIDs = target.getIdentifiedPerson().getPlayedOtherIDs();

            
            for (QUPAMT101102BCOtherIDs otherId : otherIDs) {
                List<II> otherIdList = otherId.getId();

                if (otherIdList != null && !otherIdList.isEmpty()) {
                    for (II ii : otherIdList) {
                        IdentifierAttribute identifierAttribute = new IdentifierAttribute();

                        if (!ii.getAssigningAuthorityName().equalsIgnoreCase(IdentifierTypes.MRN.name())
                                || !ii.getAssigningAuthorityName().equalsIgnoreCase(IdentifierTypes.BCPHN.name())) {
                            identifierAttribute.setSource(ii.getAssigningAuthorityName());
                            identifierAttribute.setValue(ii.getExtension());
                            IdentifierTypes type = IdentifierTypes.fromOIDtoType(ii.getRoot());
                            identifierAttribute.setType(type);
                            if (ii.isDisplayable() != null) {
                                identifierAttribute.setStatus(ii.isDisplayable() ? AttributeStatus.Active : AttributeStatus.Merged);
                            } else {
                                identifierAttribute.setStatus(AttributeStatus.Active);
                            }
                        }

                        identifiers.add(identifierAttribute);
                    }
                }
            }
            // set otherIds in entity
            demographicsResponse.getSearchResult().setIdentifier(identifiers);

            JaxbToEntityTypeConverter.convertPerson(demographicsResponse.getSearchResult(), target);

        }

    }

}
