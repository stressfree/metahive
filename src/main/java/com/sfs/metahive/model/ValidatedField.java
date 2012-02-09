package com.sfs.metahive.model;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * The Class ValidatedField.
 */
@RooJavaBean
public class ValidatedField {

	/** The valid. */
	private boolean valid;
	
	/** The notApplicable. */
	private boolean notApplicable;
	
	/** The id field. */
	private boolean idField;
		
	/** The value. */
	private String value;
	
	
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
		if (idField) {
			value += " idfield";
		}
		if (notApplicable) {
			value += " notapplicable";
		}
		return value;
	}
	
}
