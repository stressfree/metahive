package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Definition.
 */
@RooJavaBean
@RooToString
@RooEntity(finders = { "findDefinitionsByNameLike" })
public class Definition {

	/** The name. */
	@NotNull
	@Size(min = 1, max = 100)
	private String name;

	/** The data type. */
	@NotNull
	@ManyToOne
	private DataType dataType;
	
	/** The definition descriptions. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private Set<Description> descriptions = new HashSet<Description>();

	/** The data sources. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private Set<DataSource> dataSources = new HashSet<DataSource>();
	
	/** The categories. */
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "definitions")
	private Set<Category> categories = new HashSet<Category>();


	/**
	 * Adds the category.
	 * 
	 * @param category the category
	 */
	public void addCategory(Category category) {
		category.addDefinition(this);
	}
}
