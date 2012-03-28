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

import org.apache.commons.lang.StringUtils;

/**
 * The Class BackingForm.
 */
public abstract class BackingForm {

    /**
     * Trim the supplied value.
     *
     * @param value the value
     * @return the string
     */
    public static final String trim(final String value) {

        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotBlank(value)) {
            sb.append(StringUtils.strip(value));

            while (StringUtils.startsWith(sb.toString(), "\u00A0")) {
                sb.delete(0, 1);
            }
            while (StringUtils.startsWith(sb.toString(), "&#160;")) {
                sb.delete(0, 6);
            }
            while (StringUtils.startsWith(sb.toString(), "&nbsp;")) {
                sb.delete(0, 6);
            }
        }
        return sb.toString();
    }

}
