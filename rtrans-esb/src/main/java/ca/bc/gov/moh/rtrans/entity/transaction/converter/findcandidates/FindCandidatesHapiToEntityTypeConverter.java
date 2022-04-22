/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter.findcandidates;

import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.PersonNameAttribute;
import ca.bc.gov.moh.rtrans.entity.transaction.FindCandidates;
import ca.bc.gov.moh.rtrans.entity.transaction.converter.HapiEntityConverterV24;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.R09;
import ca.bc.gov.moh.rtrans.service.v2.custommodel.message.ZIA;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v24.datatype.XPN;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.commons.lang.StringUtils;

/**
 * Find Candidate Converter converts HL7v2 to Entity.
 * @author Kuan Fan
 */
@Converter
public class FindCandidatesHapiToEntityTypeConverter {

    /**
     * Convert HL7V2 R09 message to entity c;ass FindCandidates.
     * QRD segment is present in R09 request but it isn't useful, so it is NOT being translated into v3.
     * @param hapi R09 v2
     * @param exchange the camel exchange
     * @return entity class
     * @throws HL7Exception
     * @throws ParseException 
     */
    @Converter
    public static FindCandidates convert(R09 hapi, Exchange exchange) throws HL7Exception, ParseException {

        HapiEntityConverterV24 hapiEntityConverterV24 = new HapiEntityConverterV24(exchange);

        FindCandidates findCandidates = new FindCandidates();

        if (!hapi.getMSH().isEmpty()) {
            hapiEntityConverterV24.mapMSHToRequestMessage(hapi.getMSH(), findCandidates);
        }
        
        if (!hapi.getPID().isEmpty()) {
            Person person = new Person();
            boolean mapAllNameTypes = false;
            hapiEntityConverterV24.mapPIDToPerson(hapi.getPID(), person, mapAllNameTypes);
            findCandidates.setPerson(person);
        }
        
        ZIA zia = hapi.getZIA();
        if(zia!=null && !zia.isEmpty()) {
            XPN xpn = zia.getExtendedPersonName();
            if(xpn!=null && !xpn.isEmpty()) {
                PersonNameAttribute pna = null;
                String lastName = xpn.getFamilyName().encode();
                String firstName = xpn.getGivenName().encode();
                String middleName = xpn.getSecondAndFurtherGivenNamesOrInitialsThereof().encode();
                if(StringUtils.isNotBlank(lastName) || StringUtils.isNotBlank(firstName) || StringUtils.isNotBlank(middleName)) {
                    pna = new PersonNameAttribute();
                    pna.setLastName(lastName);
                    pna.setFirstName(firstName);
                    pna.setMiddleName(middleName);
                }
                if(pna!=null) {
                    List<PersonNameAttribute> pnList = new ArrayList<>();
                    pnList.add(pna);
                    if(findCandidates.getPerson()==null) {
                        findCandidates.setPerson(new Person());
                    }
                    findCandidates.getPerson().setName(pnList);
                }
            }
        }
        
        findCandidates.setMessageBody(hapi);
        
        return findCandidates;
    }

}
