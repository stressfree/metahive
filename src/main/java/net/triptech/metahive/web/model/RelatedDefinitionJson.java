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
package net.triptech.metahive.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class RelatedDefinitionJson.
 */
public class RelatedDefinitionJson {

    /** The identifier. */
    private String identifier;

    /** The label. */
    private String label;

    /** The items. */
    private List<DefinitionJson> items = new ArrayList<DefinitionJson>();


    /**
     * Sets the identifier.
     *
     * @param identifierVal the new identifier
     */
    public final void setIdentifier(final String identifierVal) {
        this.identifier = identifierVal;
    }

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    public final String getIdentifier() {
        return this.identifier;
    }

    /**
     * Sets the label.
     *
     * @param labelVal the new label
     */
    public final void setLabel(final String labelVal) {
        this.label = labelVal;
    }

    /**
     * Gets the label.
     *
     * @return the label
     */
    public final String getLabel() {
        return this.label;
    }

    /**
     * Sets the items.
     *
     * @param itemsList the new items
     */
    public final void setItems(final List<DefinitionJson> itemsList) {
        this.items = itemsList;
    }

    /**
     * Gets the items.
     *
     * @return the items
     */
    public final List<DefinitionJson> getItems() {
        return this.items;
    }

    /**
     * Adds the definition.
     *
     * @param defJ the def j
     */
    public final void addDefinition(final DefinitionJson defJ) {
        this.items.add(defJ);
    }
}
