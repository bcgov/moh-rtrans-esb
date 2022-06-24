package ca.bc.gov.moh.rtrans;

import org.apache.camel.main.Main;
import ca.bc.gov.moh.rtrans.service.v2.InboundRouteBuilder;
import ca.bc.gov.moh.rtrans.hcim.service.findcandidates.FindCandidatesRoute;
import ca.bc.gov.moh.rtrans.hcim.service.getdemographics.GetDemographicsRoute;
import ca.bc.gov.moh.rtrans.hcim.service.reviseperson.RevisePersonRoute;

/**
 *
 * @author trevor.schiavone
 */
public class RTransMainMethod {
    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.setPropertyPlaceholderLocations("file:${env:RTRANS_HOME}/properties/application-external.properties;optional=true,"
                +"classpath:application.properties,"
                + "classpath:errorMap.properties,"
                + "classpath:haValueMapConfig.properties");
        main.configure().addRoutesBuilder(InboundRouteBuilder.class);
        main.configure().addRoutesBuilder(FindCandidatesRoute.class);
        main.configure().addRoutesBuilder(GetDemographicsRoute.class);
        main.configure().addRoutesBuilder(RevisePersonRoute.class);

        main.run(args);
    }
}
