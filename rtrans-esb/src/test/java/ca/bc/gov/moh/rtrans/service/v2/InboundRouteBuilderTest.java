/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates.FindCandidatesUtils;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics.GetDemoUtils;
import ca.bc.gov.moh.rtrans.hcim.service.findcandidates.FindCandidatesRoute;
import ca.bc.gov.moh.rtrans.hcim.service.getdemographics.GetDemographicsRoute;
import ca.bc.gov.moh.rtrans.hcim.service.reviseperson.RevisePersonRoute;
import ca.bc.gov.moh.rtrans.service.audit.AuditProcessorMock;
import ca.bc.gov.moh.rtrans.service.audit.RTransAuditProcessor;
import ca.bc.gov.moh.rtrans.service.audit.RTransFileDropProcessorMock;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Properties;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ConvertBodyDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Testing the InboundRouteBuilder class Extending CamelTestSupport is necessary
 * to test Camel Routes
 *
 * @author killian.faussart
 */
public class InboundRouteBuilderTest extends CamelTestSupport {

    @Produce("direct:start")
    private ProducerTemplate mockRouteStart;

    @EndpointInject("mock:response")
    private MockEndpoint responseEndpoint;

    // Necessary to indicate Camel that we're going to use AdviceWith in the unit test
    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    private boolean isConfigured = false;
    private boolean testTimeout = false;

    private MockHCIM mockHCIM = new MockHCIM();
    private MockHCIMRevisePerson mockHCIMRP = new MockHCIMRevisePerson();

    private static final HashMap<String, String> CERT_ORGID_TO_SSLID = new HashMap<>();

    static {
        CERT_ORGID_TO_SSLID.put("VIHA", "VIHA_MAP_VALUE");
    }

    /**
     * Creates a dummy version of the appProperties JNDI attribute used by
     * routes to obtain file paths and URI settings.
     *
     * @return Properties
     */
    private Properties createAppProperties() {
        Properties appProperties = new Properties();
        appProperties.setProperty("fileDropPath", "");
        appProperties.setProperty("hcimGetDemographicsEndPointURI", "");
        appProperties.setProperty("hcimRevisePersonEndPointURI", "");
        appProperties.setProperty("hcimFindCandidatesEndPointURI", "");
        appProperties.setProperty("hcimFindCandidatesTimeout", "30000");
        appProperties.setProperty("hcimGetDemographicsTimeout", "30000");
        appProperties.setProperty("minaListenerURI", "");

        return appProperties;
    }

    /**
     * Replaces the audit and file drop processors with dummy equivalents.
     */
    private void configureAuditProcessors() {
        InboundRouteBuilder.AUDIT_PROCESSOR = new RTransAuditProcessor(new AuditProcessorMock());

        InboundRouteBuilder.FILEDROP_ERROR = new RTransFileDropProcessorMock();
        InboundRouteBuilder.FILEDROP_ESB_IN = new RTransFileDropProcessorMock();
        InboundRouteBuilder.FILEDROP_ESB_OUT = new RTransFileDropProcessorMock();
        InboundRouteBuilder.FILEDROP_HCIM_IN = new RTransFileDropProcessorMock();
        InboundRouteBuilder.FILEDROP_HCIM_OUT = new RTransFileDropProcessorMock();
        InboundRouteBuilder.FILEDROP_RECEIVE = new RTransFileDropProcessorMock();
        InboundRouteBuilder.FILEDROP_RESPOND = new RTransFileDropProcessorMock();
    }

    // We indicate the Route that we want to test and configure the application properties
    @Override
    protected RouteBuilder createRouteBuilder() {
        InboundRouteBuilder.setAppProperties(createAppProperties());
        configureAuditProcessors();
        return new InboundRouteBuilder();
    }

    /**
     * Class only used to simulate the HCIM response
     */
    private class MockHCIM implements Processor {

        InputStream xmlResponse;

        public MockHCIM() {
        }

        public MockHCIM(InputStream xmlResponse) {
            this.xmlResponse = xmlResponse;
        }

        @Override
        public void process(Exchange exchange) throws Exception {
            if (testTimeout) {
                throw new SocketTimeoutException("Read timed out"); //This is the exception thrown when a timeout occurs
            }
            exchange.getIn().setBody(xmlResponse); //We set the response here
        }
    }

