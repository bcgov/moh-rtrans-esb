/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;

import ca.bc.gov.moh.rtrans.entity.AttributeStatus;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.ProbabalisticPersonSearchResult;
import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidatesResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.JaxbToEntityTypeConverter;
import java.util.List;
import org.apache.camel.Converter;
import org.hl7.v3.HCIMINFindCandidatesResponse;
import org.hl7.v3.II;
import org.hl7.v3.PRPAIN101104BCMFMIMT700746BCParticipant;
import org.hl7.v3.QUPAMT101104BCIdentifiedPerson;
import org.hl7.v3.QUPAMT101104BCObservationEvent;
import org.hl7.v3.QUPAMT101104BCOtherIDs;

/**
 *
 * @author conrad.gustafson
 */
@Converter
public class FindCandidatesJaxbToEntityTypeConverter extends JaxbToEntityTypeConverter {

    @Converter
    public static FindCandidatesResponse convert(HCIMINFindCandidatesResponse jaxb) {

        FindCandidatesResponse findCandidatesResponse = new FindCandidatesResponse();

        convertCommonFields(findCandidatesResponse, jaxb);

        List<PRPAIN101104BCMFMIMT700746BCParticipant> subjectList = jaxb.getControlActProcess().getSubject();

        if (subjectList != null && !subjectList.isEmpty()) {
            List<ProbabalisticPersonSearchResult> searchResults = findCandidatesResponse.getSearchResults();

            for (PRPAIN101104BCMFMIMT700746BCParticipant subject : subjectList) {
                ProbabalisticPersonSearchResult searchResult = new ProbabalisticPersonSearchResult();
                searchResults.add(searchResult);
                QUPAMT101104BCObservationEvent observationEvent = subject.getTarget().getSubjectOf().getValue().getObservationEvent();
                String value = observationEvent.getValue().getValue();
                float matchScore = Float.parseFloat(value);
                searchResult.setMatchScore(matchScore);
                final QUPAMT101104BCIdentifiedPerson target = subject.getTarget();

                // map otherIds from JAXB to entity
                List<IdentifierAttribute> identifiers = searchResult.getPerson().getIdentifier();

                List<QUPAMT101104BCOtherIDs> otherIDs = target.getIdentifiedPerson().getPlayedOtherIDs();

                for (QUPAMT101104BCOtherIDs otherId : otherIDs) {
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
                searchResult.getPerson().setIdentifier(identifiers);

                convertPerson(searchResult.getPerson(), target);

            }
        }

        return findCandidatesResponse;

    }
}
