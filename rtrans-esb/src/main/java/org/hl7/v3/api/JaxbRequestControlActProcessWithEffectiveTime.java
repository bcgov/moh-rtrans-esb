/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hl7.v3.api;

import org.hl7.v3.TS;

/**
 *
 * @author Conrad.Gustafson
 */
public interface JaxbRequestControlActProcessWithEffectiveTime extends JaxbRequestControlActProcess {
    public void setEffectiveTime(TS value);
}
