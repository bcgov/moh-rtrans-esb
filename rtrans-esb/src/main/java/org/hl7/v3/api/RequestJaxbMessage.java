/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hl7.v3.api;

import org.hl7.v3.CS;
import org.hl7.v3.II;
import org.hl7.v3.TS;

/**
 *
 * @author conrad.gustafson
 */
public interface RequestJaxbMessage {
    public II getId();
    public void setCreationTime(TS value);
    public CS getProcessModeCode();
    public JaxbReceiver getReceiver();
    public JaxbSender getSender();
    public JaxbRequestControlActProcess getControlActProcess();
}
