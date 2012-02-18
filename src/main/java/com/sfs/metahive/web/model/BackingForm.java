package com.sfs.metahive.web.model;

import org.apache.commons.lang.StringUtils;

/**
 * The Class BackingForm.
 */
public abstract class BackingForm {

	/**
	 * Trim the supplied value.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static final String trim(final String value) {
				
		StringBuilder sb = new StringBuilder();
		
		if (StringUtils.isNotBlank(value)) {
			sb.append(StringUtils.strip(value));
	
			while (StringUtils.startsWith(sb.toString(), "\u00A0")) {
				sb.delete(0, 1);
			}
			while (StringUtils.startsWith(sb.toString(), "&#160;")) {
				sb.delete(0, 6);
			}
			while (StringUtils.startsWith(sb.toString(), "&nbsp;")) {
				sb.delete(0, 6);
			}
		}
		return sb.toString();
	}
	
}
