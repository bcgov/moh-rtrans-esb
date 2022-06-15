/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;

import ca.bc.gov.moh.rtrans.entity.AddressAttribute;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.ProbabalisticPersonSearchResult;
import ca.bc.gov.moh.rtrans.entity.SystemResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidatesResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.ErrorCodeValueMapper;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HealthAuthorityConstants;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HealthAuthorityValueMapper;
import ca.bc.gov.moh.rtrans.service.v2.Hl7v2Parser;
import ca.bc.gov.moh.rtrans.service.v2.V2ServiceConstants;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09Response;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09Response_PersonFound;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZTL;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.segment.ERR;
import ca.uhn.hl7v2.model.v24.segment.MSA;
import ca.uhn.hl7v2.model.v24.segment.PID;
import java.io.IOException;
import java.util.List;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Kai.Du
 */
@Converter
public class FindCandidatesEntityToHapiTypeConverter {

    /**
     * Convert find candidates response entity to R09Response which is Hapi v2 model
     * @param response the find candidates response entity 
     * @param exchange 
     * @return R09Response R09 Hapi v2 model
     * @throws HL7Exception
     * @throws IOException 
     */
    @Converter
    public static R09Response convertFCR2R09Response(FindCandidatesResponse response, Exchange exchange) throws HL7Exception, IOException {
        R09Response r09Response = new ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09Response();
        HealthAuthorityValueMapper healthAuthorityValueMapper = new HealthAuthorityValueMapper(exchange);
        r09Response.initQuickstart("R09", null, healthAuthorityValueMapper.mapMSH11Segment("ENV_INPUT"));
        Hl7v2Parser.setMSHValues(response, r09Response.getMSH(), exchange);
        convertR09FCR2MSAERR(response, r09Response.getMSA(), r09Response.getERR(), exchange);
        convertR09FCR2ZTL(response, r09Response.getZTL());
        convertR09FCR2PIDZIA(response, r09Response);
        return r09Response;
    }    

    /**
     * Convert find candidates response entity to MSA segment in Hapi v2 model
     * @param response FindCandidatesResponse
     * @param msa MSA segment
     * @throws DataTypeException
     * @throws HL7Exception 
     */
    private static void convertR09FCR2MSAERR(FindCandidatesResponse response, MSA msa, ERR err, Exchange exchange) throws DataTypeException, HL7Exception, IOException {
        
        ErrorCodeValueMapper errorCodeValueMapper = new ErrorCodeValueMapper(exchange);
        
        String v3ResponseCode = response.getSystemResponse().get(0).getCode();
        String v2ResponseCode = errorCodeValueMapper.mapFCErrorCode(v3ResponseCode);
        String v2ResponseMessage = errorCodeValueMapper.mapCodeToMessage(v3ResponseCode);
        String v2ResponseAck = errorCodeValueMapper.mapErrorCodeToAck(v3ResponseCode);
        
        //MSA-1, Acknowledge Code
        msa.getMsa1_AcknowledgementCode().parse(v2ResponseAck);
        
        //MSA-2, Message Control ID
        msa.getMsa2_MessageControlID().setValue(exchange.getIn().getHeader(V2ServiceConstants.messageid, String.class));
        
        //MSA-3, map response code from v3 entity to v2 code
        List<SystemResponse> systemResponseList = response.getSystemResponse();
        if(systemResponseList!=null && !systemResponseList.isEmpty()) {
            msa.getTextMessage().setValue(v2ResponseCode + v2ResponseMessage);
            err.getErr1_ErrorCodeAndLocation(0).getEld4_CodeIdentifyingError().getCe1_Identifier().setValue(v2ResponseCode);
            err.getErr1_ErrorCodeAndLocation(0).getEld4_CodeIdentifyingError().getCe2_Text().setValue(v2ResponseMessage);
        }
        
    }
        
    /**
     * Convert R09 Find Candidates response entity to ZTL v2 segment
     * @param response FindCandidatesResponse the R09 Find Candidates response entity.
     * @param ztl ZTL the v2 ZTL segment
     * @throws DataTypeException thrown when setting ZTL data failed
     */
    private static void convertR09FCR2ZTL(FindCandidatesResponse response, ZTL ztl) throws DataTypeException {

        List<ProbabalisticPersonSearchResult> searchResultList = response.getSearchResults();
        if(searchResultList!=null && !searchResultList.isEmpty()) {
            ztl.getTransactionSegmentCount().getCq1_Quantity().setValue(String.valueOf(searchResultList.size()));
            ztl.getTransactionSegmentCount().getCq2_Units().getCe1_Identifier().setValue(HealthAuthorityConstants.UNIT_RD);
        }
        
    }
    
