/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.esb.util.audit.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author conrad.gustafson
 */
@Entity
@Table(name = "AFFECTED_PARTY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AffectedParty.findAll", query = "SELECT a FROM AffectedParty a"),
    @NamedQuery(name = "AffectedParty.findByAffectedPartyId", query = "SELECT a FROM AffectedParty a WHERE a.affectedPartyId = :affectedPartyId"),
    @NamedQuery(name = "AffectedParty.findByIdentifier", query = "SELECT a FROM AffectedParty a WHERE a.identifier = :identifier"),
    @NamedQuery(name = "AffectedParty.findByIdentifierSource", query = "SELECT a FROM AffectedParty a WHERE a.identifierSource = :identifierSource"),
    @NamedQuery(name = "AffectedParty.findByIdentifierType", query = "SELECT a FROM AffectedParty a WHERE a.identifierType = :identifierType")})
public class AffectedParty implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "AFFECTED_PARTY_ID")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="Affctd_Prty_Affctd_Prty_ID_seq")
    @SequenceGenerator(name="Affctd_Prty_Affctd_Prty_ID_seq", sequenceName="Affctd_Prty_Affctd_Prty_ID_seq")
    private Integer affectedPartyId;
    @Size(max = 50)
    @Column(name = "IDENTIFIER")
    private String identifier;
    @Size(max = 50)
    @Column(name = "IDENTIFIER_SOURCE")
    private String identifierSource;
    @Size(max = 50)
    @Column(name = "IDENTIFIER_TYPE")
    private String identifierType;
    @Size(max = 50)
    @Column(name = "STATUS")
    private String status;    
    @Size(min = 1, max = 36)
    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    public AffectedParty() {
    }

    public AffectedParty(Integer affectedPartyId) {
        this.affectedPartyId = affectedPartyId;
    }

    public Integer getAffectedPartyId() {
        return affectedPartyId;
    }

    public void setAffectedPartyId(Integer affectedPartyId) {
        this.affectedPartyId = affectedPartyId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierSource() {
        return identifierSource;
    }

    public void setIdentifierSource(String identifierSource) {
        this.identifierSource = identifierSource;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (affectedPartyId != null ? affectedPartyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AffectedParty)) {
            return false;
        }
        AffectedParty other = (AffectedParty) object;
        if ((this.affectedPartyId == null && other.affectedPartyId != null) || (this.affectedPartyId != null && !this.affectedPartyId.equals(other.affectedPartyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.bc.gov.moh.esb.util.audit.entity.AffectedParty[ affectedPartyId=" + affectedPartyId + " ]";
    }
    
}
