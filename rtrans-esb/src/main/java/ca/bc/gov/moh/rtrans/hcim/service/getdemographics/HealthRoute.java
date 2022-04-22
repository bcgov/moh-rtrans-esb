package ca.bc.gov.moh.rtrans.hcim.service.getdemographics;

import java.util.Properties;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * A health check route. Can be disabled by setting configuration property 
 * healthCheck=false.
 * 
 * Checks that all HCIM endpoints are reachable with the current SSL and URI 
 * configuration.
 *
 * @author David Sharpe (david.a.sharpe@cgi.com)
 */
public class HealthRoute extends RouteBuilder {

    private String hcimGetDemographicsEndPointURI;
    private String hcimFindCandidatesEndPointURI;
    private String hcimRevisePersonEndPointURI;
    private boolean healthCheck;
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HealthRoute.class);

    @Override
    public void configure() throws Exception {

        Properties appProperties = new Properties();
        appProperties.load(HealthRoute.class.getClassLoader().getResourceAsStream("responseBlacklist.properties"));
        
        hcimGetDemographicsEndPointURI = appProperties.getProperty("hcimGetDemographicsEndPointURI");
        hcimFindCandidatesEndPointURI = appProperties.getProperty("hcimFindCandidatesEndPointURI");
        hcimRevisePersonEndPointURI = appProperties.getProperty("hcimRevisePersonEndPointURI");
        healthCheck = Boolean.parseBoolean(appProperties.getProperty("healthCheck"));
        
        if (!healthCheck) {
            return;
        }

        hcimGetDemographicsEndPointURI = hcimGetDemographicsEndPointURI.replaceFirst("https://", "");
        hcimFindCandidatesEndPointURI = hcimFindCandidatesEndPointURI.replaceFirst("https://", "");
        hcimRevisePersonEndPointURI = hcimRevisePersonEndPointURI.replaceFirst("https://", "");

        restConfiguration()
                .component("servlet");

        rest("/health").get().produces(MediaType.APPLICATION_JSON_VALUE).route()
                .to("http4://" + hcimGetDemographicsEndPointURI + "?bridgeEndpoint=true&sslContextParameters=#sslParameters&throwExceptionOnFailure=false")
                .process(new CacheHttpResponseCode("getDemographicsRoute"))
                .to("http4://" + hcimFindCandidatesEndPointURI + "?bridgeEndpoint=true&sslContextParameters=#sslParameters&throwExceptionOnFailure=false")
                .process(new CacheHttpResponseCode("findCandidatesRoute"))
                .to("http4://" + hcimRevisePersonEndPointURI + "?bridgeEndpoint=true&sslContextParameters=#sslParameters&throwExceptionOnFailure=false")
                .process(new CacheHttpResponseCode("revisePersonRoute"))
                .process(
                        new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Boolean getDemographicsRoute = exchange.getProperty("getDemographicsRoute", Boolean.class);
                        Boolean findCandidatesRoute = exchange.getProperty("findCandidatesRoute", Boolean.class);
                        Boolean revisePersonRoute = exchange.getProperty("revisePersonRoute", Boolean.class);
                        exchange.getOut().setBody(
                                String.format("{\"getDemographicsRoute\": %s, \"findCandidatesRoute\": %s, \"revisePersonRoute\": %s}",
                                        getDemographicsRoute, findCandidatesRoute, revisePersonRoute));
                    }
                });

    }

    private static class CacheHttpResponseCode implements Processor {

        private final String name;

        public CacheHttpResponseCode(String name) {
            this.name = name;
        }

        @Override
        public void process(Exchange exchange) throws Exception {
            int responseCode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
            String body = exchange.getIn().getBody(String.class);
            logger.debug(body);
            if (responseCode == 200) {
                exchange.setProperty(name, true);
            } else {
                exchange.setProperty(name, false);
            }
        }
    }

}
