/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.esb.util.audit;

/**
 *
 * @author conrad.gustafson
 */
public interface AuditableResponseMessage {
    public String getResponseCode();
    public String getResponseText();
}
