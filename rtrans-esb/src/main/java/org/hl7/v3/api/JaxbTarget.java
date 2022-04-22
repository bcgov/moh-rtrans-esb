/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hl7.v3.api;

import java.util.List;
import org.hl7.v3.AD;
import org.hl7.v3.II;
import org.hl7.v3.TEL;

/**
 *
 * @author conrad.gustafson
 */
public interface JaxbTarget {
    public JaxbIdentifiedPerson getIdentifiedPerson();
    public List<TEL> getTelecom();
    public List<AD> getAddr();
    public List<II> getId();  
}