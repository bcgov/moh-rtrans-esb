package ca.bc.gov.moh.rtrans.entity;

import java.io.Serializable;

/**
 * User of a system. This stores various information about the user and is
 * typically used for authorization, and auditability.
 *
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private String userId;
    
    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}//end User
