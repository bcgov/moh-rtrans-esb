package ca.bc.gov.moh.rtrans.entity;

import org.apache.commons.lang.StringUtils;

/**
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:34 PM
 */
public enum ResponseTypes {

    ERROR,
    WARNING,
    REJECT,
    INFORMATION;

    public static ResponseTypes fromString(String encode) {

        if (encode == null || StringUtils.isEmpty(encode)) {
            return null;
        }

        ResponseTypes value = null;

        switch (encode) {
            case "ERROR":
                value = ERROR;
            case "WARNING":
                value = WARNING;
            case "REJECT":
                value = REJECT;
            case "INFORMATION":
                value = INFORMATION;
            case "E":
                value = ERROR;
        }

        return value;
    }
}
