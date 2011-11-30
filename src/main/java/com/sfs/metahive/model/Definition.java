package com.sfs.metahive.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.web.model.DefinitionFilter;


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
	@OrderBy("organisation ASC")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private Set<DataSource> dataSources = new HashSet<DataSource>();
	
	/** The categories. */
	@ManyToMany
	private Set<Category> categories = new HashSet<Category>();
	
	/** The comments. */
	@OrderBy("created ASC")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private Set<Comment> comments = new HashSet<Comment>();

	
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
	 * Adds a comment.
	 * 
	 * @param comment the comment
	 */
	public final void addComment(Comment comment) {
		comment.setDefinition(this);
		getComments().add(comment);
	}
	
	/**
	 * The toString method.
	 */
	public final String toString() {
		return getName();
	}
	
	/**
	 * Find all of the definitions.
	 * 
	 * @return an ordered list of definitions
	 */
	public static List<Definition> findAllDefinitions() {
        return entityManager().createQuery(
        		"SELECT d FROM Definition d ORDER BY name ASC", Definition.class)
        		.getResultList();
    }
    
    /**
     * Find definition entries.
     *
     * @param filter the definition filter
     * @param firstResult the first result
     * @param maxResults the max results
     * @return the list
     */
    public static List<Definition> findDefinitionEntries(final DefinitionFilter filter,
    		final int firstResult, final int maxResults) {
    	
    	

    	StringBuffer sql = new StringBuffer(
    			"SELECT d FROM Definition d JOIN d.categories c");    	
    	sql.append(buildWhere(filter));
    	sql.append(" ORDER BY d.name ASC");
    	
    	TypedQuery<Definition> q = entityManager().createQuery(
        		sql.toString(), Definition.class)
        		.setFirstResult(firstResult).setMaxResults(maxResults);
    	
    	HashMap<String, String> variables = buildVariables(filter);
    	for (String variable : variables.keySet()) {
    		q.setParameter(variable, variables.get(variable));
    	}
    	
    	return q.getResultList();
    }

    /**
     * Count the definitions.
     *
     * @param filter the filter
     * @return the long
     */
    public static long countDefinitions(final DefinitionFilter filter) {
    	
    	StringBuffer sql = new StringBuffer(
    			"SELECT COUNT(d) FROM Definition d JOIN d.categories c");    	
    	sql.append(buildWhere(filter));
    	
        TypedQuery<Long> q = entityManager().createQuery(sql.toString(), Long.class);

    	HashMap<String, String> variables = buildVariables(filter);
    	for (String variable : variables.keySet()) {
    		q.setParameter(variable, variables.get(variable));
    	}
        
        return q.getSingleResult();
    }
    
    
    /**
     * Builds the where statement.
     *
     * @param filter the filter
     * @return the string
     */
    private static String buildWhere(final DefinitionFilter filter) {
    	StringBuffer where = new StringBuffer();
    	
    	if (StringUtils.isNotBlank(filter.getName())) {
    		where.append("LOWER(d.name) LIKE LOWER(:name)");		
    	}
    	
    	if (StringUtils.isNotBlank(filter.getCategory())) {
    		if (where.length() > 0) {
    			where.append(" AND ");
    		}
    		where.append("LOWER(c.name) LIKE LOWER(:category)");
    	}
    	
    	if (where.length() > 0) {
    		where.insert(0, " WHERE ");
    	}
    	return where.toString();
    }
    
    /**
     * Builds the variables for the where statement.
     *
     * @param filter the filter
     * @return the hash map
     */
    private static HashMap<String, String> buildVariables(final DefinitionFilter filter) {
    	
    	HashMap<String, String> variables = new HashMap<String, String>();

    	if (StringUtils.isNotBlank(filter.getName())) {
    		variables.put("name", "%" + filter.getName() + "%");    		
    	}
    	
    	if (StringUtils.isNotBlank(filter.getCategory())) {
    		variables.put("category", filter.getCategory());
    	}
    	return variables;
    }
    
}
