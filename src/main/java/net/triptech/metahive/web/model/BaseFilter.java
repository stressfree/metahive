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

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

/**
 * The Class BaseFilter.
 */
@RooJavaBean
public abstract class BaseFilter implements Filter {

    /** The character encoding. */
    private String encoding;

    /**
     * Encode the url path segment.
     *
     * @param pathSegment the path segment
     * @return the string
     */
    protected String encodeUrlPathSegment(String pathSegment) {

        if (StringUtils.isBlank(encoding)) {
            encoding = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, encoding);
        }
        catch (UnsupportedEncodingException uee) {}

        return pathSegment;
    }

}
