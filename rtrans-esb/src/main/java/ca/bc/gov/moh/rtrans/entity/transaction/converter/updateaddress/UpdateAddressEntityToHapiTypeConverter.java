/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.updateaddress;

import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographicsResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.RevisePersonResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.DataTypeConverter;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.ErrorCodeValueMapper;
import ca.bc.gov.moh.rtrans.service.v2.Hl7v2Parser;
import ca.bc.gov.moh.rtrans.service.v2.V2ServiceConstants;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R07Response;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R07ResponseInvalid;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.segment.ERR;
import ca.uhn.hl7v2.model.v24.segment.MSA;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import java.io.IOException;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;

/**
 *
 * @author trevor.schiavone
 */
@Converter
public class UpdateAddressEntityToHapiTypeConverter {
    
    public static final String R07_INVALID_PHN_ACK = "AE";
    public static final String R07_INVALID_PHN_CODE = "HNHR533EE";
    public static final String R07_INVALID_PHN_MESSAGE = "PHN GIVEN IS INVALID";
    
    public static final String CPC_ADDRESS_INDICATOR = "U";
    public static final String ADDRESS_VALIDATION_BEST_GUESS_INDICATOR = "N";
    
    
    @Converter
    public R07Response convert(RevisePersonResponse response, Exchange exchange) throws HL7Exception, IOException { 
    
        R07Response r07Response = new R07Response();
        ErrorCodeValueMapper errorCodeValueMapper = new ErrorCodeValueMapper(exchange);

        r07Response.initQuickstart("R07", null, "D");

        MSH msh = r07Response.getMSH();
        MSA msa = r07Response.getMSA();
        ERR err = r07Response.getERR();
        ZIA zia = r07Response.getZIA();

        Hl7v2Parser.setMSHValues(response, msh, exchange);

        String v3ResponseCode = response.getSystemResponse().get(0).getCode();

        String v2ResponseCode = errorCodeValueMapper.mapRPErrorCodeToCode(v3ResponseCode);
        String v2ResponseMessage = errorCodeValueMapper.mapCodeToMessage(v3ResponseCode);
        String v2ResponseAck = errorCodeValueMapper.mapErrorCodeToAck(v3ResponseCode);

        msa.getMsa3_TextMessage().setValue(v2ResponseCode + v2ResponseMessage);  
        msa.getMsa2_MessageControlID().parse(exchange.getIn().getHeader(V2ServiceConstants.strMessageIDProperty, String.class));
        msa.getMsa1_AcknowledgementCode().parse(v2ResponseAck);

        //Map the ERR section
        err.getErrorCodeAndLocation(0).getEld4_CodeIdentifyingError().getCe1_Identifier().parse(v2ResponseCode);
        err.getErrorCodeAndLocation(0).getEld4_CodeIdentifyingError().getCe2_Text().parse(v2ResponseMessage);

        //ZIA just returns the original request ZIA.16 section if it exsists
        zia.getZIA16_ExtendedAddress().parse(exchange.getIn().getHeader(V2ServiceConstants.ziaSegmentZAD, String.class));

        if (exchange.getIn().getHeader(V2ServiceConstants.ziaSegmentZAD) != null) {
            zia.getZIA16_ExtendedAddress().getZAD28_ValidAddressIndicator().parse(CPC_ADDRESS_INDICATOR);
            zia.getZIA16_ExtendedAddress().getZAD29_ValidationDate().parse(DataTypeConverter.convertToDateTS(response.getCreationTime()));
            zia.getZIA16_ExtendedAddress().getZAD35_AddressValidationBestGuessIndicator().parse(ADDRESS_VALIDATION_BEST_GUESS_INDICATOR);          
        }
    
        return r07Response;
    }
    
    @Converter
    public R07ResponseInvalid convert(GetDemographicsResponse response, Exchange exchange) throws HL7Exception, IOException {
        
        ErrorCodeValueMapper errorCodeValueMapper = new ErrorCodeValueMapper(exchange);
        R07ResponseInvalid r07Response = new R07ResponseInvalid();
        r07Response.initQuickstart("R07", null, "D");
            
        MSH msh = r07Response.getMSH();
        MSA msa = r07Response.getMSA();
        ERR err = r07Response.getERR();
        ZIA zia = r07Response.getZIA();
        
        Hl7v2Parser.setMSHValues(response, msh, exchange);
        
        /**
         * the InvalidGetDemographicsPredicate will send the exchange to this converter for one of four reasons.
         * Three of those reasons are correctly mapped to an INVALID PHN error.
         * The fourth requires the error mapper to be invoked.
         */
        String v2ResponseCode, v2ResponseMessage, v2ResponseAck;
        String v3ResponseCode = response.getSystemResponse().get(0).getCode();
        if(v3ResponseCode.equals("BCHCIM.GD.0.0015") || v3ResponseCode.equals("BCHCIM.GD.0.0018.0")){
            v2ResponseCode = errorCodeValueMapper.mapGDErrorCodeToCode(v3ResponseCode);
            v2ResponseMessage = errorCodeValueMapper.mapCodeToMessage(v3ResponseCode);
            v2ResponseAck = errorCodeValueMapper.mapErrorCodeToAck(v3ResponseCode);
        }
        else{
            v2ResponseCode = R07_INVALID_PHN_CODE;
            v2ResponseMessage = R07_INVALID_PHN_MESSAGE;
            v2ResponseAck = R07_INVALID_PHN_ACK;
        }
        
        msa.getMsa3_TextMessage().setValue(v2ResponseCode + v2ResponseMessage);  
        msa.getMsa2_MessageControlID().parse(exchange.getIn().getHeader(V2ServiceConstants.strMessageIDProperty, String.class));
        msa.getMsa1_AcknowledgementCode().parse(v2ResponseAck);

        //Map the ERR section
        err.getErrorCodeAndLocation(0).getEld4_CodeIdentifyingError().getCe1_Identifier().parse(v2ResponseCode);
        err.getErrorCodeAndLocation(0).getEld4_CodeIdentifyingError().getCe2_Text().parse(v2ResponseMessage);
        
        //ZIA just returns the original request ZIA.16 section if it exsists
        zia.getZIA16_ExtendedAddress().parse(exchange.getIn().getHeader(V2ServiceConstants.ziaSegmentZAD, String.class));
        
        if (exchange.getIn().getHeader(V2ServiceConstants.ziaSegmentZAD) != null) {
            zia.getZIA16_ExtendedAddress().getZAD28_ValidAddressIndicator().parse(CPC_ADDRESS_INDICATOR);
            zia.getZIA16_ExtendedAddress().getZAD29_ValidationDate().parse(DataTypeConverter.convertToDateTS(response.getCreationTime()));
            zia.getZIA16_ExtendedAddress().getZAD35_AddressValidationBestGuessIndicator().parse(ADDRESS_VALIDATION_BEST_GUESS_INDICATOR);
        }

        return r07Response;

    }

}