    /**
     * Class only used to simulate the HCIM response we need a second one
     * because RP hits HCIM twice
     */
    private class MockHCIMRevisePerson implements Processor {

        InputStream xmlResponseRP;

        public MockHCIMRevisePerson() {
        }

        public MockHCIMRevisePerson(InputStream xmlResponse) {
            this.xmlResponseRP = xmlResponse;
        }

        @Override
        public void process(Exchange exchange) throws Exception {
            if (testTimeout) {
                throw new SocketTimeoutException("Read timed out"); //This is the exception thrown when a timeout occurs
            }
            exchange.getIn().setBody(xmlResponseRP); //We set the response here
        }
    }

    /**
     * We configure the different routes by adding the different pieces of
     * advice. Each route is retrieved with its ID.
     *
     * Note: For now the "messageDrop" and "audit" components are replaced by
     * mocks as we cannot use the database nor the file system Note:
     * WeaveByToString is not working in a choice() that is why we set the ID in
     * the InBoundRouteBuilder for audit and messagedrop
     *
     * @throws Exception
     */
    @Before
    public void configureRoutes() throws Exception {

        if (isConfigured) {
            return;
        }

        isConfigured = true;
        context.addRoutes(new RevisePersonRoute());
        context.addRoutes(new GetDemographicsRoute());
        context.addRoutes(new FindCandidatesRoute());

        //We configure the Main Route in InboundRouteBuilder     
        AdviceWithRouteBuilder.adviceWith(context, "netty4:tcp", a -> {
            a.replaceFromWith("direct:start"); //We replace the starting point
            a.weaveByType(ConvertBodyDefinition.class).after().to("mock:convertBody");
            a.weaveAddLast().to("mock:response"); //Last step of the main route
        });

        //We configure the Error Route in InboundRouteBuilder     
        AdviceWithRouteBuilder.adviceWith(context, "direct:errorResponse", a -> {
            a.weaveById("processExceptionHandler").after().to("mock:exception");
            a.weaveByType(ConvertBodyDefinition.class).before().to("mock:response");
        });

        //We configure the Main Route in GetDemographicsRoute
        AdviceWithRouteBuilder.adviceWith(context, "direct:getDemographics", a -> {
            a.weaveByType(ConvertBodyDefinition.class).after().to("mock:convertBody");

            a.weaveById(GetDemographicsRoute.NODE_HCIM_GET_DEMO_ENDPOINT).replace().process(mockHCIM); //We mock the HCIM response
        });

        //We configure the Main Route in FindCandidatesRoute
        AdviceWithRouteBuilder.adviceWith(context, "direct:findCandidates", a -> {
            a.weaveByType(ConvertBodyDefinition.class).after().to("mock:convertBody");

            a.weaveById(FindCandidatesRoute.NODE_HCIM_FIND_CANDIDATES_ENDPOINT).replace().process(mockHCIM); //We mock the HCIM response
        });

        //Configure the main Route in RevisePersonRoute
        AdviceWithRouteBuilder.adviceWith(context, "direct:revisePerson", a -> {
            a.weaveByType(ConvertBodyDefinition.class).after().to("mock:convertBody");

            a.weaveById(RevisePersonRoute.NODE_HCIM_REVISE_PERSON_ENDPOINT).replace().process(mockHCIMRP); //We mock the HCIM response
        });
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteForR03Successful() throws Exception {

        //We start the Camel context
        context.start();

        mockHCIM.xmlResponse = GetDemoUtils.getSuccessfulXmlResponse();
        //We set our expectations
        responseEndpoint.expectedBodiesReceived(GetDemoUtils.getSuccessfulResponse());
        getMockEndpoint("mock:convertBody").expectedMessageCount(5);

        //We send the message
        mockRouteStart.sendBody(GetDemoUtils.getSuccessfulRequest());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteForR03InvalidPID() throws Exception {

        //We start the Camel context
        context.start();

        //We set our expectations
        responseEndpoint.expectedMessagesMatches(new SevereSystemErrorPredicate());
        getMockEndpoint("mock:convertBody").expectedMessageCount(0);

        //We send the message
        mockRouteStart.sendBody(GetDemoUtils.getInvalidPID());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteForR03Masked() throws Exception {

        //We start the Camel context
        context.start();

        mockHCIM.xmlResponse = GetDemoUtils.getMaskedXmlResponse();
        //We set our expectations
        responseEndpoint.expectedBodiesReceived(GetDemoUtils.getSuccessfulResponseMaskedBirthdate());
        getMockEndpoint("mock:convertBody").expectedMessageCount(5);

        //We send the message
        mockRouteStart.sendBody(GetDemoUtils.getSuccessfulRequest());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteForR03TrimCityandAddress() throws Exception {

        //We start the Camel context
        context.start();
        // use a different response to test a different set of less than ideal cases,
        // but use the same request
        mockHCIM.xmlResponse = GetDemoUtils.getAlternateSuccessfulXmlResponse();
        //We set our expectations
        responseEndpoint.expectedBodiesReceived(GetDemoUtils.getAlternateSuccessfulResponse());
        getMockEndpoint("mock:convertBody").expectedMessageCount(5);

        //We send the message
        mockRouteStart.sendBody(GetDemoUtils.getSuccessfulRequest());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteForR09Successful() throws Exception {

        //We start the Camel context
        context.start();

        mockHCIM.xmlResponse = FindCandidatesUtils.getSuccessfulXmlResponse();
        //We set our expectations
        responseEndpoint.expectedBodiesReceived(FindCandidatesUtils.getSuccessfulResponse());
        getMockEndpoint("mock:convertBody").expectedMessageCount(5);

        //We send the message
        mockRouteStart.sendBody(FindCandidatesUtils.getSuccessfulRequest());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteForR09ConfidentialityMasks() throws Exception {

        //We start the Camel context
        context.start();

        mockHCIM.xmlResponse = FindCandidatesUtils.getConfidentialityMaskXmlResponse();
        //We set our expectations
        responseEndpoint.expectedBodiesReceived(FindCandidatesUtils.getConfidentialityMaskResponse());
        getMockEndpoint("mock:convertBody").expectedMessageCount(5);

        //We send the message
        mockRouteStart.sendBody(FindCandidatesUtils.getConfidentialityMaskedAddressRequest());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteForR09InvalidMessageUserID() throws Exception {

        //We start the Camel context
        context.start();

        //We set our expectations
        responseEndpoint.expectedMessageCount(1);
        getMockEndpoint("mock:convertBody").expectedMessageCount(0);

        //We send the message
        mockRouteStart.sendBody(FindCandidatesUtils.getInvalidMessageUserIDRequest());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteUnhandledMessage() throws Exception {

        //We start the Camel context
        context.start();

        //We set our expectations
        responseEndpoint.expectedMessageCount(1);
        getMockEndpoint("mock:convertBody").expectedMessageCount(0);
        getMockEndpoint("mock:exception").expectedMessageCount(1);

        //We send the message
        mockRouteStart.sendBody(GetDemoUtils.getUnhandledMessage());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteInvalidR03() throws Exception {

        //We start the Camel context
        context.start();

        //We set our expectations
        responseEndpoint.expectedMessageCount(1);
        getMockEndpoint("mock:convertBody").expectedMessageCount(0);

        //We send the message
        mockRouteStart.sendBody(GetDemoUtils.getInvalidR03());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        //We stop the Camel context
        context.stop();
    }

    @Test
    @Ignore // type converter not working 
    public void testInboundRouteTimeout() throws Exception {

        //We start the Camel context
        context.start();

        testTimeout = true;

        //We set our expectations
        responseEndpoint.expectedMessageCount(1);
        getMockEndpoint("mock:exception").expectedMessageCount(1);
        getMockEndpoint("mock:convertBody").expectedMessageCount(2);

        //We send the message
        mockRouteStart.sendBody(FindCandidatesUtils.getSuccessfulRequest());

        //We verify our expectations were met
        assertMockEndpointsSatisfied();

        testTimeout = false;

        //We stop the Camel context
        context.stop();
    }

    private class SevereSystemErrorPredicate implements Predicate {

        @Override
        public boolean matches(Exchange exchange) {
            return this.check(exchange);
        }

        public boolean check(Exchange exchange) {
            String message = (String) exchange.getIn().getBody();
            return message.contains("NHR509E^SEVERE SYSTEM ERROR");
        }

    }
}
