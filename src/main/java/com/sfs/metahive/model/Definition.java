package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Definition.
 */
@RooJavaBean
@RooEntity(finders = { "findDefinitionsByNameLike" })
public class Definition {

	/** The name. */
	@NotNull
	@Size(min = 1, max = 100)
	@Column(unique = true)
	private String name;

	/** The data type. */
	@NotNull
	@ManyToOne
	private DataType dataType;
	
	/** The definition descriptions. */
	@OrderBy("created DESC")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private Set<Description> descriptions = new HashSet<Description>();

	/** The data sources. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private Set<DataSource> dataSources = new HashSet<DataSource>();
	
	/** The categories. */
	@ManyToMany
	private Set<Category> categories = new HashSet<Category>();

	
	/**
	 * Gets the category list.
	 *
	 * @return the category list
	 */
	public final String getCategoryList() {
		StringBuffer categoryList = new StringBuffer();
		
		if (this.getCategories() != null) {
			for (Category category : categories) {
				if (categoryList.length() > 0) {
					categoryList.append(", ");
				}
				categoryList.append(category.getName());
			}
		}
		return categoryList.toString();
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public final Description getDescription() {		
		Description description = null;
		
		if (getDescriptions() != null && getDescriptions().size() > 0) {
			description = getDescriptions().iterator().next();
		}
		return description;
	}

	/**
	 * Adds the category.
	 * 
	 * @param category the category
	 */
	public final void addCategory(Category category) {
		getCategories().add(category);
		category.addDefinition(this);
	}

	/**
	 * Adds a data source.
	 * 
	 * @param dataSource the data source
	 */
	public final void addDataSource(DataSource dataSource) {
		dataSource.setDefinition(this);
		getDataSources().add(dataSource);
	}
	
	/**
	 * Adds a description.
	 * 
	 * @param description the description
	 */
	public final void addDescription(Description description) {
		description.setDefinition(this);
		getDescriptions().add(description);
	}
	
	/**
	 * The toString method.
	 */
	public final String toString() {
		return getName();
	}
}
