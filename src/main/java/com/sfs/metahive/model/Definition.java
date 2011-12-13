package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
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
	@Enumerated(EnumType.STRING)
	private DataType dataType;

	/** The unit of measure. */
	private String unitOfMeasure;

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
	 * @param category
	 *            the category
	 */
	public final void addCategory(Category category) {
		getCategories().add(category);
		category.addDefinition(this);
	}

	/**
	 * Adds a data source.
	 * 
	 * @param dataSource
	 *            the data source
	 */
	public final void addDataSource(DataSource dataSource) {
		dataSource.setDefinition(this);
		getDataSources().add(dataSource);
	}

	/**
	 * Adds a description.
	 * 
	 * @param description
	 *            the description
	 */
	public final void addDescription(Description description) {
		description.setDefinition(this);
		getDescriptions().add(description);
	}

	/**
	 * Adds a comment.
	 * 
	 * @param comment
	 *            the comment
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
				"SELECT d FROM Definition d ORDER BY name ASC",
				Definition.class).getResultList();
	}

	/**
	 * Find the definition going by the supplied name.
	 * 
	 * @param name
	 *            the name
	 * @return the definition
	 */
	public static Definition findDefinitionByNameEquals(String name) {

		Definition definition = null;

		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("The name argument is required");
		}

		EntityManager em = Definition.entityManager();
		TypedQuery<Definition> q = em
				.createQuery(
						"SELECT d FROM Definition AS d WHERE LOWER(d.name) = LOWER(:name)",
						Definition.class);
		q.setParameter("name", name);

		List<Definition> definitions = q.getResultList();

		if (definitions != null && definitions.size() > 0) {
			definition = definitions.get(0);
		}
		return definition;
	}

	/**
	 * Find the definition which is the unique identifier.
	 * 
	 * @param name
	 *            the name
	 * @return the definition
	 */
	public static Definition findUniqueIdentifierDefinition() {

		Definition definition = null;

		EntityManager em = Definition.entityManager();
		TypedQuery<Definition> q = em.createQuery(
				"SELECT d FROM Definition AS d WHERE d.dataType = :dataType",
				Definition.class);
		q.setParameter("dataType", DataType.TYPE_UNIQUEID);

		List<Definition> definitions = q.getResultList();

		if (definitions != null && definitions.size() > 0) {
			definition = definitions.get(0);
		}
		return definition;
	}

	/**
	 * Find definitions that have data supplied by the supplied organisation.
	 * 
	 * @param organisation
	 *            the organisation to filter by
	 * @return the list of definitions
	 */
	public static List<Definition> findDefinitionEntries(
			final Organisation organisation) {

		List<Definition> definitions = new ArrayList<Definition>();

		if (organisation != null && organisation.getId() != null) {

			String sql = "SELECT d FROM Definition d"
					+ " JOIN d.dataSources ds JOIN ds.organisation o"
					+ " WHERE o.id = :organisationId AND d.dataType != :dataType"
					+ " ORDER BY d.name ASC";

			TypedQuery<Definition> q = entityManager().createQuery(sql,
					Definition.class);
			q.setParameter("organisationId", organisation.getId());
			// Do not include the unique id data type as it is assumed this is
			// known.
			q.setParameter("dataType", DataType.TYPE_UNIQUEID);

			definitions = q.getResultList();
		}
		return definitions;
	}

	/**
	 * Find definitions for the supplied ids.
	 * 
	 * @param definitionIds
	 *            the definition ids
	 * @return the list of definitions
	 */
	public static List<Definition> findDefinitionEntries(
			final String[] definitionIds) {

		List<Definition> definitions = new ArrayList<Definition>();

		StringBuffer where = new StringBuffer();

		if (definitionIds != null) {
			for (String definitionId : definitionIds) {
				try {
					Long id = new Long(definitionId);

					if (id > 0) {
						if (where.length() > 0) {
							where.append(" OR ");
						}
						where.append("d.id = ");
						where.append(id);
					}
				} catch (Exception e) {
					// Error casting to a Long - skip
				}
			}
		}

		if (where.length() > 0) {
			StringBuffer sql = new StringBuffer(
					"SELECT d FROM Definition d WHERE ");
			sql.append(where.toString());
			sql.append(" ORDER BY d.name ASC");

			definitions = entityManager().createQuery(sql.toString(),
					Definition.class).getResultList();
		}
		return definitions;
	}

	/**
	 * Find definition entries.
	 * 
	 * @param filter the definition filter
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the list
	 */
	public static List<Definition> findDefinitionEntries(
			final DefinitionFilter filter, final int firstResult,
			final int maxResults) {

		StringBuffer sql = new StringBuffer(
				"SELECT d FROM Definition d JOIN d.categories c");
		sql.append(buildWhere(filter));
		sql.append(" ORDER BY d.name ASC");

		TypedQuery<Definition> q = entityManager()
				.createQuery(sql.toString(), Definition.class)
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

		TypedQuery<Long> q = entityManager().createQuery(sql.toString(),
				Long.class);

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
	private static HashMap<String, String> buildVariables(
			final DefinitionFilter filter) {

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
