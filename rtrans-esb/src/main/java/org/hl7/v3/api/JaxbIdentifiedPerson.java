/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hl7.v3.api;

import java.util.List;
import org.hl7.v3.BL;
import org.hl7.v3.CE;
import org.hl7.v3.II;
import org.hl7.v3.PN;
import org.hl7.v3.TS;

/**
 *
 * @author conrad.gustafson
 */
public interface JaxbIdentifiedPerson {
    public List<PN> getName();
    public List<II> getId();
    public TS getBirthTime();
    public CE getAdministrativeGenderCode();
    public BL getDeceasedInd();
    public TS getDeceasedTime();
}
