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
    	
        StringBuffer queryString = new StringBuffer();
        
        if (StringUtils.isNotBlank(recordId)) {
        	queryString.append("&recordId=");
        	queryString.append(encodeUrlPathSegment(recordId));
        }
        return queryString.toString();    	
    }
    
}
