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
 * The Class KeyValueCollection.
 */
@RooJavaBean
public class KeyValueCollection {

    /** The id. */
    private Long id;

    /** The key value map. */
    private Map<String, KeyValue> keyValueMap = new TreeMap<String, KeyValue>();

    /** The record id. */
    private String recordId;

    /** The secondary record id. */
    private String secondaryRecordId;

    /** The tertiary record id. */
    private String tertiaryRecordId;

}
