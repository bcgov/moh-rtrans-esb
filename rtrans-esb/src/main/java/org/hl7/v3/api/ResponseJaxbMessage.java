/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hl7.v3.api;

import org.hl7.v3.CS;
import org.hl7.v3.II;
import org.hl7.v3.MCCIMT000300BCReceiver;
import org.hl7.v3.MCCIMT000300BCSender;
import org.hl7.v3.TS;

/**
 *
 * @author conrad.gustafson
 */
public interface ResponseJaxbMessage extends ResponseMessageProvider {
    
    public II getId();
    public TS getCreationTime();
    public CS getAcceptAckCode();
    public MCCIMT000300BCSender getSender();
    public MCCIMT000300BCReceiver getReceiver();
    
}
