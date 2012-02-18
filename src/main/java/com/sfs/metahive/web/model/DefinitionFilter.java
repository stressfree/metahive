package com.sfs.metahive.web.model;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class DefinitionFilter.
 */
@RooJavaBean
public class DefinitionFilter extends BaseFilter {

	/** The name. */
	private String name;
	
	/** The category. */
	private String category;
	
	
    /**
     * Builds the query string.
     *
     * @param filter the filter
     * @param request the request
     * @return the string
     */
    public String getQueryString() {
    	
        StringBuilder queryString = new StringBuilder();
        
        if (StringUtils.isNotBlank(name)) {
        	queryString.append("&name=");
        	queryString.append(encodeUrlPathSegment(name));
        }
        if (StringUtils.isNotBlank(category)) {
        	queryString.append("&category=");
        	queryString.append(encodeUrlPathSegment(category));        	
        }
        return queryString.toString();    	
    }
    
}
