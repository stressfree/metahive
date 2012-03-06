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
package com.sfs.metahive.model;

/**
 * The Enum KeyValueType.
 */
public enum KeyValueType {

    CALCULATED("label_com_sfs_metahive_model_keyvalue_caclulated"),
    OVERRIDDEN("label_com_sfs_metahive_model_keyvalue_overridden");

    private String messageKey;

    private KeyValueType(String name) {
        this.messageKey = name;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
