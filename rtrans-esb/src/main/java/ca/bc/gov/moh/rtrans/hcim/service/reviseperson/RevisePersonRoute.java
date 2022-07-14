package ca.bc.gov.moh.rtrans.hcim.service.reviseperson;

import ca.bc.gov.moh.rtrans.entity.transaction.RevisePerson;
import ca.bc.gov.moh.rtrans.entity.transaction.RevisePersonResponse;
import ca.bc.gov.moh.rtrans.service.v2.RTransRouteBuilder;
import ca.bc.gov.moh.rtrans.service.v2.ValidGetDemoResponsePredicate;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R07Response;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R07ResponseInvalid;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.hl7.v3.HCIMINPersonRevised;

/**
 *
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
public class RevisePersonRoute extends RTransRouteBuilder {
    
    private final ValidGetDemoResponsePredicate validGetDemoResponsePredicate = new ValidGetDemoResponsePredicate();

    @Override
    public void configure() throws Exception {
        super.configure();
        
        JaxbDataFormat jaxb = new JaxbDataFormat();
        jaxb.setContextPath("org.hl7.v3");
        
        String esbHCIMRevisePersonEndpoint = "spring-ws:{{hcimRevisePersonEndPointURI}}?soapAction=urn:hl7-org:v3/PRPA_IN101204"
            + "&messageFilter=#bean:healthAuthorityMessageFilter";

        from("direct:revisePerson")
                .routeId("direct:revisePerson")
                //.process(new RTransAuditProcessorConfig(START, INFO))
                //.process(AUDIT_PROCESSOR)
                .choice()
                    .when(validGetDemoResponsePredicate)
                        .convertBodyTo(RevisePerson.class)
                        .convertBodyTo(HCIMINPersonRevised.class)
                        .marshal(jaxb)
                        //.process(new RTransAuditProcessorConfig(HCIM_IN, INFO))
                        //.process(AUDIT_PROCESSOR)
                        .process(FILEDROP_HCIM_IN)
                        .toD(esbHCIMRevisePersonEndpoint + "&sslContextParameters=#sslParameters").id(NODE_HCIM_REVISE_PERSON_ENDPOINT)
                        //.process(new RTransAuditProcessorConfig(HCIM_OUT, INFO))
                        //.process(AUDIT_PROCESSOR)
                        .process(FILEDROP_HCIM_OUT)
                        .unmarshal(jaxb)
                        .convertBodyTo(RevisePersonResponse.class)
                        //.process(new RTransAuditProcessorConfig(PROCESS_RESPONSE, INFO))
                        //.process(AUDIT_PROCESSOR)
                        .convertBodyTo(R07Response.class)
                    .otherwise()
                        .convertBodyTo(R07ResponseInvalid.class);
    }
    
    // Node IDs allow tests to replace these nodes with test doubles.
    public static final String NODE_HCIM_REVISE_PERSON_ENDPOINT = "HCIMRevisePersonEndpoint_Node";
}
