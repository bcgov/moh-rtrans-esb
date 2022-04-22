/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.reviseperson;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.DateAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonRelationship;
import ca.bc.gov.moh.rtrans.entity.PhoneAttribute;
import ca.bc.gov.moh.rtrans.entity.transaction.RevisePerson;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HealthAuthorityConstants;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.NonQueryEntityToJaxbTypeConverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.camel.Converter;
import org.apache.commons.lang.StringUtils;
import org.hl7.v3.AD;
import org.hl7.v3.CE;
import org.hl7.v3.COCTMT030200BCPerson;
import org.hl7.v3.CS;
import org.hl7.v3.CV;
import org.hl7.v3.HCIMINPersonRevised;
import org.hl7.v3.II;
import org.hl7.v3.NullFlavor;
import org.hl7.v3.ObjectFactory;
import org.hl7.v3.PN;
import org.hl7.v3.PRPAMT101002BCIdentifiedPerson;
import org.hl7.v3.PRPAMT101002BCOtherIDs;
import org.hl7.v3.PRPAMT101002BCPerson;
import org.hl7.v3.PRPAMT101002BCPersonalRelationship;
import org.hl7.v3.TEL;
import org.hl7.v3.TS;
import org.slf4j.LoggerFactory;

/**
 *
 * @author conrad.gustafson
 */
