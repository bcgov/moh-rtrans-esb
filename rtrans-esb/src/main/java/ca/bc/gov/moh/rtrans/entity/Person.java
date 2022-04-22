package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This entity represents a person and contains that persons associated
 * demographics and identity data.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:33 PM
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private List<AddressAttribute> address = new ArrayList<>();

    private DateAttribute birthDate;
    private IntegerAttribute birthOrder;
    
    private BooleanAttribute multipleBirthIndicator;

    private List<ConfidentialityMaskAttribute> confidentialityMask;

    private DateAttribute deathDate;

    private BooleanAttribute deathVerified;

    private List<EmailAttribute> email;

    private GenderAttribute gender;

    private List<IdentifierAttribute> identifier;

    private List<PersonNameAttribute> name;

    private List<PhoneAttribute> phone;

    private List<PersonRelationship> relationship;
    
    

    public Person() {

    }

    /**
     * @return the address
     */
    public List<AddressAttribute> getAddress() {
        if (address == null) {
            address = new ArrayList<>();
        }
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(List<AddressAttribute> address) {
        this.address = address;
    }

    /**
     * @return the birthDate
     */
    public DateAttribute getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(DateAttribute birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the birthOrder
     */
    public IntegerAttribute getBirthOrder() {
        return birthOrder;
    }

    /**
     * @param birthOrder the birthOrder to set
     */
    public void setBirthOrder(IntegerAttribute birthOrder) {
        this.birthOrder = birthOrder;
    }

    /**
     * @return the confidentialityMask
     */
    public List<ConfidentialityMaskAttribute> getConfidentialityMask() {
        if (confidentialityMask == null) {
            confidentialityMask = new ArrayList<>();
        }
        return confidentialityMask;
    }

    /**
     * @param confidentialityMask the confidentialityMask to set
     */
    public void setConfidentialityMask(List<ConfidentialityMaskAttribute> confidentialityMask) {
        this.confidentialityMask = confidentialityMask;
    }

    /**
     * @return the deathDate
     */
    public DateAttribute getDeathDate() {
        return deathDate;
    }

    /**
     * @param deathDate the deathDate to set
     */
    public void setDeathDate(DateAttribute deathDate) {
        this.deathDate = deathDate;
    }

    /**
     * @return the deathVerified
     */
    public BooleanAttribute getDeathVerified() {
        return deathVerified;
    }

    /**
     * @param deathVerified the deathVerified to set
     */
    public void setDeathVerified(BooleanAttribute deathVerified) {
        this.deathVerified = deathVerified;
    }

    /**
     * @return the email
     */
    public List<EmailAttribute> getEmail() {

        if (email == null) {
            email = new ArrayList<>();
        }
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(List<EmailAttribute> email) {
        this.email = email;
    }

    /**
     * @return the gender
     */
    public GenderAttribute getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(GenderAttribute gender) {
        this.gender = gender;
    }

    /**
     * @return the identifier
     */
    public List<IdentifierAttribute> getIdentifier() {
        if (identifier == null) {
            identifier = new ArrayList<>();
        }
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(List<IdentifierAttribute> identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the name
     */
    public List<PersonNameAttribute> getName() {
        if (name == null) {
            name = new ArrayList<>();
        }
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(List<PersonNameAttribute> name) {

        this.name = name;
    }

    /**
     * @return the phone
     */
    public List<PhoneAttribute> getPhone() {

        if (phone == null) {
            phone = new ArrayList<>();
        }
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(List<PhoneAttribute> phone) {
        this.phone = phone;
    }

    /**
     * @return the relationship
     */
    public List<PersonRelationship> getRelationship() {
        if (relationship == null) {
            relationship = new ArrayList<>();
        }
        return relationship;
    }

    /**
     * @param relationship the relationship to set
     */
    public void setRelationship(List<PersonRelationship> relationship) {
        this.relationship = relationship;
    }

    public List<IdentifierAttribute> getAllIdentifiers() {
        ArrayList<IdentifierAttribute> allIdentifiers = new ArrayList<>();
        if (identifier != null) {
            allIdentifiers.addAll(identifier);
        }
        if (getRelationship() != null) {
            List<PersonRelationship> relationships = getRelationship();
            for (PersonRelationship rel : relationships) {
                Person relationshipHolder = rel.getRelationshipHolder();
                if (relationshipHolder != null) {
                    allIdentifiers.addAll(relationshipHolder.getAllIdentifiers());
                }
            }
        }
        return allIdentifiers;
    }

    public boolean hasPHN() {
        for (IdentifierAttribute identifierAttribute : identifier) {
            if (IdentifierTypes.BCPHN.equals(identifierAttribute.getType())) {
                return true;
            }
        }
        return false;
    }

    public String getPHN() {
        if (identifier == null) {
            identifier = new ArrayList<>();
        }
        for (IdentifierAttribute identifierAttribute : identifier) {
            if (IdentifierTypes.BCPHN.equals(identifierAttribute.getType())) {
                return identifierAttribute.getValue();
            }
        }
        return null;
    }

    public BooleanAttribute getMultipleBirthIndicator() {
        return multipleBirthIndicator;
    }
    
    public void setMultipleBirthIndicator(BooleanAttribute multipleBirthIndicator) {
        this.multipleBirthIndicator = multipleBirthIndicator;
    }

    
    
}//end Person
