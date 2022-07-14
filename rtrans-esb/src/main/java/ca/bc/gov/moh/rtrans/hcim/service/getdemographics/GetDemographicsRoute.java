package ca.bc.gov.moh.rtrans.hcim.service.getdemographics;

import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographicsResponse;
import ca.bc.gov.moh.rtrans.service.v2.RTransRouteBuilder;
import org.apache.camel.model.dataformat.JaxbDataFormat;

import org.hl7.v3.HCIMINGetDemographics;

/**
 * 
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
public class GetDemographicsRoute extends RTransRouteBuilder {
    
    @Override
    public void configure() throws Exception {
        super.configure();
        
        JaxbDataFormat jaxb = new JaxbDataFormat();
        jaxb.setContextPath("org.hl7.v3");
        
        String esbHCIMGetDemoEndpoint = "spring-ws:{{hcimGetDemographicsEndPointURI}}?soapAction=urn:hl7-org:v3/QUPA_IN101101"
                + "&messageFilter=#bean:healthAuthorityMessageFilter"
                + "&timeout={{hcimGetDemographicsTimeout}}";
        
        from("direct:getDemographics")
                .routeId("direct:getDemographics")
                //.process(new RTransAuditProcessorConfig(START, INFO))
                //.process(AUDIT_PROCESSOR)
                .convertBodyTo(HCIMINGetDemographics.class)
                .marshal(jaxb)
                .process(FILEDROP_ESB_IN)
                //.process(new RTransAuditProcessorConfig(ESB_IN, INFO))
                //.process(AUDIT_PROCESSOR)
                .toD(esbHCIMGetDemoEndpoint + "&sslContextParameters=#sslParameters").id(NODE_HCIM_GET_DEMO_ENDPOINT)
                //.process(new RTransAuditProcessorConfig(ESB_OUT, INFO))
                //.process(AUDIT_PROCESSOR)
                .process(FILEDROP_ESB_OUT)
                .unmarshal(jaxb)
                .convertBodyTo(GetDemographicsResponse.class);
                //.process(new RTransAuditProcessorConfig(PROCESS_RESPONSE, INFO))
                //.process(AUDIT_PROCESSOR);
    }
    
    // Node IDs allow tests to replace these nodes with test doubles.
    public static final String NODE_HCIM_GET_DEMO_ENDPOINT = "HCIMGetDemoEndpoint_Node";

}
