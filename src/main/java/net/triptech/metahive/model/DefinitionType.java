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
 * The Enum DefinitionType.
 */
public enum DefinitionType {

    STANDARD("label_net_triptech_metahive_model_definitiontype_standard"),
    CALCULATED("label_net_triptech_metahive_model_definitiontype_calculated"),
    SUMMARY("label_net_triptech_metahive_model_definitiontype_summary");

    private String messageKey;

    private DefinitionType(String name) {
        this.messageKey = name;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
