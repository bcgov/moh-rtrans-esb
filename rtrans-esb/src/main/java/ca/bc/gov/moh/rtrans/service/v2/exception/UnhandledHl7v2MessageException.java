/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.service.v2.exception;

/**
 *
 * @author Killian.Faussart
 */
public class UnhandledHl7v2MessageException extends Exception {
    
    public UnhandledHl7v2MessageException(String message){
        super(message);
    }
    
}
