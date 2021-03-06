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
 * The Enum CommentType.
 */
public enum CommentType {

    CREATE("label_net_triptech_metahive_model_commenttype_create"),
    MODIFY("label_net_triptech_metahive_model_commenttype_modify"),
    DELETE("label_net_triptech_metahive_model_commenttype_delete"),
    GENERAL("label_net_triptech_metahive_model_commenttype_general");

    private String messageKey;

    private CommentType(String name) {
        this.messageKey = name;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
