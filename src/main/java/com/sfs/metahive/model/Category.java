package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Category.
 */
@RooJavaBean
@RooToString
@RooEntity
@RooJson
public class Category {

	/** The name. */
	@NotNull
	@Column(unique = true)
	@Size(min = 1, max = 100)
	private String name;

	/** The definitions. */
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
	private Set<Definition> definitions = new HashSet<Definition>();

	/**
	 * Adds the definition.
	 *
	 * @param definition the definition
	 */
	public void addDefinition(Definition definition) {
		definition.getCategories().add(this);
	}
}
