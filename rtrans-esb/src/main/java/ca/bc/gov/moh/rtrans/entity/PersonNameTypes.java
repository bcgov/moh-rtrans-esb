package ca.bc.gov.moh.rtrans.entity;

/**
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:33 PM
 */
public enum PersonNameTypes {

    Declared,
    Documented;

    public String value() {
        return name();
    }

    public static PersonNameTypes fromValue(String v) {
        if (v.equalsIgnoreCase("L") || v.equalsIgnoreCase("Legal") || v.equalsIgnoreCase("Current")) {
            return Declared;
        } else if (v.equalsIgnoreCase("C")){
            return Documented;
        } else if (v.equalsIgnoreCase("Declared")) {
            return Declared;
        } else if (v.equalsIgnoreCase("Documented")) {
            return Documented;
        } else {
            return null;//what as default?
        }
    }

}
