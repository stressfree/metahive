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
	 * Gets the CSS classes.
	 *
	 * @return the CSS class string
	 */
	public String getCssClasses() {
		String value = "valid";
		if (!valid) {
			value = "invalid";
		}
		return value;
	}
}
