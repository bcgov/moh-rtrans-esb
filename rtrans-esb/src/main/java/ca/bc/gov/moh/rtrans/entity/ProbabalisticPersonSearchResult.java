package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * a single search result as part of a probabilistic person search.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public class ProbabalisticPersonSearchResult implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private float matchScore;
    private MatchTypes matchType;
    private Person person = new Person();

    public ProbabalisticPersonSearchResult() {

    }

    /**
     * @return the matchScore
     */
    public float getMatchScore() {
        return matchScore;
    }

    /**
     * @param matchScore the matchScore to set
     */
    public void setMatchScore(float matchScore) {
        this.matchScore = matchScore;
    }

    /**
     * @return the matchType
     */
    public MatchTypes getMatchType() {
        return matchType;
    }

    /**
     * @param matchType the matchType to set
     */
    public void setMatchType(MatchTypes matchType) {
        this.matchType = matchType;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }
}//end ProbabalisticPersonSearchResult
