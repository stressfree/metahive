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

/**
 * The Class KeyValueCategories.
 */
@RooJavaBean
public class KeyValueCategories {

    /** The id. */
    private String id;

    private Map<String, KeyValueCategory> categories =
            new TreeMap<String, KeyValueCategory>();

}
