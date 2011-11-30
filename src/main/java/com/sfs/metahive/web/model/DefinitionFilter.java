package com.sfs.metahive.web.model;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

/**
 * The Class DefinitionFilter.
 */
@RooJavaBean
public class DefinitionFilter implements Filter {

	/** The name. */
	private String name;
	
	/** The category. */
	private String category;
	
	/** The character encoding. */
	private String encoding;
	
	
    /**
     * Builds the query string.
     *
     * @param filter the filter
     * @param request the request
     * @return the string
     */
    public String getQueryString() {
    	
        StringBuffer queryString = new StringBuffer();
        
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
