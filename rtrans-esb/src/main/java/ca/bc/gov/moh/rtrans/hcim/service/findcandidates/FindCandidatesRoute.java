package ca.bc.gov.moh.rtrans.hcim.service.findcandidates;

import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidatesResponse;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09Response;
import ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessorConfig;
import ca.bc.gov.moh.rtrans.service.v2.RTransRouteBuilder;
import org.apache.camel.model.dataformat.JaxbDataFormat;

import org.hl7.v3.HCIMINFindCandidates;

/**
 * Route for handling FindCandidate v2 requests.
 */
public class FindCandidatesRoute extends RTransRouteBuilder {
    
    @Override
    public void configure() throws Exception {
        super.configure();
        
        JaxbDataFormat jaxb = new JaxbDataFormat();
        jaxb.setContextPath("org.hl7.v3");
        
        String esbHCIMFindCandidatesEndpoint = "spring-ws:{{hcimFindCandidatesEndPointURI}}?soapAction=urn:hl7-org:v3/QUPA_IN101103"
                + "&messageFilter=#bean:healthAuthorityMessageFilter"
                + "&timeout={{hcimFindCandidatesTimeout}}";
        
        from("direct:findCandidates")
                .routeId("direct:findCandidates")
                .process(new RTransAuditProcessorConfig(START, INFO))
                .process(AUDIT_PROCESSOR)
                .convertBodyTo(HCIMINFindCandidates.class)
                .marshal(jaxb)
                .process(FILEDROP_ESB_IN)
                .process(new RTransAuditProcessorConfig(ESB_IN, INFO))
                .process(AUDIT_PROCESSOR)
                .toD(esbHCIMFindCandidatesEndpoint + "&sslContextParameters=#sslParameters").id(NODE_HCIM_FIND_CANDIDATES_ENDPOINT)
                .process(new RTransAuditProcessorConfig(ESB_OUT, INFO))
                .process(AUDIT_PROCESSOR)
                .process(FILEDROP_ESB_OUT)
                .unmarshal(jaxb)
                .convertBodyTo(FindCandidatesResponse.class)
                .process(new RTransAuditProcessorConfig(PROCESS_RESPONSE, INFO))
                .process(AUDIT_PROCESSOR)
                .convertBodyTo(R09Response.class);
    }
    
    // Node IDs allow tests to replace these nodes with test doubles.
    public static final String NODE_LOOKUP_SSL_PARAMS_FOR_ORG = "LookupSslParamsForOrg_FindCandidatesRoute_Node";
    public static final String NODE_HCIM_FIND_CANDIDATES_ENDPOINT = "HCIMFindCandidatesEndpoint_Node";

}
