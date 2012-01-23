package com.sfs.metahive.model;

import java.util.Map;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class KeyValueCollection.
 */
@RooJavaBean
@RooToString
public class KeyValueCollection {

	/** The id. */
	private Long id;
	
	/** The key value map. */
	private Map<String, KeyValue> keyValueMap;
		
	/** The record id. */
	private String recordId;
	
	/** The secondary record id. */
	private String secondaryRecordId;
	
	/** The tertiary record id. */
	private String tertiaryRecordId;
	
}
