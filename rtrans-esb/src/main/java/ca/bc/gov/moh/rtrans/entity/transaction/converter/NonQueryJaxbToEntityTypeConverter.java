/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bc.gov.moh.rtrans.entity.transaction.converter;

import ca.bc.gov.moh.rtrans.entity.transaction.converter.parent.JaxbToEntityTypeConverter;

/**
 *
 * @author patrick.weckermann
 * 
 * Non Query includes add/update/merge/distributions. Query transactions and non-query transactions typically have different mappings and defaults.
 */
public class NonQueryJaxbToEntityTypeConverter extends JaxbToEntityTypeConverter {
    
}