    /**
     * Covert R09 Find Candidates response entity to customized R09Response_PersonFound.
     * Each ProbabalisticPersonSearchResult is mapped to one R09Response_PersonFound
     * @param response FindCandidatesResponse the R09 Find Candidates response entity
     * @param r09Response R09Response the R09 Find Candidates response entity
     * @throws DataTypeException thrown when setting PID or ZIA data failed
     */
    private static void convertR09FCR2PIDZIA(FindCandidatesResponse response, R09Response r09Response) throws DataTypeException, HL7Exception {
        
        List<ProbabalisticPersonSearchResult> searchResultList = response.getSearchResults();
        if(searchResultList!=null && !searchResultList.isEmpty()) {
            int i=0;
            for(ProbabalisticPersonSearchResult ppsr : searchResultList) {
                R09Response_PersonFound r09RRF = r09Response.getPersonFound(i);
                convertR09FCR2PID(ppsr.getPerson(), i+1, r09RRF.getPID());
                convertR09FCR2ZIA(ppsr.getPerson(), i+1, r09RRF.getZIA());
                i++;
            }
        }
        
    }
    
    /**
     * Convert R09 Find Candidates response Person entity to PID v2 segment
     * @param person Person the person entity included in R09 Find Candidates response entity
     * @param repNumber int the number of repetition 
     * @param pid PID v2 PID segment
     * @throws DataTypeException thrown when setting PID data failed
     */
    private static void convertR09FCR2PID(Person person, int repNumber, PID pid) throws DataTypeException, HL7Exception {
        
        pid.getSetIDPID().setValue(StringUtils.leftPad(String.valueOf(repNumber), 3, "0"));
        pid.getPatientID().getCx1_ID().setValue(person.getPHN());
        pid.getPatientID().getCx4_AssigningAuthority().getHd1_NamespaceID().setValue(HealthAuthorityConstants.BC);
        pid.getPatientID().getCx5_IdentifierTypeCode().setValue(HealthAuthorityConstants.PH);
        pid.getPatientID().getCx6_AssigningFacility().getHd1_NamespaceID().setValue(HealthAuthorityConstants.MOH);
        pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(DataTypeConverter.convertToDateTS(person.getBirthDate().getValue()));
        pid.getAdministrativeSex().setValue(DataTypeConverter.convertGenderValueToV2(person.getGender()));
        if (person.getDeathDate() != null && person.getDeathDate().getValue() != null) {
            pid.getPid29_PatientDeathDateAndTime().parse(DataTypeConverter.convertToDateTS(person.getDeathDate().getValue()));
        }
        
        
    }
    
    /**
     * Convert R09 Find Candidates response Person entity to ZIA v2 segment
     * @param person Person the person entity included in R09 Find Candidates response entity
     * @param repNumber int the number of repetition 
     * @param zia ZIA the v2 ZIA segment
     * @throws DataTypeException thrown when setting ZIA data failed
     */
    private static void convertR09FCR2ZIA(Person person, int repNumber, ZIA zia) throws DataTypeException {
        
        List<PersonNameAttribute> personNameList = person.getName();
        if(personNameList!=null) {
            PersonNameAttribute personName = personNameList.get(0);
            zia.getZIA15_ExtendedPersonName().getXpn1_FamilyName().getFn1_Surname().setValue(DataTypeConverter.convertFamilyNameToV2(personName.getLastName()));
            zia.getZIA15_ExtendedPersonName().getXpn2_GivenName().setValue(DataTypeConverter.convertGivenNameToV2(personName.getFirstName()));
            zia.getZIA15_ExtendedPersonName().getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof().setValue(DataTypeConverter.convertGivenNameToV2(personName.getMiddleName()));
            zia.getZIA15_ExtendedPersonName().getXpn4_SuffixEgJRorIII().setValue(DataTypeConverter.convertGivenNameToV2(personName.getTitle()));
            //in v2 message, the both documented and declared in entity are mapped to "L" declared.
            zia.getZIA15_ExtendedPersonName().getXpn7_NameTypeCode().setValue(DataTypeConverter.PERSON_NAME_TYPE_LEGAL_DECLARED);
        }
        List<AddressAttribute> addressList = person.getAddress();
        if(addressList!=null && !addressList.isEmpty()) {
            AddressAttribute address = addressList.get(0);
            List<String> addressLineList = address.getStreetAddressLines();
            StringBuilder sb = new StringBuilder();
            if(addressLineList!=null) {
                for(String addressLine : addressLineList) {
                    sb.append(StringUtils.defaultString(addressLine)).append(" ");
                }
            }
            sb.append(StringUtils.defaultString(address.getCity())).append(" ")
                .append(StringUtils.defaultString(address.getProvince())).append(" ")
                .append(StringUtils.defaultString(address.getPostalCode()));
            
            String addressString = sb.toString();
            if (addressString.length() > 50) {
                addressString = addressString.substring(0, 50);
            }
            zia.getZIA18_PatientDisplayAddress().setValue(addressString.trim());
        }
        zia.getSetIdZIA().setValue(StringUtils.leftPad(String.valueOf(repNumber), 3, "0"));

    }

}

