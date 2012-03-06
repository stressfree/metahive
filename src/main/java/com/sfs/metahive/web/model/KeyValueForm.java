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
package com.sfs.metahive.web.model;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class KeyValueForm.
 */
@RooJavaBean
public class KeyValueForm extends BackingForm {

    /** The override enabled. */
    private String overrideEnabled;

    /** The override value. */
    private String overrideValue;

    /** The override comment. */
    private String overrideComment;


    /**
     * Checks if the key value is overridden.
     *
     * @return true, if it is overridden
     */
    public boolean isOverridden() {

        boolean overridden = false;

        if (StringUtils.equalsIgnoreCase(this.overrideEnabled, "overridden")) {
            overridden = true;
        }
        return overridden;
    }

    /**
     * Gets the trimmed override comment.
     *
     * @return the trimmed override comment
     */
    public String getTrimmedOverrideComment() {
        return trim(this.getOverrideComment());
    }

}
