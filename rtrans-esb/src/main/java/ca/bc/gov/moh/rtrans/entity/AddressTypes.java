package ca.bc.gov.moh.rtrans.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

/**
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:31 PM
 */
public enum AddressTypes {

    Mail,
    Home,
    Work;

    public String value() {
        return name();
    }

    public static AddressTypes fromValue(String use) {
        String[] parts = use.split(Pattern.quote(" "));
        List<String> uses = Arrays.asList(parts);
        return fromValue(uses);
    }
    
    public static AddressTypes fromValue(List<String> uses) {

        Transformer uppercaser = (Object o) -> ((String) o).toUpperCase();

        Collection<String> uppercaseUses = (Collection<String>) CollectionUtils.collect(uses, uppercaser);

        // removed verified 'VER' attribute value for Home address type
        if (uppercaseUses.contains("H")|| uppercaseUses.contains("HOME")
                || uppercaseUses.contains("PHYS")) { 
            return Home;
        } else if (uppercaseUses.contains("W")|| uppercaseUses.contains("WORK")) {
            return Work;
        } else if (uppercaseUses.contains("M")|| uppercaseUses.contains("MAIL") || uppercaseUses.contains("PST")) {
            return Mail;
        } else {
            return null;//what as default?
        }
    }
}
