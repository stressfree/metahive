package com.sfs.metahive.model;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class KeyValueCategory.
 */
@RooJavaBean
@RooToString
public class KeyValueCategory {

	/** The name. */
	private String name;

	/** The key value sets. */
	private Map<String, KeyValueSet> keyValueSets = new TreeMap<String, KeyValueSet>();
	
}