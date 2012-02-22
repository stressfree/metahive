package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class KeyValueSet.
 */
@RooJavaBean
@RooToString
public class KeyValueSet {

	/** The definition id. */
	private Long id;
	
	/** The name. */
	private String name;
	
	/** The key values. */
	private List<KeyValue> keyValues = new ArrayList<KeyValue>();
	
	
	/**
	 * Adds the key value.
	 *
	 * @param keyValue the key value
	 */
	public void addKeyValue(final KeyValue keyValue) {
		if (keyValues.size() == 1) {
			KeyValue existing = keyValues.get(0);
			if (existing.hasNoData()) {
				// Remove the existing key value because it has no data
				keyValues.remove(0);
			}
		}
		if (keyValues.size() == 0 || !keyValue.hasNoData()) {
			keyValues.add(keyValue);
		}
	}
	
	/**
	 * Gets the key value count.
	 *
	 * @return the key value count
	 */
	public int getKeyValueCount() {
		return keyValues.size();
	}
	
}
