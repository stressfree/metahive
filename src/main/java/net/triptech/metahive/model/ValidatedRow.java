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

import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class ValidatedRow.
 */
@RooJavaBean
public class ValidatedRow {

    /** The valid. */
    private boolean valid;

    /** The value. */
    private List<ValidatedField> fields;

    /** The comment. */
    private String comment;

    /**
     * Gets the CSS classes.
     *
     * @return the CSS class string
     */
    public String getCssClasses() {
        String value = "valid";
        if (!valid) {
            value = "invalid";
        }
        return value;
    }
}
