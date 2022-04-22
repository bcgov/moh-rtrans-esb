/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.parent;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import ca.bc.gov.moh.rtrans.entity.AttributeStatus;
import ca.bc.gov.moh.rtrans.entity.BooleanAttribute;
import ca.bc.gov.moh.rtrans.entity.CommunicationFunction;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.DateAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderAttribute;
import ca.bc.gov.moh.rtrans.entity.GenderValues;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonNameTypes;
import ca.bc.gov.moh.rtrans.entity.PhoneAttribute;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import ca.bc.gov.moh.rtrans.entity.ResponseMessage;
import ca.bc.gov.moh.rtrans.entity.SystemResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HL7v3GenderValues;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics.GetDemographicsJaxbToEntityTypeConverter;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.hl7.v3.AD;
import org.hl7.v3.ANY;
import org.hl7.v3.AdxpCity;
import org.hl7.v3.AdxpCountry;
import org.hl7.v3.AdxpPostalCode;
import org.hl7.v3.AdxpState;
import org.hl7.v3.AdxpStreetAddressLine;
import org.hl7.v3.BL;
import org.hl7.v3.CE;
import org.hl7.v3.EnDelimiter;
import org.hl7.v3.EnFamily;
import org.hl7.v3.EnGiven;
import org.hl7.v3.EnPrefix;
import org.hl7.v3.EnSuffix;
import org.hl7.v3.II;
import org.hl7.v3.MCCIMT000100BCReceiver;
import org.hl7.v3.MCCIMT000300BCReceiver;
import org.hl7.v3.NullFlavor;
import org.hl7.v3.PN;
import org.hl7.v3.TEL;
import org.hl7.v3.TS;
import org.hl7.v3.api.JaxbIdentifiedPerson;
import org.hl7.v3.api.JaxbTarget;
import org.hl7.v3.api.RequestJaxbMessageDistribution;
import org.hl7.v3.api.ResponseJaxbMessage;
import org.slf4j.LoggerFactory;

/**
 *
 * @author conrad.gustafson
 */
