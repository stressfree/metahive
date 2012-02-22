package com.sfs.metahive.model;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class KeyValueCategories.
 */
@RooJavaBean
@RooToString
public class KeyValueCategories {

	/** The id. */
	private String id;
	
	private Map<String, KeyValueCategory> categories = 
			new TreeMap<String, KeyValueCategory>();
	
}
