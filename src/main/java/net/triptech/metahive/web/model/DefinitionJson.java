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

import net.triptech.metahive.model.Definition;

/**
 * The Class DefinitionJson.
 */
public class DefinitionJson {

    /** The id. */
    private long id;

    /** The name. */
    private String name;


    public DefinitionJson(final Definition definition) {
        if (definition == null) {
            throw new IllegalArgumentException("The supplied definition cannot be null");
        }
        this.id = definition.getId();
        this.name = definition.getName();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public final Long getId() {
        return this.id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public final String getName() {
        return this.name;
    }
}
