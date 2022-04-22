/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.parent;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import ca.bc.gov.moh.rtrans.entity.CommunicationFunction;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.DateAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.PhoneAttribute;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import ca.bc.gov.moh.rtrans.entity.User;
import ca.bc.gov.moh.rtrans.entity.transaction.EventMessage;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeValidator;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HealthAuthorityConstants;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hl7.v3.AD;
import org.hl7.v3.AdxpCity;
import org.hl7.v3.AdxpCountry;
import org.hl7.v3.AdxpPostalCode;
import org.hl7.v3.AdxpState;
import org.hl7.v3.AdxpStreetAddressLine;
import org.hl7.v3.EnFamily;
import org.hl7.v3.EnGiven;
import org.hl7.v3.MCCIMT000100BCDevice;
import org.hl7.v3.ObjectFactory;
import org.hl7.v3.PN;
import org.hl7.v3.TEL;
import org.hl7.v3.TS;
import org.hl7.v3.api.JaxbRequestControlActProcessWithEffectiveTime;
import org.hl7.v3.api.RequestJaxbMessage;
import org.joda.time.DateTime;

/**
 *
 * @author conrad.gustafson
 */
public class EntityToJaxbTypeConverter {

    protected static final ObjectFactory objectFactory = new ObjectFactory();

    protected static void convertCommonFields(RequestJaxbMessage jaxb, RequestMessage request) {

        // set ID
        jaxb.getId().setExtension(request.getMessageId());

        // set creation time
        final TS creationTime = new TS();
        creationTime.setValue(DataTypeConverter.convertToTS(request.getCreationTime()));
        jaxb.setCreationTime(creationTime);

        // set receiver information
        List<CommunicationFunction> receiver = request.getReceiver();
        if (receiver != null && !receiver.isEmpty()) {
            CommunicationFunction receiverCommunicationFunction = receiver.get(0);

            String receiverSystemNameValue = receiverCommunicationFunction.getSystemName();
            final MCCIMT000100BCDevice receiverDevice = jaxb.getReceiver().getDevice();
            receiverDevice.getId().setExtension(receiverSystemNameValue);

            String receiverOrganizationValue = receiverCommunicationFunction.getOrganization();
            receiverDevice.getAsAgent().getValue().getRepresentedOrganization().getValue().getId().setExtension(receiverOrganizationValue);
        }

        // set sender information
        CommunicationFunction senderCommunicationFunction = request.getSender();
        String senderSystemNameValue = senderCommunicationFunction.getSystemName();
        final MCCIMT000100BCDevice senderDevice = jaxb.getSender().getDevice();
        senderDevice.getId().setExtension(senderSystemNameValue);
        String senderOrganizationValue = senderCommunicationFunction.getOrganization();
        senderDevice.getAsAgent().getValue().getRepresentedOrganization().getValue().getId().setExtension(senderOrganizationValue);

        // set data enterer
        String dataEntererID = null;
        if (request.getAuthor() != null) {
            User dataEnterer = request.getAuthor().getUser();
            if (dataEnterer != null && dataEnterer.getUserId() != null) {              
                dataEntererID = dataEnterer.getUserId();
            }
        }
        StringBuilder dataEntererIdentifierBuilder = new StringBuilder("");
        if (!StringUtils.isEmpty(dataEntererID)) {
            dataEntererIdentifierBuilder.append(dataEntererID);
        }

        if (StringUtils.isEmpty(dataEntererID)) {
            dataEntererIdentifierBuilder.append("");
        }

        jaxb.getControlActProcess().getDataEnterer().getAssignedPerson().getId().setExtension(dataEntererIdentifierBuilder.toString());


        // set event time
        if (jaxb.getControlActProcess() instanceof JaxbRequestControlActProcessWithEffectiveTime
                && request instanceof EventMessage) {
            Date timeToLive = DateUtils.addDays(new Date(System.currentTimeMillis()), 1);
            TS effectiveTimeTS = new TS();
            effectiveTimeTS.setValue(DataTypeConverter.convertToTS(timeToLive));
            JaxbRequestControlActProcessWithEffectiveTime capWithEffectiveTime = (JaxbRequestControlActProcessWithEffectiveTime) jaxb.getControlActProcess();
            capWithEffectiveTime.setEffectiveTime(effectiveTimeTS);
        }
    }

