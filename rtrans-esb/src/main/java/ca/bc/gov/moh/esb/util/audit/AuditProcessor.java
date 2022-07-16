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
package ca.bc.gov.moh.esb.util.audit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.slf4j.LoggerFactory;

/**
 * Handles persistence for ESB database audits.
 *
 * @author joshua.burton
 */
public class AuditProcessor {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuditProcessor.class);

    private static final String PERSISTENCE_UNIT_RTRANS_ESB_AUDITS = "ESBPU";
    private static final String DATABASE_SCHEMA = "db.user";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final String DATABASE_URL = "db.URL";
    
    private static final Map<String, String> persistenceUnitProperties = new HashMap<String, String>();
    private static final Properties appProperties = new Properties();
    
    private final EntityManagerFactory emf;
    
    public AuditProcessor() {
        try {
           appProperties.load(AuditProcessor.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException exception) {
           logger.error(exception.getMessage());
        }
        
        persistenceUnitProperties.put("javax.persistence.jdbc.url", appProperties.getProperty(DATABASE_URL));
        persistenceUnitProperties.put("javax.persistence.jdbc.user", appProperties.getProperty(DATABASE_SCHEMA));
        persistenceUnitProperties.put("javax.persistence.jdbc.password", appProperties.getProperty(DATABASE_PASSWORD));
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_RTRANS_ESB_AUDITS, persistenceUnitProperties);
    }

    public <T> T insert(T record) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();
        em.persist(record);
        et.commit(); 
        return record;
    }

}
