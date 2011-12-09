package com.sfs.metahive.model;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class ValidatedField.
 */
@RooJavaBean
public class ValidatedField {

	/** The valid. */
	private boolean valid;
	
	/** The value. */
	private String value;
	
	/** The comment. */
	private String comment;
	
	/**
	 * Gets the valid boolean as a string.
	 *
	 * @return the valid string
	 */
	public String getValidString() {
		String value = "valid";
		if (!valid) {
			value = "invalid";
		}
		return value;
	}
	
}
