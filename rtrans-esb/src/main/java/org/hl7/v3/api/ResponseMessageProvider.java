/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hl7.v3.api;

import ca.bc.gov.moh.rtrans.entity.SystemResponse;
import java.util.List;

/**
 *
 * @author conrad.gustafson
 */
public interface ResponseMessageProvider {
    List<SystemResponse> getSystemResponses();
}
