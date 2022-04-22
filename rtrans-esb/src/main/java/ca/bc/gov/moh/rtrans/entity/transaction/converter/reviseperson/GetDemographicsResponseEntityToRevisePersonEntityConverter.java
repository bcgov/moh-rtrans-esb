package ca.bc.gov.moh.rtrans.entity.transaction.converter.reviseperson;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.AddressTypes;
import ca.bc.gov.moh.rtrans.entity.Author;
import ca.bc.gov.moh.rtrans.entity.CommunicationTypes;
import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.IdentifierTypes;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PhoneAttribute;
import ca.bc.gov.moh.rtrans.entity.User;
import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographicsResponse;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import ca.bc.gov.moh.rtrans.entity.transaction.RevisePerson;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.CountryCodeMapper;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeValidator;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HapiEntityConverterV24;
import ca.bc.gov.moh.rtrans.service.v2.V2ServiceConstants;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R07;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.components.ZAD;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.datatype.XTN;
import java.util.Date;

/**
 *
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
@Converter
public class GetDemographicsResponseEntityToRevisePersonEntityConverter {
    
    @Converter
    public static RevisePerson convertGDResponse(GetDemographicsResponse demographicsResponse, Exchange exchange) throws Exception{
        
        HapiEntityConverterV24 hapiEntityConverterV24 = new HapiEntityConverterV24(exchange);
        R07 r07 = new R07(); //used to create a zia segment to parse the header
        r07.initQuickstart("R07", null, "D");
        ZIA zia = r07.getZIA();
        Author author = new Author();
        User user = new User();
        
        Message message = exchange.getIn();
        RevisePerson revisePerson = new RevisePerson();
        //Get the identifier, name, birthdate, and gender from the HCIM response
        //We make sure that we use the original PHN
        IdentifierAttribute ident = new IdentifierAttribute(message.getHeader(V2ServiceConstants.pidSegmentPHN, String.class), "MOH_CRS", IdentifierTypes.BCPHN);
        revisePerson.getPerson().getIdentifier().add(ident);
        revisePerson.getPerson().setName(demographicsResponse.getSearchResult().getName());
        revisePerson.getPerson().setBirthDate(demographicsResponse.getSearchResult().getBirthDate());
        revisePerson.getPerson().setGender(demographicsResponse.getSearchResult().getGender());
  
        //Get the other RevisePerson values from the header (these were saved from the original request)        
        String messageId = message.getHeader(V2ServiceConstants.strMessageIDProperty, String.class);
        String creationTimeString = message.getHeader(V2ServiceConstants.messageCreationTime, String.class);  
        Date creationTime = DataTypeConverter.convertStringToDate(creationTimeString);
        
        String eventDateTimeString = message.getHeader(V2ServiceConstants.eventDateTime, String.class);
        Date eventDateTime = DataTypeConverter.convertStringToDate(eventDateTimeString);
     
        String authorUserId = message.getHeader(V2ServiceConstants.messageSecurity, String.class);
       
        String senderApplication = message.getHeader(V2ServiceConstants.senderApplication, String.class);
        String senderFacility = message.getHeader(V2ServiceConstants.senderFacility, String.class);
        String receiverApplication = message.getHeader(V2ServiceConstants.receiverApplication, String.class);
        String receiverFacility = message.getHeader(V2ServiceConstants.receiverFacility, String.class);
        String ziaSegmentZAD = message.getHeader(V2ServiceConstants.ziaSegmentZAD, String.class);
        String ziaSegmentXTN = message.getHeader(V2ServiceConstants.ziaSegmentXTN, String.class);
           
        revisePerson.setMessageId(messageId);
        revisePerson.setEventTime(eventDateTime);
        revisePerson.setCreationTime(creationTime);
       
        revisePerson.setAuthor(author);
        revisePerson.getAuthor().setUser(user);
        revisePerson.getAuthor().getUser().setUserId(authorUserId);
        
        revisePerson.getSender().setSystemName(hapiEntityConverterV24.getSenderSystemName(senderApplication, senderFacility));
        revisePerson.getSender().setOrganization(hapiEntityConverterV24.getSenderOrganizationName(senderFacility));
        revisePerson.getReceiver().get(0).setSystemName(receiverApplication);
        revisePerson.getReceiver().get(0).setOrganization(receiverFacility);
        
        setZIAPersonValues(revisePerson, zia, ziaSegmentZAD, ziaSegmentXTN);
        
        return revisePerson;
    }
    
    private static void setZIAPersonValues(RevisePerson revisePerson, ZIA zia, String ziaSegmentZAD, String ziaSegmentXTN) throws HL7Exception {
        
        Person person = revisePerson.getPerson();
        person.getPhone().add(new PhoneAttribute());
        
        zia.getZIA16_ExtendedAddress().parse(ziaSegmentZAD);
        zia.getZIA17_ExtendedTelephoneNumber().parse(ziaSegmentXTN);
        
        ZAD extendedAddress = zia.getZIA16_ExtendedAddress();
        XTN extendedPhone = zia.getZIA17_ExtendedTelephoneNumber();
        
        if (extendedAddress != null) {
            String adressLine1 = extendedAddress.getZAD1_AddressLine1().encode();
            String adressLine2 = extendedAddress.getZAD2_AddressLine2().encode();
            String adressLine3 = extendedAddress.getZAD3_AddressLine3().encode();
            String city = extendedAddress.getZAD20_City().encode();
            String province = extendedAddress.getZAD21_Province().encode();
            String postalCode = extendedAddress.getZAD22_PostalCode().encode();
            String country = "";
            if(extendedAddress.getZAD23_Country().encode() != null){
                country = (String) CountryCodeMapper.countryCodes.get(extendedAddress.getZAD23_Country().encode());             
            }
            
            String addressType = extendedAddress.getZAD24_AddressType().encode();
            AddressAttribute addr = new AddressAttribute();
            addr.getStreetAddressLines().add(adressLine1);
            addr.getStreetAddressLines().add(adressLine2);
            addr.getStreetAddressLines().add(adressLine3);
            addr.setCity(city);
            addr.setProvince(province);
            addr.setPostalCode(postalCode);
            addr.setCountry(country);
            addr.setType(AddressTypes.fromValue(addressType));
            if(DataTypeValidator.addressAttributeNotEmpty(addr)){
                person.getAddress().add(addr);
            }
            else{
                addr = null;
            }
        }
        if (extendedPhone != null) {

            String areaCode = extendedPhone.getXtn6_AreaCityCode().encode();
            String phoneNumber = extendedPhone.getXtn7_PhoneNumber().encode();

            //Always set to Home
            person.getPhone().get(0).setType(CommunicationTypes.Home);
            person.getPhone().get(0).setAreaCode(areaCode);
            person.getPhone().get(0).setNumber(phoneNumber);
        } 
    }
}
