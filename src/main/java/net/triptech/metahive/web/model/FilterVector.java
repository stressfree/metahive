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

import java.util.HashMap;
import java.util.Map;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class FilterVector.
 */
@RooJavaBean
public class FilterVector {

	/** The action. */
	private FilterAction action = FilterAction.ADD;

    /** The filter variables. */
    private Map<String, String> filterVariables = new HashMap<String, String>();


    /**
     * Adds the variable.
     *
     * @param key the key
     * @param value the value
     */
    public final void addVariable(final String key, final String value) {
    	this.filterVariables.put(key, value);
    }
}
