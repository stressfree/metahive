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

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Enum DataType.
 */
public enum DataType {

    TYPE_UNIQUEID("label_com_sfs_metahive_model_datatype_uniqueid"),
    TYPE_STRING("label_com_sfs_metahive_model_datatype_string"),
    TYPE_NUMBER("label_com_sfs_metahive_model_datatype_number"),
    TYPE_CURRENCY("label_com_sfs_metahive_model_datatype_currency"),
    TYPE_PERCENTAGE("label_com_sfs_metahive_model_datatype_percentage"),
    TYPE_BOOLEAN("label_com_sfs_metahive_model_datatype_boolean");

    private String messageKey;

    private DataType(String name) {
        this.messageKey = name;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getCssClass() {
        String cssClass = "keyValueNumber";

        if (this == DataType.TYPE_STRING) {
            cssClass = "keyValueString";
        }
        if (this == DataType.TYPE_CURRENCY) {
            cssClass = "keyValueCurrency";
        }
        if (this == DataType.TYPE_PERCENTAGE) {
            cssClass = "keyValuePercentage";
        }
        if (this == DataType.TYPE_BOOLEAN) {
            cssClass = "keyValueBoolean";
        }
        return cssClass;
    }

    public Collection<KeyValueGenerator> getKeyValueGenerators() {

        Collection<KeyValueGenerator> keyValueGens = new ArrayList<KeyValueGenerator>();

        keyValueGens.add(KeyValueGenerator.NEWEST);
        keyValueGens.add(KeyValueGenerator.OLDEST);
        keyValueGens.add(KeyValueGenerator.FREQUENT_DEFAULT_NEW);
        keyValueGens.add(KeyValueGenerator.FREQUENT_DEFAULT_OLD);

        if (this == DataType.TYPE_NUMBER  || this == DataType.TYPE_CURRENCY
                || this == DataType.TYPE_PERCENTAGE) {
            keyValueGens.add(KeyValueGenerator.TOTAL);
            keyValueGens.add(KeyValueGenerator.AVERAGE);
            keyValueGens.add(KeyValueGenerator.HIGHEST);
            keyValueGens.add(KeyValueGenerator.LOWEST);
            keyValueGens.add(KeyValueGenerator.MEDIAN);
            keyValueGens.add(KeyValueGenerator.QUARTILE_LOWER);
            keyValueGens.add(KeyValueGenerator.QUARTILE_UPPER);
        }

        if (this == DataType.TYPE_BOOLEAN) {
            keyValueGens.add(KeyValueGenerator.UNCLEAR);
            keyValueGens.add(KeyValueGenerator.MEDIAN);
            keyValueGens.add(KeyValueGenerator.QUARTILE_LOWER);
            keyValueGens.add(KeyValueGenerator.QUARTILE_UPPER);
        }

        if (this == DataType.TYPE_STRING) {
            keyValueGens.add(KeyValueGenerator.CONCAT);
        }

        if (this == DataType.TYPE_UNIQUEID) {
            keyValueGens = new ArrayList<KeyValueGenerator>();
            keyValueGens.add(KeyValueGenerator.NOT_APPLICABLE);
        }
        return keyValueGens;
    }

}
