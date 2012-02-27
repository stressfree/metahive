package com.sfs.metahive.web.model;

import com.sfs.metahive.model.Definition;

/**
 * The Class DefinitionJson.
 */
public class DefinitionJson {

	/** The id. */
	private long id;
	
	/** The name. */
	private String name;
	
	
	public DefinitionJson(final Definition definition) {
		if (definition == null) {
			throw new IllegalArgumentException("The supplied definition cannot be null");
		}
		this.id = definition.getId();
		this.name = definition.getName();
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
		return this.name;
	}
}
