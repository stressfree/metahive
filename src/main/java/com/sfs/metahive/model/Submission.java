/*******************************************************************************
 * Copyright (c) 2012 David Harrison, Triptech Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     David Harrison, Triptech Ltd - initial API and implementation
 ******************************************************************************/
package com.sfs.metahive.model;

import flexjson.JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import com.sfs.metahive.web.model.SubmissionFilter;

/**
 * The Class Submission.
 */
@RooJavaBean
@RooToString
@RooEntity
@RooJson
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submission")
    private List<SubmittedField> submittedFields = new ArrayList<SubmittedField>();

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

    @JSON(include=false)
    public Person getPerson() {
        return this.person;
    }

    /**
     * Gets the person's name.
     *
     * @return the person name
     */
    public String getPersonName() {

        String personName = "";

        if (this.person != null) {
            personName = this.person.getFormattedName();
        }
        return personName;
    }

    /**
     * Gets the organisation.
     *
     * @return the organisation
     */
    @JSON(include=false)
    public Organisation getOrganisation() {
        return this.organisation;
    }

    /**
     * Gets the organisation's name.
     *
     * @return the organisation name
     */
    public String getOrganisationName() {

        String organisationName = "";

        if (this.organisation != null) {
            organisationName = this.organisation.getName();
        }
        return organisationName;
    }

    public String getFormattedCreationDate() {

        String formattedCreated = "";

        if (this.created != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedCreated = format.format(this.created);
        }
        return formattedCreated;
    }

    /**
     * Gets the raw data.
     *
     * @return the raw data
     */
    @JSON(include=false)
    public String getRawData() {
        if (StringUtils.isBlank(rawData)) {
            rawData = "";
        }
        return rawData;
    }

    /**
     * Gets the raw data grid.
     *
     * @return the raw data grid
     */
    @JSON(include=false)
    public DataGrid getRawDataGrid() {
        DataGrid dataGrid = new DataGrid();

        if (StringUtils.isNotBlank(this.getRawData())) {
            dataGrid = new DataGrid(this.getRawData());
        }
        return dataGrid;
    }

    /**
     * Gets the creation time for the submission.
     *
     * @return the created
     */
    @JSON(include=false)
    public Date getCreated() {
        return this.created;
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
     * Find all of the submissions for the supplied organisations.
     *
     * @return an ordered list of submissions
     */
    public static List<Submission> findAllSubmissions(List<Organisation> organisations) {

        List<Submission> submissions = new ArrayList<Submission>();

        StringBuilder where = new StringBuilder();

        if (organisations != null) {
            for (Organisation organisation : organisations) {
                if (where.length() > 0) {
                    where.append(" OR ");
                }
                where.append("s.organisation = ");
                where.append(organisation.getId());
            }
        }

        if (where.length() > 0) {

            submissions = entityManager().createQuery("SELECT s FROM Submission s WHERE "
                    + where.toString() + " ORDER BY created ASC",
                    Submission.class).getResultList();
        }
        return submissions;
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

        StringBuilder sql = new StringBuilder("SELECT s FROM Submission s");
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

        StringBuilder sql = new StringBuilder("SELECT COUNT(s) FROM Submission s");
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
        StringBuilder where = new StringBuilder();

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
