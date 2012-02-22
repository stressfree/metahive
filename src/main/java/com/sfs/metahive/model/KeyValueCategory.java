package com.sfs.metahive.model;

import java.util.List;
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

	/** The key values. */
	private Map<String, List<KeyValue>> keyValues = new TreeMap<String, List<KeyValue>>();
	
}