/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bc.gov.moh.rtrans.entity.transaction;

import java.util.Date;

/**
 *
 * @author Conrad.Gustafson
 */
public interface EventMessage {

    public Date getEventTime();

    public void setEventTime(Date eventTime);
}
