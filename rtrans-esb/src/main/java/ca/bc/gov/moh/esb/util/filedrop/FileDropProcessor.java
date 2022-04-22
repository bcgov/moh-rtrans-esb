/*
 * *********************************************************************************************************************
 *  Copyright (c) 2018, Ministry of Health, BC.                                                 *
 *                                                                                                                     *
 *  All rights reserved.                                                                                               *
 *    This information contained herein may not be used in whole                                                       *
 *    or in part without the express written consent of the                                                            *
 *    Government of British Columbia, Canada.                                                                          *
 *                                                                                                                     *
 *  Revision Control Information                                                                                       *
 *  File:                $Id::                                                                                       $ *
 *  Date of Last Commit: $Date::                                                                                     $ *
 *  Revision Number:     $Rev::                                                                                      $ *
 *  Last Commit by:      $Author::                                                                                   $ *
 *                                                                                                                     *
 * *********************************************************************************************************************
 */
package ca.bc.gov.moh.esb.util.filedrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author joshua.burton
 */
public class FileDropProcessor {
    
    private final String fileDropPath;
    private final String transactionType;
    private final String transactionId;
    private final Properties filedropProperties;
    
    private static final String XML_VERSION_TAG = "<?xml";

    public FileDropProcessor(String path, String transactionType, String transactionId, Properties filedropProperties) {
        this.fileDropPath = path;
        this.transactionType = transactionType;
        this.transactionId = transactionId;
        this.filedropProperties = filedropProperties;
    }
    
    public void dropFile(String body, String messageType) {
                
        if (new FileDropConfiguration().isMessageToBeSaved(transactionType, messageType, filedropProperties)) {
            
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime());
            String fileName = fileDropPath+transactionType + "/"
                    + transactionId + "-" + timeStamp + "-" + messageType;

            File file = new File(fileName);
            file.getParentFile().mkdirs();
            try (
                PrintWriter writer = new PrintWriter(fileName)) {
                writer.println(isXml(body) ? prettyPrint(body) : body);
            } catch (FileNotFoundException | TransformerException ex) {
                Logger.getLogger(FileDropProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String prettyPrint(String xml) throws TransformerException {
        xml = xml.replaceFirst("^[^<]*", "");
        xml = xml.replaceFirst("(UTF-16)", "UTF-8");
        
        Source input = new StreamSource(new StringReader(xml));
        StreamResult output = new StreamResult(new StringWriter());
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        transformer.transform(input, output);
        
        // Inserts newline between xml version tag and root element
        String result = output.getWriter().toString();
        result = new StringBuilder(result).insert(result.indexOf(">") + 1, "\n").toString();
        
        return result;
    }
    
    private boolean isXml(String body) {
        return body.contains(XML_VERSION_TAG) 
                || (body.contains("<") && body.contains("</"));
    }
}
