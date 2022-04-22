package ca.bc.gov.moh.rtrans.entity.transaction;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.ResponseMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * The results of a deterministic search.
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:32 PM
 */
public class GetDemographicsResponse extends ResponseMessage {

	private Person searchResult = new Person();
        private String reportOfEligibilityFlag;

	public GetDemographicsResponse(){

	}

    /**
     * @return the searchResult
     */
    public Person getSearchResult() {
        return searchResult; 
   }

    /**
     * @param searchResult the searchResult to set
     */
    public void setSearchResult(Person searchResult) {
        this.searchResult = searchResult;
    }

    public String getReportOfEligibilityFlag() {
        return reportOfEligibilityFlag;
    }

    public void setReportOfEligibilityFlag(String reportOfEligibilityFlag) {
        this.reportOfEligibilityFlag = reportOfEligibilityFlag;
    }

    
    @Override
    public List<IdentifierAttribute> getAuditableIdentifiers() {
        List<IdentifierAttribute> identifierList = new ArrayList<>();
        if (this.getSearchResult() != null) {
            identifierList.addAll(this.getSearchResult().getAllIdentifiers());
        }
        return identifierList;
    }
}//end GetDemographicsResponse