    protected static AD convertAddressAttributeToAD(AddressAttribute addressAttribute) {
        return convertAddressAttributeToAD(addressAttribute, false);
    }

    protected static AD convertAddressAttributeToAD(AddressAttribute addressAttribute, boolean checkForHomelessPostalCode) {
        AD addressValue = new AD();

        AddressTypes type = addressAttribute.getType();
        String city = addressAttribute.getCity();
        String province = addressAttribute.getProvince();
        String country = addressAttribute.getCountry();
        String postalCode = addressAttribute.getPostalCode();
        List<String> streetAddressLines = addressAttribute.getStreetAddressLines();

        
        List<Serializable> addressContentList = addressValue.getContent();
        boolean addressNotEmpty = DataTypeValidator.addressAttributeNotEmpty(addressAttribute);
        for (String streetAddressLine : streetAddressLines) {
            if (!StringUtils.isEmpty(streetAddressLine)) {
                AdxpStreetAddressLine streetLine = objectFactory.createAdxpStreetAddressLine();
                streetLine.setText(streetAddressLine);
                JAXBElement<AdxpStreetAddressLine> streetLineElement = objectFactory.createADStreetAddressLine(streetLine);
                addressContentList.add(streetLineElement);
            }
        }
        
        final String addressUse = DataTypeConverter.convertAddressTypeValue(type);
        if (!StringUtils.isEmpty(addressUse)) {
            addressValue.getUse().add(addressUse);
        } else if(addressNotEmpty){
            // if they haven't specified an address use, search for all address uses
            // add address use only if there is a street address
            addressValue.getUse().add(DataTypeConverter.ADDRESS_TYPE_PHYSICAL);
            addressValue.getUse().add(DataTypeConverter.ADDRESS_TYPE_MAILING);
        }

        if (!StringUtils.isEmpty(city)) {
            AdxpCity cityLine = objectFactory.createAdxpCity();
            cityLine.setText(city);
            JAXBElement<AdxpCity> cityElement = objectFactory.createADCity(cityLine);
            addressContentList.add(cityElement);
        }

        if (!StringUtils.isEmpty(province)) {
            AdxpState stateLine = objectFactory.createAdxpState();
            stateLine.setText(province);
            JAXBElement<AdxpState> stateElement = objectFactory.createADState(stateLine);
            addressContentList.add(stateElement);
        }

        if (!StringUtils.isEmpty(country)) {
            AdxpCountry countryLine = objectFactory.createAdxpCountry();
            countryLine.setText(country);
            JAXBElement<AdxpCountry> countryElement = objectFactory.createADCountry(countryLine);
            addressContentList.add(countryElement);
        }

        if (!StringUtils.isEmpty(postalCode)) {
            AdxpPostalCode postalCodeLine = objectFactory.createAdxpPostalCode();
            // REQ-0257 Homeless Postal Code Translation
            if (checkForHomelessPostalCode && postalCode.equalsIgnoreCase(HealthAuthorityConstants.HOMELESS_POSTAL_CODE)
                    || postalCode.equalsIgnoreCase(StringUtils.deleteWhitespace(HealthAuthorityConstants.HOMELESS_POSTAL_CODE))) {
                postalCodeLine.setText("");
            } else {
                // Thought this was a requirement but it doesn't seem to be
//                postalCodeLine.setText(StringUtils.remove(postalCode, " "));
                postalCodeLine.setText(postalCode);
            }
            JAXBElement<AdxpPostalCode> postalCodeElement = objectFactory.createADPostalCode(postalCodeLine);
            addressContentList.add(postalCodeElement);
        }

        return addressValue;
    }

    protected static TEL convertPhoneAttributeToTEL(PhoneAttribute phoneAttribute) {
        TEL tel = new TEL();

        //Check that one of these isn't null so we don't put 'null' into the area code
        String areaCode = phoneAttribute.getAreaCode() == null ? "" : phoneAttribute.getAreaCode();
        String phoneNumber = phoneAttribute.getNumber() == null ? "" : phoneAttribute.getNumber();

        CommunicationTypes type = phoneAttribute.getType();

        tel.getUse().add(DataTypeConverter.convertCommunicationTypeToV3(type));

        tel.setValue("tel:" + areaCode + phoneNumber);

        return tel;
    }