@Converter
public class RevisePersonEntityToJaxbTypeConverter extends NonQueryEntityToJaxbTypeConverter {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RevisePersonEntityToJaxbTypeConverter.class);
    private static final ObjectFactory objectFactory = new ObjectFactory();

    @Converter
    public static HCIMINPersonRevised convert(RevisePerson request)
            throws InstantiationException, IllegalAccessException {

        HCIMINPersonRevised jaxb = new HCIMINPersonRevised();

        convertCommonFields(jaxb, request);
        setStatusCode(jaxb, request);
        setPersonId(jaxb, request);
        setAddress(jaxb, request);
        setTelecom(jaxb, request);
        setIdentifiedPerson(jaxb, request);

        return jaxb;
    }

    private static PRPAMT101002BCIdentifiedPerson getIdentifiedPerson(HCIMINPersonRevised jaxb) {
        return jaxb.getControlActProcess().getSubject().getRegistrationEvent().getSubject1().getIdentifiedPerson();
    }

    private static void setStatusCode(HCIMINPersonRevised jaxb, RevisePerson request) {
        CS statusCodeCS = new CS();
        statusCodeCS.setCode(HealthAuthorityConstants.STATUS_CODE_ACTIVE);
        jaxb.getControlActProcess().getSubject().getRegistrationEvent().setStatusCode(statusCodeCS);
    }

    private static void setPersonId(HCIMINPersonRevised jaxb, RevisePerson request) {
        List<IdentifierAttribute> personIdentifierList = request.getPerson().getIdentifier();
        if (personIdentifierList == null || personIdentifierList.isEmpty()) {
            return;
        }

        PRPAMT101002BCIdentifiedPerson identifiedPerson = getIdentifiedPerson(jaxb);

        boolean found = false;
        II personIdValue = new II();
        for (IdentifierAttribute personIdentifier : personIdentifierList) {
            if (!found) {
//            if (!found && personIdentifier.isMRN()) {
                String mrn = personIdentifier.getValue();
                personIdValue.setExtension(mrn);
                personIdValue.setRoot(HCIMINPersonRevised.DEFAULT_REGISTRATION_IDENTIFIER_ROOT);
                found = true;
            }
        }

        identifiedPerson.setId(personIdValue);
    }

    private static void setAddress(HCIMINPersonRevised jaxb, RevisePerson request) {
        List<AddressAttribute> addressAttributeList = request.getPerson().getAddress();

        if (addressAttributeList == null || addressAttributeList.isEmpty()) {
            return;
        }

        PRPAMT101002BCIdentifiedPerson identifiedPerson = getIdentifiedPerson(jaxb);
        List<AD> ADList = identifiedPerson.getAddr();

        for (AddressAttribute addressAttribute : addressAttributeList) {
            AD ad = convertAddressAttributeToAD(addressAttribute, true);
            ADList.add(ad);
        }
    }

    private static void setTelecom(HCIMINPersonRevised jaxb, RevisePerson request) {
        PRPAMT101002BCIdentifiedPerson identifiedPerson = getIdentifiedPerson(jaxb);
        List<TEL> TELList = identifiedPerson.getTelecom();
        List<PhoneAttribute> phoneAttributeList = request.getPerson().getPhone();
        
        if (phoneAttributeList == null || phoneAttributeList.isEmpty()) {
            return;
        }

        PhoneAttribute phoneAttribute = phoneAttributeList.get(0);    
        
        if (phoneAttribute != null) {

            if (phoneAttribute.getAreaCode() != null || phoneAttribute.getNumber() != null) {
                TEL tel = convertPhoneAttributeToTEL(phoneAttribute);
                if (tel != null) {
                    TELList.add(tel);
                }
            }
        }
    }

    private static void setIdentifiedPersonEffectiveTime(PRPAMT101002BCIdentifiedPerson identifiedPerson, RevisePerson request) {

        Date eventTime = request.getEventTime();
        if (eventTime != null) {
            TS ts = new TS();
            ts.setValue(DataTypeConverter.convertToTS(eventTime));
            identifiedPerson.setEffectiveDate(ts);
        }
    }

    /**
     * Set values from the entity RP request to JAXB specific structure. Added
     * conditions to set playedOtherIds subelements for the alternate
     * identifiers
     *
     * @param jaxb HCIMINPersonRevised
     * @param request RevisePerson entity business class
     * @throws InstantiationException, IllegalAccessException
     */
    private static void setIdentifiedPerson(HCIMINPersonRevised jaxb, RevisePerson request)
            throws InstantiationException, IllegalAccessException {
        // Initialize identifiedPersonInner
        PRPAMT101002BCIdentifiedPerson identifiedPerson = getIdentifiedPerson(jaxb);
        PRPAMT101002BCPerson identifiedPersonInner = identifiedPerson.getIdentifiedPerson();
        if (identifiedPersonInner == null) {
            identifiedPersonInner = new PRPAMT101002BCPerson();
            identifiedPersonInner.setClassCode(HCIMINPersonRevised.DEFAULT_IDENTIFIED_PERSON_INNER_CLASS_CODE);
            identifiedPersonInner.setDeterminerCode(HCIMINPersonRevised.DEFAULT_IDENTIFIED_PERSON_INNER_DETERMINER_CODE);
            identifiedPerson.setIdentifiedPerson(identifiedPersonInner);
        }
        setID(identifiedPersonInner, request.getPerson());
        setGender(identifiedPersonInner, request.getPerson());
        setBirthDate(identifiedPersonInner, request.getPerson());
        setDeathDate(identifiedPersonInner, request.getPerson());
        setName(identifiedPersonInner, request.getPerson());
        setIdentifiedPersonEffectiveTime(identifiedPerson, request);
        setPersonalRelationships(identifiedPerson, request);
        // set playedOtherIds for the alternate identifiers except BCPHN or MRN or DEFAULT_ID       
        for (IdentifierAttribute identifierId : request.getPerson().getIdentifier()) {

            // map identifier type ids from IdentifierTypes and ignore BCPHN/MRN/DEFAULT_ID 
            if (!identifierId.getType().equals(IdentifierTypes.BCPHN)
                    && !identifierId.getType().equals(IdentifierTypes.MRN)
                    && !identifierId.getType().equals(IdentifierTypes.DEFAULT_ID)) {
//                PRPAMT101002BCOtherIDs playedOtherID = new PRPAMT101002BCOtherIDs();
                PRPAMT101002BCOtherIDs playedOtherID = objectFactory.createPRPAMT101002BCOtherIDs();
                setPlayedOtherIds(identifierId, playedOtherID);
                identifiedPersonInner.getPlayedOtherIDs().add(playedOtherID);
            }

        }
    }

    private static void setGender(PRPAMT101002BCPerson identifiedPersonInner, Person person) {
        GenderAttribute genderAttribute = person.getGender();
        if (genderAttribute == null) {
            return;
        }
        CE ce = convertGenderAttributeToCE(genderAttribute);
        identifiedPersonInner.setAdministrativeGenderCode(ce);
    }

    private static void setBirthDate(PRPAMT101002BCPerson identifiedPersonInner, Person person) {
        DateAttribute birthDateAttribute = person.getBirthDate();
        TS birthDateTS = convertDateAttributeToTSWithDefault(birthDateAttribute);
        identifiedPersonInner.setBirthTime(birthDateTS);
    }

    private static void setDeathDate(PRPAMT101002BCPerson identifiedPersonInner, Person person) {
        DateAttribute deathDateAttribute = person.getDeathDate();
        if (deathDateAttribute == null) {
            return;
        }
        TS deathDateTS = convertDateAttributeToTS(deathDateAttribute);
        identifiedPersonInner.setDeceasedTime(deathDateTS);

        // DO NOT MAP (REQ-0408)
//        BL deceasedIndBL = new BL();
//        deceasedIndBL.setValue(Boolean.TRUE);
//        identifiedPersonInner.setDeceasedInd(deceasedIndBL);
    }

    private static void setName(PRPAMT101002BCPerson identifiedPersonInner, Person person) {
        List<PersonNameAttribute> nameAttributeList = person.getName();

        if (nameAttributeList == null || nameAttributeList.isEmpty()) {
            return;
        }

        List<PN> namePNList = identifiedPersonInner.getName();
        if (namePNList == null) {
            namePNList = new ArrayList<PN>();
            identifiedPersonInner.setName(namePNList);
        }

        for (PersonNameAttribute personNameAttribute : nameAttributeList) {
            PN pn = capitalizeAndConvertNameAttributeToPN(personNameAttribute);
            namePNList.add(pn);
        }
    }

    private static void setID(PRPAMT101002BCPerson identifiedPersonInner, Person person) {
        List<IdentifierAttribute> identifier = person.getIdentifier();

        II idII = new II();
        idII.setRoot(HCIMINPersonRevised.DEFAULT_INNER_PERSON_ID_ROOT);
        idII.setUse(HCIMINPersonRevised.DEFAULT_BUSINESS_USE);

        boolean phnFound = false;

        for (IdentifierAttribute identifierAttribute : identifier) {

            if (identifierAttribute.isBCPHN()) {
                String value = identifierAttribute.getValue();
                if (!StringUtils.isEmpty(value)) {
                    idII.setExtension(value);
                    phnFound = true;
                }
            }
        }

        if (!phnFound) {
            idII.setNullFlavor(NullFlavor.NI);
        }

        identifiedPersonInner.setId(idII);
    }

    private static void setPersonalRelationships(PRPAMT101002BCIdentifiedPerson identifiedPerson, RevisePerson request) {

        if (!request.isAddNewborn()) {
            return;
        }

        List<PersonRelationship> relationshipList = request.getPerson().getRelationship();
        if (relationshipList != null && !relationshipList.isEmpty()) {

            PersonRelationship relationship = relationshipList.get(0);

            JAXBElement<PRPAMT101002BCPersonalRelationship> personalRelationshipElement = identifiedPerson.getPersonalRelationship();
            PRPAMT101002BCPersonalRelationship personalRelationship = null;

            personalRelationship = new PRPAMT101002BCPersonalRelationship();
            CV cv = new CV();
            cv.setCode(HCIMINPersonRevised.DEFAULT_RELATIONSHIP_CODE_NEW_MOTHER);
            personalRelationship.setCode(cv);
            COCTMT030200BCPerson relationshipHolder = new COCTMT030200BCPerson();
            II relationshipHolderId = new II();
            String oid = IdentifierTypes.fromTypeToOID(relationship.getRelationshipHolder().getIdentifier().get(0).getType());
            relationshipHolderId.setRoot(oid);
            relationshipHolderId.setExtension(relationship.getRelationshipHolder().getIdentifier().get(0).getValue());
            relationshipHolder.setId(relationshipHolderId);
            personalRelationship.setRelationshipHolder(relationshipHolder);

            if (personalRelationshipElement == null) {
                personalRelationshipElement = objectFactory.createPRPAMT101002BCIdentifiedPersonPersonalRelationship(personalRelationship);
                identifiedPerson.setPersonalRelationship(personalRelationshipElement);
            } else {
                personalRelationshipElement.setValue(personalRelationship);
            }
        }
    }

    /**
     * Set individual identifier type codes and OIDs in JAXB specific structure.
     *
     *
     * @param otherIdentifier IdentifierAttribute
     * @param playedOtherID PRPAMT101002BCOtherIDs JAXB specific structure
     * @throws InstantiationException, IllegalAccessException
     */
    private static void setPlayedOtherIds(IdentifierAttribute otherIdentifier,
            PRPAMT101002BCOtherIDs playedOtherID) throws InstantiationException,
            IllegalAccessException {

        II idII = new II();
        // set otherId subelement attributes
        idII.setExtension(otherIdentifier.getValue());
        idII.setAssigningAuthorityName(otherIdentifier.getSource());
        switch (otherIdentifier.getType()) {
            case ABPHN:
                idII.setRoot(HealthAuthorityConstants.ABPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case PEPHN:
                idII.setRoot(HealthAuthorityConstants.PEPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case QCPHN:
                idII.setRoot(HealthAuthorityConstants.QCPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case SKPHN:
                idII.setRoot(HealthAuthorityConstants.SKPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case YTPHN:
                idII.setRoot(HealthAuthorityConstants.YTPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case AHULI:
                idII.setRoot(HealthAuthorityConstants.AHULI_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case CACF:
                idII.setRoot(HealthAuthorityConstants.CACF_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case RCMP:
                idII.setRoot(HealthAuthorityConstants.RCMP_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case VAC:
                idII.setRoot(HealthAuthorityConstants.VAC_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case MBPHN:
                idII.setRoot(HealthAuthorityConstants.MBPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case NBPHN:
                idII.setRoot(HealthAuthorityConstants.NBPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case NFPHN:
                idII.setRoot(HealthAuthorityConstants.NFPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case NSPHN:
                idII.setRoot(HealthAuthorityConstants.NSPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case NTPHN:
                idII.setRoot(HealthAuthorityConstants.NTPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case NUPHN:
                idII.setRoot(HealthAuthorityConstants.NUPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            case ONPHN:
                idII.setRoot(HealthAuthorityConstants.ONPHN_REGISTRATION_IDENTIFIER_ROOT);
                break;
            default:
                break;
        }
        playedOtherID.getId().add(idII);
    }
}
