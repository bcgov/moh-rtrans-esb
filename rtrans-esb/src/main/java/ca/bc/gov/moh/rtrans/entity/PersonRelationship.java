package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * a relationship between two people, and the nature (type) of relationship.
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public class PersonRelationship extends Attribute implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;
    
    /**
     * @return the relationshipHolder
     */
    public Person getRelationshipHolder() {
        return relationshipHolder;
    }

    /**
     * @param relationshipHolder the relationshipHolder to set
     */
    public void setRelationshipHolder(Person relationshipHolder) {
        this.relationshipHolder = relationshipHolder;
    }

    /**
     * @return the type
     */
    public PersonRelationshipTypes getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(PersonRelationshipTypes type) {
        this.type = type;
    }

	/**
	 * @author patrick.weckermann
	 * @version 1.0
	 * @created 26-Aug-2014 2:49:34 PM
	 */
	public enum PersonRelationshipTypes {
		/**
		 * <remarks/>
		 */
		NATURAL_CHILD,
		/**
		 * <remarks/>
		 */
		NATURAL_MOTHER
	}

	private Person relationshipHolder;
	private PersonRelationshipTypes type;

	public PersonRelationship(){

	}
}//end PersonRelationship