/*******************************************************************************
 * Copyright (c) 2012 David Harrison, Triptech Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     David Harrison, Triptech Ltd - initial API and implementation
 ******************************************************************************/
package net.triptech.metahive.messaging;

import net.triptech.metahive.KeyValueCalculator;
import net.triptech.metahive.model.Definition;

import org.apache.log4j.Logger;



/**
 * The listener interface for receiving jmsKeyValueGeneration events.
 * The class that is interested in processing a jmsKeyValueGeneration
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addJmsKeyValueGenerationListener<code> method. When
 * the jmsKeyValueGeneration event occurs, that object's appropriate
 * method is invoked.
 *
 * @see JmsKeyValueGenerationEvent
 */
public class JmsKeyValueGenerationListener {

    private static Logger logger =
            Logger.getLogger(JmsKeyValueGenerationListener.class);
    /**
     * On message.
     *
     * @param message the message
     */
    public void onMessage(Object message) {

        logger.debug("JMS message received: " + message);

        if (message instanceof JmsRecalculateRequest) {
            JmsRecalculateRequest req = (JmsRecalculateRequest) message;

            logger.info("Primary Id: " + req.getPrimaryRecordId());
            logger.info("Secondary Id: " + req.getSecondaryRecordId());
            logger.info("Tertiary Id: " + req.getTertiaryRecordId());
            logger.info("Definition Id: " + req.getDefinitionId());

            Definition definition = Definition.findDefinition(req.getDefinitionId());

            if (definition != null) {
                KeyValueCalculator.calculateKeyValue(
                        definition,
                        req.getPrimaryRecordId(),
                        req.getSecondaryRecordId(),
                        req.getTertiaryRecordId());
            }
        }
    }
}
