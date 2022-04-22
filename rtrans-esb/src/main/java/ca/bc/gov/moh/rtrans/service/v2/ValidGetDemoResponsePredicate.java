/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.transaction.GetDemographicsResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

/**
 *
 * @author trevor.schiavone
 */
public class ValidGetDemoResponsePredicate implements Predicate {
    
    @Override
    public boolean matches(Exchange exchange) {
         
        GetDemographicsResponse demographicsResponse = exchange.getIn().getBody(GetDemographicsResponse.class);
        Person searchResult = demographicsResponse.getSearchResult();
        String responseCode = demographicsResponse.getSystemResponse().get(0).getResponseCode();
        return searchResult != null 
                && searchResult.getName() != null
                && searchResult.getGender() != null
                && searchResult.getBirthDate() != null
                && !responseCode.equals("BCHCIM.GD.2.0018");
    }
    
}
