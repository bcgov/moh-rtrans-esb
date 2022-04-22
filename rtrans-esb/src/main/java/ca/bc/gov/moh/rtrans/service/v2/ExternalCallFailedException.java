/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2;

/**
 *
 * @author conrad.gustafson
 */
public class ExternalCallFailedException extends Exception {

    private static final String MESSAGE = "The external call failed to process";
    
    public ExternalCallFailedException() {
        super(MESSAGE);
    }
    
}
