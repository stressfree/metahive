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
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sfs.metahive.KeyValueIdentifier;

/**
 * The Enum KeyValueGenerator.
 */
public enum KeyValueGenerator {

    NOT_APPLICABLE("label_com_sfs_metahive_model_keyvaluegenerator_not_applicable"),
    NEWEST("label_com_sfs_metahive_model_keyvaluegenerator_newest"),
    OLDEST("label_com_sfs_metahive_model_keyvaluegenerator_oldest"),
    FREQUENT_DEFAULT_NEW("label_com_sfs_metahive_model_keyvaluegenerator_frequent_new"),
    FREQUENT_DEFAULT_OLD("label_com_sfs_metahive_model_keyvaluegenerator_frequent_old"),
    UNCLEAR("label_com_sfs_metahive_model_keyvaluegenerator_unclear"),
    AVERAGE("label_com_sfs_metahive_model_keyvaluegenerator_average"),
    HIGHEST("label_com_sfs_metahive_model_keyvaluegenerator_highest"),
    LOWEST("label_com_sfs_metahive_model_keyvaluegenerator_lowest"),
    MEDIAN("label_com_sfs_metahive_model_keyvaluegenerator_median"),
    QUARTILE_LOWER("label_com_sfs_metahive_model_keyvaluegenerator_quartile_lower"),
    QUARTILE_UPPER("label_com_sfs_metahive_model_keyvaluegenerator_quartile_upper");

    /** The message key. */
    private String messageKey;

    /**
     * Instantiates a new key value generator.
     *
     * @param name the name
     */
    private KeyValueGenerator(String name) {
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

    /**
     * Calculate the key value for the supplied definition and list of raw string values.
     *
     * @param def the def
     * @param values the values
     * @return the object
     */
    public static Object calculateFromRawStrings(final Definition def,
            final List<String> values) {

        if (def == null) {
            throw new IllegalArgumentException("A valid definition is required");
        }

        return calculateFromObjects(def, parseValues(def.getDataType(), values));
    }

    /**
     * Calculate the key value for the supplied definition and list of value objects.
     *
     * @param def the def
     * @param values the list of value objects
     * @return the object
     */
    public static Object calculateFromObjects(final Definition def,
            final List<Object> values) {

        if (def == null) {
            throw new IllegalArgumentException("A valid definition is required");
        }

        Object keyValue = null;

        if (def != null && def.getKeyValueGenerator() != null) {
            if (def.getKeyValueGenerator() == KeyValueGenerator.NEWEST) {
                keyValue = KeyValueIdentifier.newest(values);
            }
            if (def.getKeyValueGenerator() == KeyValueGenerator.OLDEST) {
                keyValue = KeyValueIdentifier.oldest(values);
            }
            if (def.getKeyValueGenerator() == KeyValueGenerator.FREQUENT_DEFAULT_NEW) {
                keyValue = KeyValueIdentifier.frequentDefaultNewest(values);
            }
            if (def.getKeyValueGenerator() == KeyValueGenerator.FREQUENT_DEFAULT_OLD) {
                keyValue = KeyValueIdentifier.frequentDefaultOldest(values);
            }
            // This assumes a boolean set of values.
            if (def.getKeyValueGenerator() == KeyValueGenerator.UNCLEAR) {
                keyValue = KeyValueIdentifier.unclear(values);
            }
            // This assumes a boolean or numeric set of values.
            if (def.getKeyValueGenerator() == KeyValueGenerator.MEDIAN) {
                keyValue = KeyValueIdentifier.median(values);
            }
            if (def.getKeyValueGenerator() == KeyValueGenerator.QUARTILE_LOWER) {
                keyValue = KeyValueIdentifier.quartileLower(values);
            }
            if (def.getKeyValueGenerator() == KeyValueGenerator.QUARTILE_UPPER) {
                keyValue = KeyValueIdentifier.quartileUpper(values);
            }
            // This assumes numeric (Double) set of values
            if (def.getKeyValueGenerator() == KeyValueGenerator.AVERAGE) {
                keyValue = KeyValueIdentifier.average(values);
            }
            if (def.getKeyValueGenerator() == KeyValueGenerator.HIGHEST) {
                keyValue = KeyValueIdentifier.highest(values);
            }
            if (def.getKeyValueGenerator() == KeyValueGenerator.LOWEST) {
                keyValue = KeyValueIdentifier.lowest(values);
            }
        }
        return keyValue;
    }

    /**
     * Parses the values to a list of Objects.
     *
     * @param dataType the data type
     * @param values the values
     * @return the list
     */
    public static List<Object> parseValues(final DataType dataType,
            final List<String> values) {

        List<Object> parsedValues = new ArrayList<Object>();

        if (values != null) {
            for (String value : values) {
                parsedValues.add(parseValue(dataType, value));
            }
        }
        return parsedValues;
    }

    /**
     * Parses the value to its respective object.
     *
     * @param dataType the data type
     * @param value the value
     * @return the object
     */
    public static Object parseValue(final DataType dataType, final String value) {

        if (dataType == null) {
            throw new IllegalArgumentException("A valid data type is required");
        }

        Object objValue = null;

        if (value != null) {
            if (dataType == DataType.TYPE_NUMBER
                    || dataType == DataType.TYPE_CURRENCY
                    || dataType == DataType.TYPE_PERCENTAGE) {
                // Cast to doubles
                double dblValue = 0;
                try {
                    dblValue = Double.parseDouble(value);
                } catch (NumberFormatException nfe) {
                    // Do not add this value to the parsed value map
                }
                objValue = dblValue;
            }

            if (dataType == DataType.TYPE_BOOLEAN) {
                objValue = parseToBoolean(value);
            }

            if (dataType == DataType.TYPE_STRING || dataType == DataType.TYPE_UNIQUEID) {
                objValue = value;
            }
        }
        return objValue;

    }

    /**
     * Parses the boolean.
     *
     * @param booleanValue the boolean value
     * @return the string
     */
    private static KeyValueBoolean parseToBoolean(final String blValue) {

        KeyValueBoolean value = KeyValueBoolean.BL_UNCLEAR;

        if (StringUtils.equalsIgnoreCase(blValue, "true")
                || StringUtils.equalsIgnoreCase(blValue, "yes")
                || StringUtils.equals(blValue, "1")) {
            value = KeyValueBoolean.BL_TRUE;
        }
        if (StringUtils.equalsIgnoreCase(blValue, "false")
                || StringUtils.equalsIgnoreCase(blValue, "no")
                || StringUtils.equals(blValue, "0")) {
            value = KeyValueBoolean.BL_FALSE;
        }
        return value;
    }

}
