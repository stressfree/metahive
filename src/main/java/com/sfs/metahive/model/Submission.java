package com.sfs.metahive.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.sfs.metahive.web.model.SubmissionFilter;

/**
 * The Class Submission.
 */
@RooJavaBean
@RooToString
@RooEntity
public class Submission {
	
	/** The person. */
	@NotNull
    @ManyToOne
	private Person person;
	
	/** The organisation. */
	@NotNull
    @ManyToOne
	private Organisation organisation;
	
	/** The raw data. */
	@Lob
	private String rawData;
	
	/** The created timestamp. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;
    
    /**
     * The on create actions.
     */
    @PrePersist
    protected void onCreate() {
    	created = new Date();
    }
	
	/**
	 * Find all of the submissions.
	 * 
	 * @return an ordered list of submissions
	 */
	public static List<Submission> findAllSubmissions() {
		return entityManager().createQuery(
				"SELECT s FROM Submission s ORDER BY created ASC",
				Submission.class).getResultList();
	}

	/**
	 * Find submission entries.
	 * 
	 * @param filter the submission filter
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the list
	 */
	public static List<Submission> findSubmissionEntries(
			final SubmissionFilter filter, final int firstResult,
			final int maxResults) {

		StringBuffer sql = new StringBuffer("SELECT s FROM Submission s");
		sql.append(buildWhere(filter));
		sql.append(" ORDER BY s.created ASC");

		TypedQuery<Submission> q = entityManager()
				.createQuery(sql.toString(), Submission.class)
				.setFirstResult(firstResult).setMaxResults(maxResults);

		HashMap<String, Long> variables = buildVariables(filter);
		for (String variable : variables.keySet()) {
			q.setParameter(variable, variables.get(variable));
		}

		return q.getResultList();
	}

	/**
	 * Count the submissions.
	 * 
	 * @param filter the filter
	 * @return the long
	 */
	public static long countSubmissions(final SubmissionFilter filter) {

		StringBuffer sql = new StringBuffer("SELECT COUNT(s) FROM Submission s");
		sql.append(buildWhere(filter));

		TypedQuery<Long> q = entityManager().createQuery(sql.toString(),
				Long.class);

		HashMap<String, Long> variables = buildVariables(filter);
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
	private static String buildWhere(final SubmissionFilter filter) {
		StringBuffer where = new StringBuffer();

		if (filter.getPersonId() != null && filter.getPersonId() > 0) {
			where.append("s.person = :personId");
		}
		if (filter.getOrganisationId() != null && filter.getOrganisationId() > 0) {
			if (where.length() > 0) {
				where.append(" AND ");
			}
			where.append("s.organisation = :organisationId");
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
	private static HashMap<String, Long> buildVariables(
			final SubmissionFilter filter) {

		HashMap<String, Long> variables = new HashMap<String, Long>();

		if (filter.getPersonId() != null && filter.getPersonId() > 0) {
			variables.put("personId", filter.getPersonId());
		}

		if (filter.getOrganisationId() != null && filter.getOrganisationId() > 0) {
			variables.put("organisationId", filter.getOrganisationId());
		}
		return variables;
	}

}
