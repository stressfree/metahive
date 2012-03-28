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

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class DefinitionFilter.
 */
@RooJavaBean
public class SubmissionFilter extends BaseFilter {

    /** The person id. */
    private Long personId;

    /** The organisation id. */
    private Long organisationId;


    /**
     * Builds the query string.
     *
     * @param filter the filter
     * @param request the request
     * @return the string
     */
    public String getQueryString() {

        StringBuilder queryString = new StringBuilder();

        if (personId != null && personId > 0) {
            queryString.append("&personId=");
            queryString.append(personId);
        }
        if (organisationId != null && organisationId > 0) {
            queryString.append("&organisationId=");
            queryString.append(organisationId);
        }
        return queryString.toString();
    }

}
