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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class KeyValueSet.
 */
@RooJavaBean
public class KeyValueSet {

    /** The definition id. */
    private Long id;

    /** The name. */
    private String name;

    /** The key values. */
    private List<KeyValue> keyValues = new ArrayList<KeyValue>();

    /** The child key value sets. */
    private Map<String, KeyValueSet> childKeyValueSets
            = new TreeMap<String, KeyValueSet>();


    /**
     * Adds the key value.
     *
     * @param keyValue the key value
     * @param showAllDefinitions the show all definitions
     */
    public void addKeyValue(final KeyValue keyValue, final boolean showAllDefinitions) {
        if (keyValues.size() == 1) {
            KeyValue existing = keyValues.get(0);
            if (existing.hasNoData()) {
                // Remove the existing key value because it has no data
                keyValues.remove(0);
            }
        }
        if (!keyValue.hasNoData()) {
            keyValues.add(keyValue);
        }
        if (keyValues.size() == 0 && showAllDefinitions) {
            keyValues.add(keyValue);
        }
    }

    /**
     * Adds the child key value.
     *
     * @param keyValue the key value
     * @param showAllDefinitions the show all definitions
     */
    public void addChildKeyValue(final KeyValue keyValue,
            final boolean showAllDefinitions) {

        Long defId = keyValue.getDefinition().getId();
        String defName = keyValue.getDefinition().getName();

        KeyValueSet kvSet = new KeyValueSet();
        kvSet.setId(defId);
        kvSet.setName(defName);

        if (this.childKeyValueSets.containsKey(defName)) {
            kvSet = this.childKeyValueSets.get(defName);
        }

        kvSet.addKeyValue(keyValue, showAllDefinitions);

        if (kvSet.getKeyValueCount() > 0 || showAllDefinitions) {
            this.childKeyValueSets.put(defName, kvSet);
        }
    }

    /**
     * Gets the key value count.
     *
     * @return the key value count
     */
    public int getKeyValueCount() {
        return keyValues.size();
    }

    /**
     * Gets the child key value count.
     *
     * @return the child key value count
     */
    public int getChildKeyValueSetCount() {
        return childKeyValueSets.size();
    }

}
