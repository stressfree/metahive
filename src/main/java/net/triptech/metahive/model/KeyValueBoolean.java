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
package net.triptech.metahive.model;

/**
 * The Enum KeyValueBoolean.
 */
public enum KeyValueBoolean {

    BL_TRUE("label_net_triptech_metahive_model_keyvalueboolean_true"),
    BL_FALSE("label_net_triptech_metahive_model_keyvalueboolean_false"),
    BL_UNCLEAR("label_net_triptech_metahive_model_keyvalueboolean_unclear");

    /** The message key. */
    private String messageKey;

    /**
     * Instantiates a new key value boolean.
     *
     * @param name the name
     */
    private KeyValueBoolean(String name) {
        this.messageKey = name;
    }

    /**
     * Gets the message key.
     *
     * @return the message key
     */
    public String getMessageKey() {
        return messageKey;
    }
}