public abstract class JaxbToEntityTypeConverter {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JaxbToEntityTypeConverter.class);

    protected static void convertCommonFields(ResponseMessage response, ResponseJaxbMessage jaxb) {
        setMessageID(response, jaxb);
        setCreationTime(response, jaxb);
        setAcceptAckCode(response, jaxb);
        setSender(response, jaxb);
        setReceiver(response, jaxb);
        setSystemResponses(response, jaxb);
    }

    protected static void convertCommonFieldsRPDistribution(RequestMessage hcimRequest, RequestJaxbMessageDistribution jaxb) {
        setMessageIDForDistribution(hcimRequest, jaxb);
        setInteractionIDForDistribution(hcimRequest, jaxb);
        setCreationTimeForDistribution(hcimRequest, jaxb);
        setSenderForDistribution(hcimRequest, jaxb);
        setReceiverForDistribution(hcimRequest, jaxb);
    }

    private static void setMessageID(ResponseMessage response, ResponseJaxbMessage jaxb) {
        response.setMessageId(jaxb.getId().getExtension());
    }

    private static void setCreationTime(ResponseMessage response, ResponseJaxbMessage jaxb) {
        response.setCreationTime(DataTypeConverter.convertToDate(jaxb.getCreationTime()));
    }

    /**
     * Set the acceptAckCode in response entity from HL7V3 response message.
     *
     * @param response
     * @param jaxb
     */
    private static void setAcceptAckCode(ResponseMessage response, ResponseJaxbMessage jaxb) {
        response.setAcceptAckCode(jaxb.getAcceptAckCode().getCode());
    }

    private static void setSender(ResponseMessage response, ResponseJaxbMessage jaxb) {
        String senderSystemName = null;
        String senderOrganization = null;
        try {
            senderSystemName = jaxb.getSender().getDevice().getId().getExtension();
        } catch (NullPointerException npe) {
            // not a problem
        }
        try {
            senderOrganization = jaxb.getSender().getDevice().getAsAgent().getValue().getRepresentedOrganization().getValue().getId().getExtension();
        } catch (NullPointerException npe) {
            // not a problem
        }
        if (senderSystemName != null || senderOrganization != null) {
            final CommunicationFunction sender = new CommunicationFunction();
            sender.setSystemName(senderSystemName);
            sender.setOrganization(senderOrganization);
            response.setSender(sender);
        }
    }

    private static void setReceiver(ResponseMessage response, ResponseJaxbMessage jaxb) {
        MCCIMT000300BCReceiver receiver = jaxb.getReceiver();
        if (receiver != null) {
            ArrayList<CommunicationFunction> arrayList = new ArrayList<>();
            final CommunicationFunction communicationFunction = new CommunicationFunction();
            arrayList.add(communicationFunction);
            response.setReceiver(arrayList);

            String receiverSystemName = null;
            String receiverOrganization = null;
            try {
                receiverSystemName = jaxb.getReceiver().getDevice().getId().getExtension();
            } catch (NullPointerException npe) {
                // not a problem
            }
            try {
                receiverOrganization = jaxb.getReceiver().getDevice().getAsAgent().getValue().getRepresentedOrganization().getValue().getId().getExtension();
            } catch (NullPointerException npe) {
                // not a problem
            }
            if (receiverSystemName != null || receiverOrganization != null) {
                communicationFunction.setSystemName(receiverSystemName);
                communicationFunction.setOrganization(receiverOrganization);
            }
        }
    }

    private static void setSystemResponses(ResponseMessage response, ResponseJaxbMessage jaxb) {

        List<SystemResponse> systemResponses = jaxb.getSystemResponses();
        if (systemResponses != null && !systemResponses.isEmpty()) {
            response.setSystemResponse(systemResponses);
        }
    }

    private static void setMessageIDForDistribution(RequestMessage hcimRequest, RequestJaxbMessageDistribution jaxb) {
        hcimRequest.setMessageId(jaxb.getId().getExtension());
    }

    private static void setInteractionIDForDistribution(RequestMessage hcimRequest, RequestJaxbMessageDistribution jaxb) {
        hcimRequest.setInteractionId(jaxb.getInteractionId().getExtension());
    }

    private static void setCreationTimeForDistribution(RequestMessage hcimRequest, RequestJaxbMessageDistribution jaxb) {
        hcimRequest.setCreationTime(DataTypeConverter.convertToDate(jaxb.getCreationTime()));
    }

    private static void setSenderForDistribution(RequestMessage hcimRequest, RequestJaxbMessageDistribution jaxb) {
        String senderSystemName = null;
        String senderOrganization = null;
        try {
            senderSystemName = jaxb.getSender().getDevice().getId().getExtension();
        } catch (NullPointerException npe) {
        }
        try {
            senderOrganization = jaxb.getSender().getDevice().getAsAgent().getValue().getRepresentedOrganization().getValue().getId().getExtension();
        } catch (NullPointerException npe) {
        }
        if (senderSystemName != null || senderOrganization != null) {
            final CommunicationFunction sender = new CommunicationFunction();
            sender.setSystemName(senderSystemName);
            sender.setOrganization(senderOrganization);
            hcimRequest.setSender(sender);
        }
    }

    private static void setReceiverForDistribution(RequestMessage hcimRequest, RequestJaxbMessageDistribution jaxb) {
        MCCIMT000100BCReceiver receiver = jaxb.getReceiverDistribution();
        if (receiver != null) {
            ArrayList<CommunicationFunction> arrayList = new ArrayList<>();
            final CommunicationFunction communicationFunction = new CommunicationFunction();
            arrayList.add(communicationFunction);
            hcimRequest.setReceiver(arrayList);

            String receiverSystemName = null;
            String receiverOrganization = null;
            try {
                receiverSystemName = jaxb.getReceiverDistribution().getDevice().getId().getExtension();
            } catch (NullPointerException npe) {
            }
            try {
                receiverOrganization = jaxb.getReceiverDistribution().getDevice().getAsAgent().getValue().getRepresentedOrganization().getValue().getId().getExtension();
            } catch (NullPointerException npe) {
            }
            if (receiverSystemName != null || receiverOrganization != null) {
                communicationFunction.setSystemName(receiverSystemName);
                communicationFunction.setOrganization(receiverOrganization);
            }
        }
    }

    protected static void convertPerson(Person person, JaxbTarget target) {

        JaxbIdentifiedPerson identifiedPerson = target.getIdentifiedPerson();

        setGender(identifiedPerson, person);
        setBirthDate(identifiedPerson, person);
        setDeathDate(identifiedPerson, person);
        setDeceasedInd(identifiedPerson, person);
        setIdentifier(target, person);
        setNames(identifiedPerson, person);
        setTelecom(target, person);
        setAddresses(target, person);
    }

    public static void setGender(JaxbIdentifiedPerson identifiedPerson, Person person) {
        GenderAttribute genderAttribute = convertHL7Gender(identifiedPerson.getAdministrativeGenderCode());
        person.setGender(genderAttribute);
    }

    public static GenderAttribute convertHL7Gender(CE administrativeGenderCode) {
        if (administrativeGenderCode == null) {
            return null;
        }

        String genderCode = administrativeGenderCode.getCode();
        if (genderCode != null) {
            if (genderCode.equals(HL7v3GenderValues.GENDER_MALE)) {
                return new GenderAttribute(GenderValues.Male);
            } else if (genderCode.equals(HL7v3GenderValues.GENDER_FEMALE)) {
                return new GenderAttribute(GenderValues.Female);
            } else if (genderCode.equals(HL7v3GenderValues.GENDER_UN)) {
                return new GenderAttribute(GenderValues.NotUniquelyIdentified);
            }
        }

        if (administrativeGenderCode.getNullFlavor() == NullFlavor.UNK) {
            return new GenderAttribute(GenderValues.Unknown);
        } else if (administrativeGenderCode.getNullFlavor() == NullFlavor.MSK) {
            GenderAttribute genderAttribute = new GenderAttribute();
            genderAttribute.setMasked(Boolean.TRUE);
            return genderAttribute;
        }

        return null;
    }

    public static void setBirthDate(JaxbIdentifiedPerson identifiedPerson, Person person) {
        if (identifiedPerson.getBirthTime() == null) {
            return;
        }
        TS birthTime = identifiedPerson.getBirthTime();

        if (NullFlavor.MSK.equals(birthTime.getNullFlavor())) {
            DateAttribute dateAttribute = new DateAttribute();
            dateAttribute.setMasked(Boolean.TRUE);
            person.setBirthDate(dateAttribute);
        } else {
            Date convertedDate = DataTypeConverter.convertToDate(birthTime);
            person.setBirthDate(new DateAttribute(convertedDate));
        }
    }

    public static void setDeathDate(JaxbIdentifiedPerson identifiedPerson, Person person) {

        if (identifiedPerson.getDeceasedTime() == null) {
            return;
        }
        TS deceasedTime = identifiedPerson.getDeceasedTime();

        if (NullFlavor.MSK.equals(deceasedTime.getNullFlavor())) {
            DateAttribute dateAttribute = new DateAttribute();
            dateAttribute.setMasked(Boolean.TRUE);
            person.setDeathDate(dateAttribute);
        } else {
            Date convertedDate = DataTypeConverter.convertToDate(deceasedTime);
            person.setDeathDate(new DateAttribute(convertedDate));
        }

    }

    public static void setDeceasedInd(JaxbIdentifiedPerson identifiedPerson, Person person) {
        final BL deceasedInd = identifiedPerson.getDeceasedInd();
        if (deceasedInd == null) {
            return;
        }
        if (NullFlavor.MSK.equals(deceasedInd.getNullFlavor())) {
            BooleanAttribute booleanAttribute = new BooleanAttribute();
            booleanAttribute.setMasked(Boolean.TRUE);
        } else {
            Boolean value = identifiedPerson.getDeceasedInd().isValue();
            person.setDeathVerified(new BooleanAttribute(value));
        }
    }

    private static void setIdentifier(JaxbTarget target, Person person) {

        List<IdentifierAttribute> identifierList = person.getIdentifier();
        if (person.getIdentifier() == null || person.getIdentifier().isEmpty()) {
            identifierList = new ArrayList<>();
        }

        // extract MRN identifiers from JAXB object
        List<II> idTargetList = target.getId();

        for (II id : idTargetList) {
            String assigningAuthorityName = id.getAssigningAuthorityName();
            String extension = id.getExtension();
            IdentifierTypes type = IdentifierTypes.fromOIDtoType(id.getRoot());
            if (type != null) {
                IdentifierAttribute identifierAttribute = new IdentifierAttribute();

                if (NullFlavor.MSK.equals(id.getNullFlavor())) {
                    identifierAttribute.setMasked(Boolean.TRUE);
                } else {
                    identifierAttribute.setType(type);
                    identifierAttribute.setValue(extension);
                    identifierAttribute.setSource(assigningAuthorityName);
                    if (id.isDisplayable() != null) {
                        identifierAttribute.setStatus(id.isDisplayable() ? AttributeStatus.Active : AttributeStatus.Merged);
                    } else {
                        identifierAttribute.setStatus(AttributeStatus.Active);
                    }
                }
                identifierList.add(identifierAttribute);
            }
        }

        // extract PHN identifiers from JAXB object
        List<II> idList = target.getIdentifiedPerson().getId();

        for (II id : idList) {
            String assigningAuthorityName = id.getAssigningAuthorityName();
            String extension = id.getExtension();
            IdentifierTypes type = IdentifierTypes.fromOIDtoType(id.getRoot());
            if (type == null) {
                System.out.println("No OID mapping for (2):" + id.getRoot());
            }
            IdentifierAttribute identifierAttribute = new IdentifierAttribute();

            if (NullFlavor.MSK.equals(id.getNullFlavor())) {
                identifierAttribute.setMasked(Boolean.TRUE);
            } else {
                identifierAttribute.setType(type);
                identifierAttribute.setValue(extension);
                identifierAttribute.setSource(assigningAuthorityName);
                if (id.isDisplayable() != null) {
                    identifierAttribute.setStatus(id.isDisplayable() ? AttributeStatus.Active : AttributeStatus.Merged);
                } else {
                    identifierAttribute.setStatus(AttributeStatus.Active);
                }
            }
            identifierList.add(identifierAttribute);
        }
        person.setIdentifier(identifierList);
    }

    private static void setNames(JaxbIdentifiedPerson identifiedPerson, Person person) {
        List<PN> nameList = identifiedPerson.getName();

        if (nameList == null || nameList.isEmpty()) {
            return;
        }

        ArrayList<PersonNameAttribute> nameAttributeList = new ArrayList<>();
        person.setName(nameAttributeList);

        for (PN pn : nameList) {
            nameAttributeList.add(convertToNameAttribute(pn));
        }

        //Defect 37 - Only take one name (documented first, then declared if documented not present)
        PersonNameAttribute toReturn = null;
        for (PersonNameAttribute name : nameAttributeList) {
            if (name.getType() == PersonNameTypes.Documented) {
                toReturn = name;
                break;
            }
        }
        if (toReturn == null) {
            for (PersonNameAttribute name : nameAttributeList) {
                if (name.getType() == PersonNameTypes.Declared) {
                    toReturn = name;
                    break;
                }
            }
        }
        if (toReturn != null) {
            for (PersonNameAttribute name : nameAttributeList) {
                if (name.getPreferredGivenName() != null && !"".equals(name.getPreferredGivenName())
                        && (toReturn.getPreferredGivenName() == null || "".equals(toReturn.getPreferredGivenName()))) {
                    toReturn.setPreferredGivenName(name.getPreferredGivenName());
                }
            }
            nameAttributeList.clear();
            nameAttributeList.add(toReturn);
        }
    }

    private static PersonNameAttribute convertToNameAttribute(PN pn) {
        PersonNameAttribute personNameAttribute = new PersonNameAttribute();

        List<Serializable> content = pn.getContent();

        if (NullFlavor.MSK.equals(pn.getNullFlavor())) {
            personNameAttribute.setMasked(Boolean.TRUE);
        } else {
            for (Serializable serializable : content) {
                if (serializable instanceof JAXBElement) {
                    convertNameAttributeContent(personNameAttribute, (JAXBElement) serializable);
                }
            }
        }

        List<String> use = pn.getUse();
        if (use != null && !use.isEmpty()) {
            personNameAttribute.setType(PersonNameTypes.fromValue(use.get(0)));
        }

        return personNameAttribute;
    }

    private static void convertNameAttributeContent(PersonNameAttribute personNameAttribute, JAXBElement<?> jaxbElement) {
        Class<?> declaredType = jaxbElement.getDeclaredType();
        ANY value = (ANY) jaxbElement.getValue();

        if (EnFamily.class
                .equals(declaredType)) {
            personNameAttribute.setLastName(value.getText());
        } else if (EnGiven.class.equals(declaredType)) {
            if (((EnGiven) value).getQualifier().contains("CL")) {
                personNameAttribute.setPreferredGivenName(value.getText());
            } else if (StringUtils.isEmpty(personNameAttribute.getFirstName())) {
                personNameAttribute.setFirstName(value.getText());
            } else if (StringUtils.isEmpty(personNameAttribute.getMiddleName())) {
                personNameAttribute.setMiddleName(value.getText());
            } else if (StringUtils.isEmpty(personNameAttribute.getTitle())) {
                personNameAttribute.setTitle(value.getText());
            }
        } else if (EnDelimiter.class
                .equals(declaredType)) {
            personNameAttribute.setLastName(value.getText());
        } else if (EnSuffix.class
                .equals(declaredType)) {
            personNameAttribute.setLastName(value.getText());
        } else if (EnPrefix.class
                .equals(declaredType)) {
            personNameAttribute.setLastName(value.getText());
        }

    }

    private static void setTelecom(JaxbTarget target, Person person) {

        //TODO: Support country and extension
        List<TEL> telecomList = target.getTelecom();
        if (telecomList == null || telecomList.isEmpty()) {
            return;
        }

        List<PhoneAttribute> phoneAttributeList = new ArrayList<>();

        for (TEL tel : telecomList) {
            String value = tel.getValue();
            if(StringUtils.isNotBlank(value)){
                List<String> use = tel.getUse();

                if (NullFlavor.MSK.equals(tel.getNullFlavor())) {
                    PhoneAttribute phoneAttribute = new PhoneAttribute();
                    if (use != null && !use.isEmpty()) {
                        phoneAttribute.setType(CommunicationTypes.fromValue(use.get(0)));
                    }
                    phoneAttribute.setMasked(Boolean.TRUE);
                    phoneAttributeList.add(phoneAttribute);
                } else {
                    try {
                        URI telecom = new URI(value);
                        String scheme = telecom.getScheme();

                        if ("tel".equals(scheme)) {
                            PhoneAttribute phoneAttribute = new PhoneAttribute();
                            if (use != null && !use.isEmpty()) {
                                phoneAttribute.setType(CommunicationTypes.fromValue(use.get(0)));
                            }

                            String phoneNumber = telecom.getSchemeSpecificPart();
                            if (phoneNumber != null) {
                                phoneAttribute.setRawValue(phoneNumber);

                                phoneNumber = StringUtils.remove(phoneNumber, " ");
                                phoneNumber = StringUtils.remove(phoneNumber, "-");

                                if (phoneNumber.length() >= 10) {
                                    phoneAttribute.setAreaCode(phoneNumber.substring(0, 3));   
                                    phoneAttribute.setNumber(phoneNumber.substring(3, 10));
                                } else if (phoneNumber.length() >= 3) {
                                    phoneAttribute.setAreaCode(phoneNumber.substring(0, 3));
                                    phoneAttribute.setNumber(phoneNumber.substring(3));
                                } else {
                                    phoneAttribute.setAreaCode(phoneNumber);
                                }
                                phoneAttributeList.add(phoneAttribute);
                            }                
                        } 
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(GetDemographicsJaxbToEntityTypeConverter.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
        person.setPhone(phoneAttributeList);
    }

    private static void setAddresses(JaxbTarget target, Person person) {
        List<AD> addressList = target.getAddr();

        if (addressList == null || addressList.isEmpty()) {
            return;
        }

        List<AddressAttribute> addressAttributes = person.getAddress();

        for (AD ad : addressList) {

            AddressAttribute addressAttribute = new AddressAttribute();

            List<String> uses = ad.getUse();
            if (uses != null && !uses.isEmpty()) {
                AddressTypes addressType = AddressTypes.fromValue(uses);
                addressAttribute.setType(addressType);
            }

            Transformer uppercaser = new Transformer() {

                @Override
                public Object transform(Object o) {
                    return ((String) o).toUpperCase();
                }
            };
            Collection<String> uppercaseUses = CollectionUtils.collect(uses, uppercaser);
            addressAttribute.setIsVerified(uppercaseUses.contains("VER"));

            if (NullFlavor.MSK.equals(ad.getNullFlavor())) {
                addressAttribute.setMasked(Boolean.TRUE);
            } else {
                List<Serializable> content = ad.getContent();
                for (Serializable serializable : content) {
                    if (serializable instanceof JAXBElement) {
                        convertAddressAttributeContent(addressAttribute, (JAXBElement) serializable);
                    }
                }
            }

            addressAttributes.add(addressAttribute);
        }
        person.setAddress(addressAttributes);
    }

    private static void convertAddressAttributeContent(AddressAttribute addressAttribute, JAXBElement<?> jaxbElement) {
        Class<?> declaredType = jaxbElement.getDeclaredType();
        ANY value = (ANY) jaxbElement.getValue();

        final String text = value.getText();

        if (AdxpCity.class
                .equals(declaredType)) {
            addressAttribute.setCity(text);
        } else if (AdxpState.class
                .equals(declaredType)) {
            addressAttribute.setProvince(text);
        } else if (AdxpCountry.class
                .equals(declaredType)) {
            addressAttribute.setCountry(text);
        } else if (AdxpPostalCode.class
                .equals(declaredType)) {
            addressAttribute.setPostalCode(text);
        } else if (AdxpStreetAddressLine.class
                .equals(declaredType)) {
            List<String> streetAddressLines = addressAttribute.getStreetAddressLines();
            if (streetAddressLines
                    == null) {
                streetAddressLines = new ArrayList<>();
                addressAttribute.setStreetAddressLines(streetAddressLines);
            }

            streetAddressLines.add(text);
        } else {
            logger.warn("Unsupported address element: " + declaredType.getCanonicalName());
        }
    }

}
