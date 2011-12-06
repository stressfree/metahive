package com.sfs.metahive.web.model;

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
