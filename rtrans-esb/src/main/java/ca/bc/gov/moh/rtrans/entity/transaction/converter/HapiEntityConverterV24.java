/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import ca.bc.gov.moh.rtrans.entity.Author;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.DateAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.IntegerAttribute;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.PersonNameTypes;
import ca.bc.gov.moh.rtrans.entity.PhoneAttribute;
import ca.bc.gov.moh.rtrans.entity.RequestMessage;
import ca.bc.gov.moh.rtrans.entity.User;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.datatype.CX;
import ca.uhn.hl7v2.model.v24.datatype.XPN;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.validator.internal.util.Contracts;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import static ca.bc.gov.moh.rtrans.entity.transaction.converter.HealthAuthorityConstants.NAME_TYPE_CODE_V2_CURRENT;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import java.util.Properties;
import org.apache.camel.Exchange;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author Kai.Du
 */
public class HapiEntityConverterV24 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HapiEntityConverterV24.class);
    
    private static final String NAME_TYPE_CODE_PREFERRED = "PREFERRED";
    private static final String NAME_TYPE_CODE_LEGAL = "L";    
    private static final String PHONE_USE_CODE = "PRN";

    private final HealthAuthorityValueMapper haValueMapper;

    public HapiEntityConverterV24(Properties properties){
        this.haValueMapper = new HealthAuthorityValueMapper(properties);
    }
    
    public HapiEntityConverterV24(Exchange exchange) {
        this.haValueMapper = new HealthAuthorityValueMapper(exchange);
    }


    public void mapPIDToPerson(ca.uhn.hl7v2.model.v24.segment.PID pid, Person person, boolean mapAllNameTypes) throws HL7Exception {

        Contracts.assertNotNull(pid);

        //Person person = new Person();
        for (CX cx : pid.getPid3_PatientIdentifierList()) {
            IdentifierAttribute id = new IdentifierAttribute();
            mapCXToId(cx, id);
            person.getIdentifier().add(id);
        }

        final String birthDateString = pid.getDateTimeOfBirth().encode();
        if (!StringUtils.isEmpty(birthDateString)) {
            person.setBirthDate(new DateAttribute(
                    DataTypeConverter.convertStringToDate(birthDateString)
            )
            );
        }
        final String deathDateString = pid.getPatientDeathDateAndTime().encode();
        if (!StringUtils.isEmpty(deathDateString)) {
            person.setDeathDate(new DateAttribute(
                    DataTypeConverter.convertStringToDate(deathDateString)
            )
            );
        }

        person.setGender(DataTypeConverter.convertV2ValueToGender(pid.getAdministrativeSex()));

        if (pid.getBirthOrder()!=null){
            if (pid.getBirthOrder().getValue()!=null){
                try{
                    person.setBirthOrder(new IntegerAttribute(Integer.parseInt(pid.getBirthOrder().getValue())));
                }catch(Exception e){
                    //ignore integer parsing exception
                }
            }
        }
        
        String preferredGivenName = null;
        
        //Check for preferred name in names
        for (XPN patientName : pid.getPatientName()) {
            final String nameTypeCode = patientName.getNameTypeCode().encode();
            if (NAME_TYPE_CODE_PREFERRED.equals(nameTypeCode.toUpperCase())) {
                char[] charArray = {'|'};  //use | as delimeter to make sure only capitalize the first word in name
                preferredGivenName = WordUtils.capitalize(patientName.getGivenName().encode(), charArray);
            }
        }
        //Check for preferred name in alias (overwrite preferred name in names if found)
        for (XPN patientAlia : pid.getPatientAlias()) {
            final String nameTypeCode = patientAlia.getNameTypeCode().encode();
            if (NAME_TYPE_CODE_PREFERRED.equals(nameTypeCode.toUpperCase())) {
                char[] charArray = {'|'};  //use | as delimeter to make sure only capitalize the first word in name
                preferredGivenName = WordUtils.capitalize(patientAlia.getGivenName().encode(), charArray);
            }
        }

        for (XPN xpn : pid.getPatientName()) {
            String nameTypeCode = xpn.getNameTypeCode().encode();
            // added condition for V2 address type code 'Current'
            // to match the latest PHSA sample messages
            if (NAME_TYPE_CODE_LEGAL.equals(nameTypeCode)
                    || NAME_TYPE_CODE_V2_CURRENT.equalsIgnoreCase(nameTypeCode) || mapAllNameTypes) {
                PersonNameAttribute name = new PersonNameAttribute();
                mapXPNToName(xpn, name);
                if (!StringUtils.isEmpty(preferredGivenName)) {
                    name.setPreferredGivenName(preferredGivenName);
                }
                person.getName().add(name);
            }
        }

    }
     
    
    public void mapPersonToPID(Person person, PID pid) throws HL7Exception {
        if (person == null) {
            return;
        }
        mapGenderToPID(person, pid);
        mapBirthDateToPID(person, pid);
        mapDeathDateToPID(person, pid);
        mapPatientIDToPID(person, pid);

    }
    
    private void mapPatientIDToPID(Person person, PID pid) throws DataTypeException, HL7Exception {
        if (person.getPHN() != null) {
            pid.getPid2_PatientID().getCx1_ID().parse(person.getPHN());
            
            //TODO Figure out setting constants for these
            pid.getPid2_PatientID().getCx4_AssigningAuthority().getHd1_NamespaceID().parse("BC");
            pid.getPid2_PatientID().getCx5_IdentifierTypeCode().parse(HealthAuthorityConstants.PH);
        }
    }

    private void mapDeathDateToPID(Person person, PID pid) throws DataTypeException, HL7Exception {
        if (person.getDeathDate() != null && !person.getDeathDate().getMasked()) {
            pid.getPid29_PatientDeathDateAndTime().getTs1_TimeOfAnEvent().setValue(DataTypeConverter.convertToDateTS(person.getDeathDate().getValue()));
        }
    }

    private void mapBirthDateToPID(Person person, PID pid) throws DataTypeException {
        if (person.getBirthDate() != null && !person.getBirthDate().getMasked()) {
            pid.getPid7_DateTimeOfBirth().getTs1_TimeOfAnEvent().setValue(DataTypeConverter.convertToDateTS(person.getBirthDate().getValue()));
        }
    }

    private void mapGenderToPID(Person person, PID pid) throws DataTypeException {
        if (person.getGender() != null) {
            pid.getPid8_AdministrativeSex().setValue(DataTypeConverter.convertGenderValueToV2(person.getGender()));
        }
    }
    
    public void mapPersonToZIA(Person person, ZIA zia) throws HL7Exception{
        mapNamesToZIA(person, zia);
        mapPhoneToZIA(person, zia);
        mapAddressToZIA(person, zia);
        
    }
    
    private void mapNamesToZIA(Person person, ZIA zia) throws HL7Exception {
        PersonNameAttribute personName;
        
        if (person.getName().isEmpty()) {
            return;           
        }
        personName = person.getName().get(0);
        //ensure family name is no longer than 35 characters
        zia.getExtendedPersonName().getXpn1_FamilyName().parse(DataTypeConverter.convertFamilyNameToV2(personName.getLastName()));
        //ensure Given Names is no longer than 15 characters
        zia.getExtendedPersonName().getXpn2_GivenName().parse(DataTypeConverter.convertGivenNameToV2(personName.getFirstName()));
        zia.getExtendedPersonName().getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof().parse(DataTypeConverter.convertGivenNameToV2(personName.getMiddleName()));
        //We are using the title for third names both in the entity and the ZIA segment
        zia.getExtendedPersonName().getXpn6_DegreeEgMD().parse(DataTypeConverter.convertGivenNameToV2(personName.getTitle()));
        //Type should always be 'L'
        zia.getExtendedPersonName().getXpn7_NameTypeCode().parse(NAME_TYPE_CODE_LEGAL);
        
    }
    
    private void mapPhoneToZIA(Person person, ZIA zia) throws HL7Exception {
        PhoneAttribute phoneAttribute;
        //Check that there is phone data and that the type is 'Home'
        if (person.getPhone().isEmpty() || 
                person.getPhone().get(0).getType() == null || 
                !person.getPhone().get(0).getType().equals(CommunicationTypes.Home)) {
            return;
        }
        phoneAttribute = person.getPhone().get(0);
        
        zia.getZIA17_ExtendedTelephoneNumber().getXtn2_TelecommunicationUseCode().parse(PHONE_USE_CODE);
        zia.getZIA17_ExtendedTelephoneNumber()
                .getXtn3_TelecommunicationEquipmentType()
                .parse(DataTypeConverter.convertCommunicationTypeToV2(phoneAttribute.getType()));
        zia.getZIA17_ExtendedTelephoneNumber().getXtn6_AreaCityCode().parse(phoneAttribute.getAreaCode());
        zia.getZIA17_ExtendedTelephoneNumber().getXtn7_PhoneNumber().parse(phoneAttribute.getNumber());
    }

    private void mapAddressToZIA(Person person, ZIA zia) throws HL7Exception {
        
        AddressAttribute address = null;
        
        if (person.getAddress().isEmpty()) {
            return;
        }
        //Get a home address if available otherwise use a mailing address
        //If not home or mailing don't use the address
        for (AddressAttribute currentAddress : person.getAddress()) {
            if (currentAddress.getType() == null) {
                //DO NOTHING
            }
            else if (currentAddress.getType().equals(AddressTypes.Home)) {
                address = currentAddress;
                break;
            } 
            else if (address == null && currentAddress.getType().equals(AddressTypes.Mail)) {
                address = currentAddress;
            }
        }
        if (address != null){           
            
            int lineCount = address.getStreetAddressLines() != null ? address.getStreetAddressLines().size() : 0;
            if (lineCount >= 1) {
                zia.getZIA16_ExtendedAddress().getZAD1_AddressLine1().parse(DataTypeConverter.clipAddressLineOrCityForV2(address.getStreetAddressLines().get(0)));
            }
            if (lineCount >= 2) {
                zia.getZIA16_ExtendedAddress().getZAD2_AddressLine2().parse(DataTypeConverter.clipAddressLineOrCityForV2(address.getStreetAddressLines().get(1)));
            }
            if (lineCount >= 3) {
                zia.getZIA16_ExtendedAddress().getZAD3_AddressLine3().parse(DataTypeConverter.clipAddressLineOrCityForV2(address.getStreetAddressLines().get(2)));
            }
            
            zia.getZIA16_ExtendedAddress().getZAD20_City().parse(DataTypeConverter.clipAddressLineOrCityForV2(address.getCity()));
            zia.getZIA16_ExtendedAddress().getZAD21_Province().parse(address.getProvince());
            zia.getZIA16_ExtendedAddress().getZAD22_PostalCode().parse(address.getPostalCode());
            
            String country = "";
            if(address.getCountry() != null){
                country = (String) CountryCodeMapper.countryCodes.getKey(address.getCountry());     
            }
            zia.getZIA16_ExtendedAddress().getZAD23_Country().parse(country);
 
            zia.getZIA16_ExtendedAddress().getZAD24_AddressType()
                    .parse(DataTypeConverter.convertAddressTypeValueToV2(address.getType()));        
            zia.getZIA16_ExtendedAddress().getZAD28_ValidAddressIndicator()
                    .parse(DataTypeConverter.convertIsAddressValid(address.getIsVerified()));
            
        }
        
        
    }

    public void mapMSHToRequestMessage(MSH msh, RequestMessage message) throws HL7Exception, ParseException {
        String messageID = msh.getMsh10_MessageControlID().getValue();
        String sendingApplication = msh.getMsh3_SendingApplication().encode();
        String sendingFacility = msh.getMsh4_SendingFacility().encode();
        String receivingApplication = msh.getMsh5_ReceivingApplication().encode();
        String receivingFacility = msh.getMsh6_ReceivingFacility().encode();
        String security = msh.getMsh8_Security().encode();
        
        String encodedCreationTime = msh.getMsh7_DateTimeOfMessage().encode();
        Date creationTime = DataTypeConverter.convertStringToDate(encodedCreationTime);

        final String senderSystemName
                = getSenderSystemName(sendingApplication, sendingFacility);
        final String senderOrganizationName
                = getSenderOrganizationName(sendingFacility);
        final String receiverSystemName
                = getReceiverSystemName(receivingApplication);
        final String receiverOrganizationName
                = getReceiverOrganizationName(receivingFacility);

        // String assigningAuthority = hapi.getMSH().getMsh4_SendingFacility().encode();
        message.setMessageId(messageID);
        message.setCreationTime(creationTime);
        message.getReceiver().get(0).setSystemName(receiverSystemName);
        message.getReceiver().get(0).setOrganization(receiverOrganizationName);
        message.getSender().setSystemName(senderSystemName);
        message.getSender().setOrganization(senderOrganizationName);
        
        Author author = message.getAuthor();
        if (author == null) {
            author = new Author();
            message.setAuthor(author);
        }
        User operatorUser = author.getUser();
        if (operatorUser == null) {
            operatorUser = new User();
            author.setUser(operatorUser);
        }
        
        message.getAuthor().getUser().setUserId(security);

    }

    public String getReceiverOrganizationName(String receivingFacility) throws HL7Exception {
        return haValueMapper.mapRequestReceiverFacility(receivingFacility);
    }

    public String getReceiverSystemName(String receiverApplication) throws HL7Exception {
        return haValueMapper.mapRequestReceiverApplication(receiverApplication);
    }

    public String getSenderOrganizationName(String sendingFacility) throws HL7Exception {
        return haValueMapper.mapRequestSenderFacility(sendingFacility);
    }

    public String getSenderSystemName(String sendingApplication, String SendingFacility) throws HL7Exception {
        return haValueMapper.mapRequestSenderApplication(sendingApplication, SendingFacility);
    }


    private void mapXPNToName(XPN xpn, PersonNameAttribute name) throws HL7Exception {

        char[] charArray = {'|'}; //use | as delimeter to make sure only capitalize the first word in name        
        name.setLastName(WordUtils.capitalize(xpn.getFamilyName().encode(), charArray));
        name.setFirstName(WordUtils.capitalize(xpn.getGivenName().encode(), charArray));
        name.setMiddleName(WordUtils.capitalize(xpn.getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof().encode(), charArray));
        name.setPrefix(xpn.getXpn5_PrefixEgDR().encode());
        name.setSuffix(xpn.getXpn4_SuffixEgJRorIII().encode());
        name.setDegree(xpn.getXpn6_DegreeEgMD().encode());
        
        name.setType(PersonNameTypes.fromValue(
                xpn.getNameTypeCode().encode())
        );
    }

    public void mapCXToId(CX cx, IdentifierAttribute id) throws HL7Exception {
        id.setValue(cx.getCx1_ID().encode());
        id.setSource(cx.getCx4_AssigningAuthority().encode());
        //This value is always PH so we get BCPHN in the Entity
        id.setType(IdentifierTypes.fromValueToType(HealthAuthorityConstants.PH)); 
    }

}
