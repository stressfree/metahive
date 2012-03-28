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

import java.util.Map;
import java.util.TreeMap;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class KeyValueCategory.
 */
@RooJavaBean
@RooToString
public class KeyValueCategory {

    /** The name. */
    private String name;

    /** The key value sets. */
    private Map<String, KeyValueSet> keyValueSets = new TreeMap<String, KeyValueSet>();

}
