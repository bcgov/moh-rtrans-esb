/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;

import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographicsResponse;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.ErrorCodeValueMapper;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HapiEntityConverterV24;
import ca.bc.gov.moh.rtrans.service.v2.Hl7v2Parser;
import ca.bc.gov.moh.rtrans.service.v2.V2ServiceConstants;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R03Response;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.segment.ERR;
import ca.uhn.hl7v2.model.v24.segment.MSA;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import java.io.IOException;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;

/**
 *
 * @author trevor.schiavone
 */
@Converter
public class GetDemographicsEntityToHapiTypeConverter {
      
  
    @Converter
    public R03Response convert(GetDemographicsResponse response, Exchange exchange) throws HL7Exception, IOException {
              
        R03Response r03Response = new R03Response();
        HapiEntityConverterV24 hapiEntityConverterV24 = new HapiEntityConverterV24(exchange);
        ErrorCodeValueMapper errorCodeValueMapper = new ErrorCodeValueMapper(exchange);
                   
        //This creates a default R03Response message with MSH-9 set to R03
        r03Response.initQuickstart("R03", null, "T");
        
        MSH msh = r03Response.getMSH();
        MSA msa = r03Response.getMSA();
        ERR err = r03Response.getERR();
        PID pid = r03Response.getPID();
        ZIA zia = r03Response.getZIA();
               
        Hl7v2Parser.setMSHValues(response, msh, exchange);
        
        //Map HCIM Response codes for MSA and ERR sections
        String v3ResponseCode = response.getSystemResponse().get(0).getCode();

        String v2ResponseCode = errorCodeValueMapper.mapGDErrorCodeToCode(v3ResponseCode);
        String v2ResponseMessage = errorCodeValueMapper.mapCodeToMessage(v3ResponseCode);
        String v2ResponseAck = errorCodeValueMapper.mapErrorCodeToAck(v3ResponseCode);
        
        //Set the MSA section
        msa.getMsa3_TextMessage().setValue(v2ResponseCode + v2ResponseMessage);    
        msa.getMsa2_MessageControlID().parse(exchange.getIn().getHeader(V2ServiceConstants.strMessageIDProperty, String.class));
        msa.getMsa1_AcknowledgementCode().parse(v2ResponseAck);
        
        //Set the ERR section
        err.getErrorCodeAndLocation(0).getEld4_CodeIdentifyingError().getCe1_Identifier().parse(v2ResponseCode);
        err.getErrorCodeAndLocation(0).getEld4_CodeIdentifyingError().getCe2_Text().parse(v2ResponseMessage);
        
        //Set the pid and zia sections
        hapiEntityConverterV24.mapPersonToPID(response.getSearchResult(), pid);
        hapiEntityConverterV24.mapPersonToZIA(response.getSearchResult(), zia);
   
        return r03Response;
    }
}
