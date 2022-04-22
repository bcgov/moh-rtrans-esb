/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.hl7.v3.HCIMINFindCandidatesResponse;

/**
 *
 * @author trevor.schiavone
 */
public class FindCandidatesUtils {
    
    public static HCIMINFindCandidatesResponse createValidV3FindCandidatesResponse() throws JAXBException {
        HCIMINFindCandidatesResponse jaxb;
        JAXBContext jaxbContext = JAXBContext.newInstance(HCIMINFindCandidatesResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxb = (HCIMINFindCandidatesResponse) jaxbUnmarshaller.unmarshal(getSuccessfulXmlResponse());
        return jaxb;
    }
    
    /**
     * 
     * @param fcr FindCandidatesResponse returned as InputStream
     * @return FindCandidatesResponse object unmarshalled from XML
     * @throws JAXBException 
     */
    public static HCIMINFindCandidatesResponse createV3FindCandidatesResponse(InputStream fcr) throws JAXBException{
        HCIMINFindCandidatesResponse jaxb;
        JAXBContext jaxbContext = JAXBContext.newInstance(HCIMINFindCandidatesResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxb = (HCIMINFindCandidatesResponse) jaxbUnmarshaller.unmarshal(fcr);
        return jaxb;
    }
    
    public static InputStream getSuccessfulXmlResponse(){
        return FindCandidatesUtils.class.getClassLoader().getResourceAsStream("HCIM_IN_FindCandidates_1.xml");
    }
    
    public static InputStream getMasksAndNullsXmlResponse(){
        return FindCandidatesUtils.class.getClassLoader().getResourceAsStream("HCIM_IN_FindCandResp_MasksAndNulls.xml");
    }
    
    
    public static InputStream getConfidentialityMaskXmlResponse(){
        return FindCandidatesUtils.class.getClassLoader().getResourceAsStream("HCIM_IN_RevisePersonResponseConfidentialityMasks.xml");
    }
    
    public static String getSuccessfulRequest(){
        return "00000181MSH|^~\\&|HNWEB|BC01000106|RAIPRSN-NM-SRCH|BC00002041|20170530161528|GARY.DONOGHUE|R09|20170530161528|D|2.4\r"
                    +"ZHD|20170530161528|^^00000010|TRAININGAdmin||||2.4\r"
                    +"QRD|||||||^RD||PSN\r"
                    +"PID||^^^BC^PH|||||19780924|M\r"
                    +"ZIA|||||||||||||||branton^eward^james";

    }
    
    public static String getConfidentialityMaskedAddressRequest(){
        return "00000181MSH|^~\\&|HNClient|BC01000088|RAIPRSN-NM-SRCH|BC00001000|20170530161528|DAVID.ANDERSON|R09|20170530161528|D|2.4||\n" +
                    "ZHD|20170530161528|^^00000010|TRAININGAdmin||||2.4\n" +
                    "QRD|||||||35^RD||PSN\n" +
                    "PID||^^^BC^PH|||||19990702|F\n" +
                    "ZIA|||||||||||||||Mauldin^^";
    }
    
    /**
     * MSH.8 MessageUser ID exceeds the 20 character limit
     * @return 
     */
    public static String getInvalidMessageUserIDRequest(){
        return "00000181MSH|^~\\&|HNWEB|BC01000106|RAIPRSN-NM-SRCH|BC00002041|20170530161528|GARY.DONOGHUE-LIBERTY|R09|20170530161528|D|2.4\r"
                    +"ZHD|20170530161528|^^00000010|TRAININGAdmin||||2.4\r"
                    +"QRD|||||||^RD||PSN\r"
                    +"PID||^^^BC^PH|||||19780924|M\r"
                    +"ZIA|||||||||||||||branton^eward^james";

    }
    
    public static String getSuccessfulResponse(){

        return "00000590MSH|^~\\&|RAIPRSN-NM-SRCH|BC00002041|HNWEB|BC01000106|20170621115133|GARY.DONOGHUE|R09|6018a3e4-7175-4b64-9012-c680102807ae|D|2.4\r"
                +"MSA|AA|20170530161528|HJMB001ISUCCESSFULLY COMPLETED\r"
                +"ERR|^^^HJMB001I&SUCCESSFULLY COMPLETED\r"
                +"ZTL|2^RD\r"
                +"PID|001|9863508351^^^BC^PH^MOH|||||19780924|M|||||||||||||||||||||20110906\r"
                +"ZIA|||||||||||||||BRANTON-JAMES-HOWARD^EDWARD-BARNESIU^TIMOTHY-JAMES-B^JEROME-HENRI-MA^^^L|||1102 HILDA ST VICTORIA BC V8V2Z3||||001\r"
                +"PID|002|9890949446^^^BC^PH^MOH|||||19280124|M\r"
                +"ZIA|||||||||||||||Dekeyzer^Eward^Jimmy^Jr^^^L|||SKINNER HOLLOW ROAD blah blah blah blah this is to||||002\r"; //the last \r is veri important otherwise it will cause test fail.
    }
    
    public static String getConfidentialityMaskResponse(){
        return "00000333MSH|^~\\&|RAIPRSN-NM-SRCH|BC00001000|HNClient|BC01000088|20171030133338|DAVID.ANDERSON|R09|4076dbc2-1200-43e2-bfbd-a12eb7b47e10|D|2.4\r" +
                "MSA|AA|20170530161528|HJMB001ISUCCESSFULLY COMPLETED\r" +
                "ERR|^^^HJMB001I&SUCCESSFULLY COMPLETED\r" +
                "ZTL|1^RD\r" +
                "PID|001|1234567890^^^BC^PH^MOH||||||U\r" +
                "ZIA|||||||||||||||MAULDIN^JOETTA^LOLITA^ORALIA^^^L|||||||001\r";
    }
    
}
