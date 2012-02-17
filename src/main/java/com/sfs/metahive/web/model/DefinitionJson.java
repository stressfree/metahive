package com.sfs.metahive.web.model;

/**
 * The Class DefinitionJson.
 */
public class DefinitionJson {

	/** The id. */
	private long id;
	
	/** The name. */
	private String name;
	
	/**
	 * Sets the id.
	 *
	 * @param idVal the new id
	 */
	public final void setId(final Long idVal) {
		this.id = idVal;
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
	 * Sets the name.
	 *
	 * @param nameVal the new name
	 */
	public final void setName(final String nameVal) {
		this.name = nameVal;
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
