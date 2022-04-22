/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hl7.v3.api;

import org.hl7.v3.II;
import org.hl7.v3.MCCIMT000100BCReceiver;
import org.hl7.v3.TS;

/**
 *
 * @author Dan.Stepanov
 */
public interface RequestJaxbMessageDistribution {
    public II getId();
    public II getInteractionId();
    public void setCreationTime(TS value);
    public JaxbReceiver getReceiver();
    public JaxbSender getSender();
    public JaxbRequestControlActProcess getControlActProcess();
    public TS getCreationTime();
    public MCCIMT000100BCReceiver getReceiverDistribution();

}