    public static TS convertDateAttributeToTS(DateAttribute birthDateAttribute) {
        if (birthDateAttribute == null) {
            return null;
        }
        Date date = birthDateAttribute.getValue();
        String dateString = DataTypeConverter.convertToDateTS(date);
        if (dateString == null) {
            return null;
        }
        TS ts = new TS();
        ts.setValue(dateString);
        return ts;
    }

    public static PN convertNameAttributeToPN(PersonNameAttribute nameAttribute) {
        PN pn = new PN();
        List<Serializable> nameContent = pn.getContent();

        // There is only one name on a person in FindCandidates
        String firstName = StringUtils.capitalize(nameAttribute.getFirstName());
        String middleName = StringUtils.capitalize(nameAttribute.getMiddleName());
        String lastName = StringUtils.capitalize(nameAttribute.getLastName());
        String preferredGivenName = StringUtils.capitalize(nameAttribute.getPreferredGivenName());

        String nameUse = DataTypeConverter.convertPersonNameTypeValue((nameAttribute.getType()));
        if (!StringUtils.isEmpty(nameUse)) {
            pn.getUse().add(nameUse);
        } else {
            pn.getUse().add(DataTypeConverter.PERSON_NAME_TYPE_LEGAL_DECLARED);
        }

        if (!StringUtils.isEmpty(lastName)) {
            EnFamily familyNameElement = new EnFamily();
            familyNameElement.setText(lastName);
            nameContent.add(objectFactory.createENFamily(familyNameElement));
        }
        if (!StringUtils.isEmpty(firstName)) {
            EnGiven firstNameElement = new EnGiven();
            firstNameElement.setText(firstName);
            nameContent.add(objectFactory.createENGiven(firstNameElement));
        }
        if (!StringUtils.isEmpty(middleName)) {
            EnGiven middleNameElement = new EnGiven();
            middleNameElement.setText(middleName);
            nameContent.add(objectFactory.createENGiven(middleNameElement));
        }
        if (!StringUtils.isEmpty(preferredGivenName)) {
            EnGiven preferredGivenNameElement = new EnGiven();
            preferredGivenNameElement.setText(preferredGivenName);
            preferredGivenNameElement.getQualifier().add(PREFERRED_NAME_QUALIFIER);
            final JAXBElement<EnGiven> ENGiven = objectFactory.createENGiven(preferredGivenNameElement);
            nameContent.add(ENGiven);
        }

        return pn;
    }
    private static final String PREFERRED_NAME_QUALIFIER = "CL";

    /**
    * Side Effects Warning, this method will modify the name attribute passed in.
     * @param nameAttribute
     * @return 
    */
    public static PN capitalizeAndConvertNameAttributeToPN(PersonNameAttribute nameAttribute) {
        String firstName = nameAttribute.getFirstName();
        String middleName = nameAttribute.getMiddleName();
        String lastName = nameAttribute.getLastName();

        if (!StringUtils.isEmpty(lastName)) {
            nameAttribute.setLastName(StringUtils.capitalize(lastName));
        }
        if (!StringUtils.isEmpty(firstName)) {
            nameAttribute.setFirstName(StringUtils.capitalize(firstName));
        }
        if (!StringUtils.isEmpty(middleName)) {
            nameAttribute.setMiddleName(StringUtils.capitalize(middleName));
        }
        return convertNameAttributeToPN(nameAttribute);
    }

    public static TS convertDateAttributeToTSWithDefault(DateAttribute birthDateAttribute) {

        if (birthDateAttribute == null || birthDateAttribute.getValue() == null) {
            return convertDateAttributeToTS(birthDateAttribute);
        }

        DateTime birthDateTime = new DateTime(birthDateAttribute.getValue());
        if (birthDateTime.getYear() == 1900 && birthDateTime.getMonthOfYear() == 1 && birthDateTime.getDayOfMonth() == 1) {
            return convertDateAttributeToTS(DEFAULT_BIRTH_DATE);
        } else {
            return convertDateAttributeToTS(birthDateAttribute);
        }

    }

    private static DateAttribute DEFAULT_BIRTH_DATE = new DateAttribute(new DateTime(1800, 1, 1, 0, 0).toDate());

}
