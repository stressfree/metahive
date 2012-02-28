package com.sfs.metahive.web.model;

import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.KeyValue;
import com.sfs.metahive.model.KeyValueBoolean;
import com.sfs.metahive.model.KeyValueType;
import com.sfs.metahive.model.UserRole;

/**
 * The Class KeyValueJson.
 */
public class KeyValueJson {

	/** The id. */
	private long id;
	
	/** The name. */
	private String name;
	
	/** The overridden. */
	private boolean overridden;
	
	/** The value. */
	private String value;
	
	/** The comment. */
	private String comment;
	
	
	/**
	 * Instantiates a new key value json.
	 *
	 * @param kv the kv
	 * @param definition the definition
	 * @param userRole the user role
	 */
	public KeyValueJson(final KeyValue kv, final Definition definition, 
			final UserRole userRole) {
		if (kv == null) {
			throw new IllegalArgumentException("The supplied key value cannot be null");
		}
		if (definition == null) {
			throw new IllegalArgumentException("The supplied definition cannot be null");
		}
		if (userRole == null) {
			throw new IllegalArgumentException("The supplied user role cannot be null");
		}
		
		this.id = kv.getId();
		this.name = kv.getDefinition().getName();
		this.comment = kv.getComment();
		
		if (kv.getKeyValueType() == KeyValueType.OVERRIDDEN) {
			this.overridden = true;
		}
				
		if (UserRole.allowAccess(userRole, definition.getKeyValueAccess())) {			
			if (definition.getDataType() == DataType.TYPE_STRING) {
				if (kv.getStringValue() != null) {
					value = kv.getStringValue();
				}
			}
			if (definition.getDataType() == DataType.TYPE_BOOLEAN) {
				if (kv.getBooleanValue() != null) {
					value = "Unclear";
					if (kv.getBooleanValue() == KeyValueBoolean.BL_TRUE) {
						value = "Yes";
					}
					if (kv.getBooleanValue() == KeyValueBoolean.BL_FALSE) {
						value = "No";
					}
				}
			}
			if (definition.getDataType() == DataType.TYPE_NUMBER
					|| definition.getDataType() == DataType.TYPE_CURRENCY
					|| definition.getDataType() == DataType.TYPE_PERCENTAGE) {
				if (kv.getDoubleValue() != null) {				
					value = String.valueOf(kv.getDoubleValue());
				}
			}
		}
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public final Long getId() {
		return this.id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public final String getName() {
		if (this.name == null) {
			this.name = "";
		}
		return this.name;
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public final String getComment() {
		if (this.comment == null) {
			this.comment = "";
		}
		return this.comment;
	}
	
	/**
	 * Gets the overridden.
	 *
	 * @return the overridden
	 */
	public final boolean getOverridden() {
		return this.overridden;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public final String getValue() {
		if (this.value == null) {
			this.value = "";
		}
		return this.value;
	}
}
