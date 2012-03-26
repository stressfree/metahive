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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.web.model.FilterVector;

/**
 * The Class RecordFilter.
 */
@RooJavaBean
public class RecordFilter extends BaseFilter {

	/** The id. */
	private String id;

    /** The recordId. */
    private String recordId;

    /** The order id. */
    private Long orderId = 0l;

    /** The order descending. */
    private boolean orderDescending;

    /** The filter components. */
    private List<FilterVector> filterVectors = new ArrayList<FilterVector>();

    /** The timestamp. */
    private Date created;


    /**
     * Instantiates a new record filter.
     */
    public RecordFilter() {
    	// Generate a unique id and keep it as a string
    	this.id = UUID.randomUUID().toString();
    	this.created = Calendar.getInstance().getTime();
    }

    /**
     * Sets the order id.
     *
     * @param orderIdVal the new order id
     */
    public final void setOrderId(Long orderIdVal) {
    	Long id = 0l;

    	if (orderIdVal != null) {
    		id = orderIdVal;
    	}

    	if (this.orderId != null && this.orderId == id) {
    		if (this.orderDescending) {
        		this.orderDescending = false;
    		} else {
        		this.orderDescending = true;
    		}
    	} else {
    		this.orderId = id;
    		this.orderDescending = false;
    	}
    }

    /**
     * Builds the query string.
     *
     * @param filter the filter
     * @param request the request
     * @return the string
     */
    public String getQueryString() {

        StringBuilder queryString = new StringBuilder();

        if (StringUtils.isNotBlank(id)) {
            queryString.append("&id=");
            queryString.append(encodeUrlPathSegment(id));
        }
        return queryString.toString();
    }

    /**
     * Process the parameters from the search form.
     *
     * @param request the request
     */
    public void processSearchForm(final HttpServletRequest request) {

    	FilterVector filterVector = new FilterVector();

    	for (Object objParam : request.getParameterMap().keySet()) {
    		String param = (String) objParam;
    		if (StringUtils.startsWith(param, "d_")) {
    			// This is a search term - put the param and its value into the map
    			Object objValue = request.getParameterMap().get(param);
    			if (objValue != null && objValue instanceof String[]) {
    				String[] value = (String[]) objValue;
    				if (StringUtils.isNotBlank(value[0])
    						&& !StringUtils.equalsIgnoreCase(value[0], "*")) {
    					filterVector.addVariable(param, value[0]);
    				}
    			}
    		}
    	}
    	this.filterVectors.add(filterVector);
    }

}
