package ca.bc.gov.moh.rtrans.service.version;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * VersionRoute class is added to provide the version information of deployed artifacts
 * This information can be retrieved from URL: http://<hostname>:<port>/version
 * 
 */
public class VersionRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
        from("jetty:http://{{minaListenerURI}}:{{port}}/version").routeId("rtrans-version")
    	.log("RTrans received a request for version information")
    	.to("log:HttpLogger?level=DEBUG&showBody=true&multiline=true")
    	.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
    	.process(new PopulateVersionInformation())
    	.log("RTrans sent a response with version information")
    	;

	}

}
