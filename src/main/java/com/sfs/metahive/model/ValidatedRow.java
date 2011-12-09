package com.sfs.metahive.model;

import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class ValidatedRow.
 */
@RooJavaBean
public class ValidatedRow {

	/** The valid. */
	private boolean valid;
	
	/** The value. */
	private List<ValidatedField> fields;
	
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
