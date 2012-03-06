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
 * The Class RecordFilter.
 */
@RooJavaBean
public class RecordFilter extends BaseFilter {

    /** The name. */
    private String recordId;

    /**
     * Builds the query string.
     *
     * @param filter the filter
     * @param request the request
     * @return the string
     */
    public String getQueryString() {

        StringBuilder queryString = new StringBuilder();

        if (StringUtils.isNotBlank(recordId)) {
            queryString.append("&recordId=");
            queryString.append(encodeUrlPathSegment(recordId));
        }
        return queryString.toString();
    }

}
