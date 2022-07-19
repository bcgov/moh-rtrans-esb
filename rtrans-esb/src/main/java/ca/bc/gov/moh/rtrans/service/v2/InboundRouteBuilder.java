package ca.bc.gov.moh.rtrans.service.v2;

import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidates;
import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographics;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R03Response;
import ca.bc.gov.moh.rtrans.service.v2.exception.UnhandledHl7v2MessageException;
import io.netty.buffer.ByteBuf;

public class InboundRouteBuilder extends RTransRouteBuilder {
    
    private final FindCandidatesPredicate findCandidatesPredicate = new FindCandidatesPredicate();
    private final GetDemographicsPredicate getDemographicsPredicate = new GetDemographicsPredicate();
    private final UpdateAddressPredicate updateAddressPredicate = new UpdateAddressPredicate();
    
    private final HnSecureAddLeadingBytes hnSecureAddLeadingBytes = new HnSecureAddLeadingBytes();
    private final HnSecureStripLeadingBytes hnSecureStripLeadingBytes = new HnSecureStripLeadingBytes();
    
    @Override
    public void configure() throws Exception {
        super.configure();
                
        from("jetty:http://{{minaListenerURI}}:{{port}}/{{endpoint}}?httpMethodRestrict=POST")                              
                .routeId("rtrans-route")
            	.process(hnSecureStripLeadingBytes)
                .process(new Hl7v2Parser())
                .choice()
                    .when(findCandidatesPredicate)
                        //.process(new RTransAuditProcessorConfig(RECEIVE, INFO))
                        //.process(AUDIT_PROCESSOR)
                        .process(FILEDROP_RECEIVE)
                        .convertBodyTo(FindCandidates.class)
                        .to("direct:findCandidates")
                    .when(getDemographicsPredicate)
                        //.process(new RTransAuditProcessorConfig(RECEIVE, INFO))
                        //.process(AUDIT_PROCESSOR)
                        .process(FILEDROP_RECEIVE)
                        .convertBodyTo(GetDemographics.class)
                        .to("direct:getDemographics")
                        .convertBodyTo(R03Response.class)
                    .when(updateAddressPredicate)
                        //.process(new RTransAuditProcessorConfig(RECEIVE, INFO))
                        //.process(AUDIT_PROCESSOR)
                        .process(FILEDROP_RECEIVE)
                        .convertBodyTo(GetDemographics.class)                       
                        .to("direct:getDemographics")
                        .to("direct:revisePerson")
                    .otherwise()
                        .throwException(new UnhandledHl7v2MessageException("HL7 formatting error or type not handled"))
                .end()
                .process(hnSecureAddLeadingBytes)
                //.process(new RTransAuditProcessorConfig(COMPLETE, INFO))
                //.process(AUDIT_PROCESSOR)
                .process(FILEDROP_RESPOND)
                .convertBodyTo(ByteBuf.class);
                
        from("direct:errorResponse")
                .routeId("direct:errorResponse")
                .process(new ExceptionHandler()).id("processExceptionHandler")
                .process(hnSecureAddLeadingBytes).id("processHnSecureAddLeadingBytes")
                //.process(new RTransAuditProcessorConfig(ERROR, ERROR))
                //.process(AUDIT_PROCESSOR)
                .process(FILEDROP_ERROR)
                .convertBodyTo(ByteBuf.class);
        
    }

}
