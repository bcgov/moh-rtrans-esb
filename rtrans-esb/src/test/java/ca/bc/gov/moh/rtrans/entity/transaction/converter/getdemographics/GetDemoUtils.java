/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.getdemographics;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.hl7.v3.HCIMINGetDemographicsResponse;

/**
 *
 * @author trevor.schiavone
 */
public class GetDemoUtils {

    public static HCIMINGetDemographicsResponse createValidV3GetDemoResponse() throws JAXBException {
        HCIMINGetDemographicsResponse jaxb;
        JAXBContext jaxbContext = JAXBContext.newInstance(HCIMINGetDemographicsResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxb = (HCIMINGetDemographicsResponse) jaxbUnmarshaller.unmarshal(getSuccessfulXmlResponse());
        return jaxb;
    }

    public static HCIMINGetDemographicsResponse createValidV3NoResultsGetDemoResponse() throws JAXBException {
        HCIMINGetDemographicsResponse jaxb;
        JAXBContext jaxbContext = JAXBContext.newInstance(HCIMINGetDemographicsResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxb = (HCIMINGetDemographicsResponse) jaxbUnmarshaller.unmarshal(getDemoNoResultsResponse());
        return jaxb;
    }
    
     /**
     * 
     * @param fcr FindCandidatesResponse returned as InputStream
     * @return FindCandidatesResponse object unmarshalled from XML
     * @throws JAXBException 
     */
    public static HCIMINGetDemographicsResponse createValidV3GetDemoResponse(InputStream fcr) throws JAXBException{
        HCIMINGetDemographicsResponse jaxb;
        JAXBContext jaxbContext = JAXBContext.newInstance(HCIMINGetDemographicsResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxb = (HCIMINGetDemographicsResponse) jaxbUnmarshaller.unmarshal(fcr);
        return jaxb;
    }

    public static InputStream getSuccessfulXmlResponse() {
        return GetDemoUtils.class.getClassLoader().getResourceAsStream("HCIM_IN_GetDemographicsResponse_1.xml");
    }
    
    public static InputStream getNoHomePhoneResponse() {
        return GetDemoUtils.class.getClassLoader().getResourceAsStream("GetDemoResponse_NoHomePhone.xml");
    }

    public static InputStream getMaskedXmlResponse() {
        return GetDemoUtils.class.getClassLoader().getResourceAsStream("GetDemographicsResponse_Masked.xml");
    }

    public static InputStream getMergedPersonXmlResponse() {
        return GetDemoUtils.class.getClassLoader().getResourceAsStream("HCIM_IN_GetDemographicsResponse_MergedPerson.xml");
    }

    public static InputStream getAlternateSuccessfulXmlResponse() {
        return GetDemoUtils.class.getClassLoader().getResourceAsStream("HCIM_IN_GetDemographicsResponse_2.xml");
    }

    public static InputStream getDemoNoResultsResponse() {
        return GetDemoUtils.class.getClassLoader().getResourceAsStream("GetDemographicsResponseNoResults.xml");
    }

    public static String getSuccessfulRequest() {
        return "00000181MSH|^~\\&|HNWEB|VIHA|RAIGT-PRSN-DMGR|BC00001013|20170125122125|train96|R03|20170125122125|D|2.4||^M\r"
                + "ZHD|20170125122125|^^00000010|HNAIADMINISTRATION||||2.4^M\r"
                + "PID||1234567890^^^BC^PH";
    }

    public static String getInvalidPID() {
        return "00000181MSH|^~\\&|HNWEB|VIHA|RAIGT-PRSN-DMGR|BC00001013|20170125122125|train96|R03|20170125122125|D|2.4||^M\r"
                + "ZHD|20170125122125|^^00000010|HNAIADMINISTRATION||||2.4^M\r"
                + "PID1234567890^^^BC^PH";
    }

    public static String getSuccessfulResponse() {
        return "00000462MSH|^~\\&|RAIGT-PRSN-DMGR|BC00001013|HNWEB|VIHA|20170125122125|train96|R03|b3a12f0c-a332-4ec9-94b9-d8539a02df48|D|2.4\r"
                + "MSA|AA|20170125122125|HJMB001ISUCCESSFULLY COMPLETED\r"
                + "ERR|^^^HJMB001I&SUCCESSFULLY COMPLETED\r"
                + "PID||1234567890^^^BC^PH|||||19721016|F\r"
                + "ZIA|||||||||||||||PNEUMONOULTRAMICROSCOPICSILICOVOLCA^JEAN-RENE-BAPTI^DAWN-SHAWN-BROW^^^ANGELICA-MONTAN^L|321 SUNSHINE PL^NEW LINE 2^NEW LINE 3^^^^^^^^^^^^^^^^^VANCOUVER^BC^V9L 4H2^CAN^H^^^^Y|^PRN^PH^^^604^8883322\r";
    }

    public static String getSuccessfulResponseMaskedBirthdate() {
        return "00000392MSH|^~\\&|RAIGT-PRSN-DMGR|BC00001013|HNWEB|VIHA|20171204135349|train96|R03|2c7b7c5c-ee6a-4c9d-8b76-58f7662fd63c|D|2.4\r"
                + "MSA|AA|20170125122125|HJMB001ISUCCESSFULLY COMPLETED\r"
                + "ERR|^^^HJMB001I&SUCCESSFULLY COMPLETED\r"
                + "PID||1234567890^^^BC^PH|||||20000411|U\r"
                + "ZIA|||||||||||||||GUNTHER^ANTON^BRYON^^^HIRAM^L|827 Critchfield Arcade^^^^^^^^^^^^^^^^^^^Westbridge^BC^V6T 7N4^CAN^H^^^^N|^PRN^PH^^^480^2839783\r";
    }

    public static String getAlternateSuccessfulResponse() {
        return "00000518MSH|^~\\&|RAIGT-PRSN-DMGR|BC00001013|HNWEB|VIHA|20170125122125|train96|R03|b3a12f0c-a332-4ec9-94b9-d8539a02df48|D|2.4\r"
                + "MSA|AA|20170125122125|HJMB001ISUCCESSFULLY COMPLETED\r"
                + "ERR|^^^HJMB001I&SUCCESSFULLY COMPLETED\r"
                + "PID||1234567890^^^BC^PH|||||19721016|F\r"
                + "ZIA|||||||||||||||PNEUMONOULTRAMICROSCOPICSILICOVOLCA^JEAN-RENE-BAPTI^DAWN-SHAWN-BROW^^^ANGELICA-MONTAN^L|321 THYROPARATHYROIDECTOM^APT# 12345685321849653484^LINE 3 123456853218496534^^^^^^^^^^^^^^^^^LLANFAIRPWLLGWYNGYLLGOGER^BC^V9L 4H2^CAN^H^^^^Y|^PRN^PH^^^604^8883322\r";
    }

    public static String getGenericFailureResponse() {
        return "MSH|^~\\&|RAIGT-PRSN-DMGR|20170125122125|HNWEB|VIHA|20170629095913.933-0700||ACK|8502|2.4|2.4"
                + "MSA|AE|D|IllegalArgumentException: Incoming HL7 type not handled\n";
    }

    //Header missing the Receiving Facility ID - Will throw an UnhandledHL7v2MessageException
    public static String getUnhandledMessage() {
        return "00000181MSH|^~\\&|HNWEB|VIHA|RAIGT-PRSN-DMGR|20170125122125|train96|R03|20170125122125|D|2.4||^M\r"
                + "ZHD|20170125122125|^^00000010|HNAIADMINISTRATION||||2.4^M\r"
                + "PID||1234567890^^^BC^PH";
    }

    //Sending facility
    public static String getInvalidR03() {
        return "00000181MSH|^~\\&|HNWEB|VIHA|RAIGT-PRSN-DMGR|BC00001013|20170125122125|train96|R03|20170125122125|D|2.4||^M\r"
                + "ZHV|20170125122125|^^00000010|HNAIADMINISTRATION||||2.4^M\r"
                + "PID||1234567890^^^BC^PH";
    }

}
