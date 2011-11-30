package com.sfs.metahive.web.model;

public interface Filter {
	
    /**
     * Builds the query string.
     *
     * @param filter the filter
     * @param request the request
     * @return the string
     */
    String getQueryString();
    
}
