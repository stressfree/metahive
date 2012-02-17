package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.web.model.DefinitionFilter;

/**
 * The Class Definition.
 */
@RooJavaBean
@RooEntity(finders = { "findDefinitionsByNameLike" })
public class Definition {

	private static Logger logger = Logger.getLogger(Definition.class);
	
	/** The name. */
	@NotNull
	@Size(min = 1, max = 100)
	@Column(unique = true)
	private String name;
	
	/** The definition type. */
	@NotNull
	@Enumerated(EnumType.STRING)
	private DefinitionType definitionType = DefinitionType.STANDARD;

	/** The data type. */
	@NotNull
	@Enumerated(EnumType.STRING)
	private DataType dataType;
	
	/** The calculation. */
	private String calculation;
	
	/** The key value generator. */
	@NotNull
	@Enumerated(EnumType.STRING)
	private KeyValueGenerator keyValueGenerator;
	
	/** The key value access. */
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole keyValueAccess;

	/** The definition descriptions. */
	@OrderBy("created DESC")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private List<Description> descriptions = new ArrayList<Description>();

	/** The data sources. */
	@OrderBy("organisation ASC")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private List<DataSource> dataSources = new ArrayList<DataSource>();

	/** The category. */
	@ManyToOne
	@NotNull
	private Category category;
	
	/** The applicability. */
	@NotNull
	@Enumerated(EnumType.STRING)
	private Applicability applicability;
	
	/** The related definitions. */
	@OrderBy("name ASC")
	@ManyToMany
	private List<Definition> relatedDefinitions = new ArrayList<Definition>();
		
	/** The comments. */
	@OrderBy("created ASC")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
	private List<Comment> comments = new ArrayList<Comment>();

	
	/**
	 * Gets the calculation.
	 *
	 * @return the calculation
	 */
	public final String getCalculation() {
		if (this.definitionType != DefinitionType.CALCULATED) {
			this.calculation = "";
		}
		return this.calculation;
	}
	
	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public final Description getDescription() {
		Description description = null;

		if (getDescriptions() != null && getDescriptions().size() > 0) {
			description = getDescriptions().get(0);
		}
		return description;
	}

	/**
	 * Gets the related definitions.
	 *
	 * @return the related definitions
	 */
	public final List<Definition> getRelatedDefinitions() {
		if (this.relatedDefinitions == null) {
			this.relatedDefinitions = new ArrayList<Definition>();
		}
		if (this.definitionType == DefinitionType.STANDARD 
				&& this.relatedDefinitions.size() > 0) {
			this.relatedDefinitions = new ArrayList<Definition>();
		}
		return this.relatedDefinitions;
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
				"SELECT d FROM Definition d ORDER BY name ASC",
				Definition.class).getResultList();
	}

	/**
	 * A helper function to load the definitions grouped by their category.
	 * The resulting map excludes the unique identifier definition(s).
	 *
	 * @return the map
	 */
	public static Map<String, List<Definition>> findGroupedDefinitions() {			
		return groupDefinitions(Definition.findAllDefinitions());
	}	

	/**
	 * A helper function to load the definitions that an organisation 
	 * has contributed data for, grouped by their category. 
	 * The resulting map excludes the unique identifier definition(s).
	 *
	 * @return the map
	 */
	public static Map<String, List<Definition>> findGroupedDefinitions(
			final Organisation organisation) {		
		return groupDefinitions(Definition.findDefinitionEntries(organisation));
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

		TypedQuery<Definition> q = entityManager().createQuery(
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

		TypedQuery<Definition> q = entityManager().createQuery(
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
	 * @param organisation the organisation to filter by
	 * @return the list of definitions
	 */
	public static List<Definition> findDefinitionEntries(
			final Organisation organisation) {

		List<Definition> definitions = new ArrayList<Definition>();

		if (organisation != null && organisation.getId() != null) {

			String sql = "SELECT d FROM Definition d"
					+ " JOIN d.dataSources ds JOIN ds.organisation o"
					+ " WHERE o.id = :organisationId ORDER BY d.name ASC";

			TypedQuery<Definition> q = entityManager().createQuery(sql,
					Definition.class);
			q.setParameter("organisationId", organisation.getId());

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
				"SELECT d FROM Definition d JOIN d.category c");
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
				"SELECT COUNT(d) FROM Definition d JOIN d.category c");
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
	 * Find the definitions that could be potentially related to the supplied definition.
	 *
	 * @param definition the definition
	 * @return the list
	 */
	public static List<Definition> findPotentialRelatedDefinitions(
			final Definition definition) {
		
		List<Definition> relatedDefinitions = new ArrayList<Definition>();
		HashMap<String, Object> variables = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer();
		
		if (definition != null) {
			StringBuffer where = new StringBuffer();				
						
			if (definition.getRelatedDefinitions() != null) {
				for (Definition def : definition.getRelatedDefinitions()) {
					if (where.length() > 0) {
						where.append(" AND ");
					}
					where.append("d.id != ");
					where.append(def.getId());
				}
				if (where.length() > 0) {
					where.insert(0, "(");
					where.append(")");
				}
			}

			logger.error("Definition type: " + definition.getDefinitionType());
			if (definition.getDefinitionType() == DefinitionType.CALCULATED
					|| definition.getDefinitionType() == DefinitionType.SUMMARY) {
				
				if (definition.getDefinitionType() == DefinitionType.SUMMARY) {
					if (where.length() > 0) {
						where.append(" AND ");
					}
					where.append("d.definitionType != :definitionType");
					variables.put("definitionType", DefinitionType.SUMMARY);
				}
				
				// Load all of the definitions, less the definitions already associated
				sql.append("SELECT d FROM Definition d");
				
				if (where.length() > 0) {
					sql.append(" WHERE ");
					sql.append(where.toString());
				}
				sql.append(" ORDER BY d.name ASC");
			}
		}
		
		if (sql.length() > 0) {
			logger.error("SQL: " + sql.toString());
			TypedQuery<Definition> q = entityManager().createQuery(sql.toString(),
					Definition.class);
			for (String variable : variables.keySet()) {
				logger.error("Parameter: " + variable);
				logger.error("Value: " + variables.get(variable));
				q.setParameter(variable, variables.get(variable));
			}			
			relatedDefinitions = q.getResultList();
		}
		
		return relatedDefinitions;
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

	
	/**
	 * Group the supplied definitions.
	 *
	 * @param definitions the definitions
	 * @return the map
	 */
	private static Map<String, List<Definition>> groupDefinitions(
			final List<Definition> definitions) {
		
		Map<String, List<Definition>> groupedDefinitions = 
				new TreeMap<String, List<Definition>>();
		
		for (Definition definition : definitions) {
			if (definition.getDataType() != DataType.TYPE_UNIQUEID) {
				String name = definition.getCategory().getName();
				
				List<Definition> subGroup = new ArrayList<Definition>();
				
				if (groupedDefinitions.containsKey(name)) {
					subGroup = groupedDefinitions.get(name);
				}
				subGroup.add(definition);
				
				groupedDefinitions.put(name, subGroup);
			}
		}		
		return groupedDefinitions;		
	}
	
}
