package ca.bc.gov.moh.esb.util;

import com.thoughtworks.xstream.XStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author conrad.gustafson
 */
public class SimpleSerializer {
    
    private static XStream xstream = new XStream();
    
    private static void setSecurity(){
        xstream.allowTypesByWildcard(new String[] {
            "ca.bc.gov.moh.**" });
    }
    
    public static Object fromString(String serializedTransaction) {
        setSecurity();
        return xstream.fromXML(serializedTransaction);
    }

    public static String toString(Object object) {
        setSecurity();
        return xstream.toXML(object);
    }
}
