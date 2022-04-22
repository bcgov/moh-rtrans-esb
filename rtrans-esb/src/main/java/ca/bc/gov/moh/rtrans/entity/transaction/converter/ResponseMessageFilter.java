/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple filter that uses a blacklist to determine if a response message 
 * should be suppressed or not.
 * 
 * @author greg.perkins
 */
public class ResponseMessageFilter {

    /**
     * Blacklist
     */
    private static final Properties blacklist = new Properties();

    /**
     * Determines if a message should be included in a response, or not.
     * @param responseCode String - Response Code of the message
     * @return boolean - True if the message should be included
     */
    public static boolean includeMessage(String responseCode) {
        //Lazy initialize blacklist from properties file
        if (blacklist.isEmpty()) {
            try {
                blacklist.load(ResponseMessageFilter.class.getClassLoader().getResourceAsStream("responseBlacklist.properties"));
            } catch (Exception ex) {
                Logger.getLogger(ResponseMessageFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Check if the code is blacklisted
        return !(blacklist.getProperty(responseCode) != null);
    }
}
