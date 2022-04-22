package ca.bc.gov.moh.rtrans.entity.transaction;

import ca.bc.gov.moh.rtrans.entity.IdentifierAttribute;
import ca.bc.gov.moh.rtrans.entity.Person;
import ca.bc.gov.moh.rtrans.entity.ProbabalisticPersonSearchResult;
import ca.bc.gov.moh.rtrans.entity.ResponseMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * The response message for a probabilistic search request.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public class FindCandidatesResponse extends ResponseMessage {

    private List<ProbabalisticPersonSearchResult> searchResults = new ArrayList<ProbabalisticPersonSearchResult>();

    public FindCandidatesResponse() {
    }

    /**
     * @return the searchResults
     */
    public List<ProbabalisticPersonSearchResult> getSearchResults() {
        return searchResults;
    }

    /**
     * @param searchResults the searchResults to set
     */
    public void setSearchResults(List<ProbabalisticPersonSearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public List<IdentifierAttribute> getAuditableIdentifiers() {
        List<IdentifierAttribute> identifierList = new ArrayList<>();
        if (searchResults != null) {
            for (ProbabalisticPersonSearchResult searchResult : searchResults) {
                Person person = searchResult.getPerson();
                if (person != null) {
                    identifierList.addAll(person.getAllIdentifiers());
                }
            }
        }
        return identifierList;
    }

    
}//end ProbabalisticPersonSearchResults
