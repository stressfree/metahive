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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Index;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;


/**
 * The Class SubmittedField.
 */
@RooJavaBean
@RooToString
@RooEntity
public class SubmittedField {

    /** The record. */
    @ManyToOne
    @NotNull
    private Record record;

    /** The definition. */
    @ManyToOne
    @NotNull
    @Index(name="indexDefinition")
    private Definition definition;

    /** The submission. */
    @ManyToOne
    @NotNull
    private Submission submission;

    /** The primary record id. */
    @NotNull
    @Index(name="indexPrimaryRecordId")
    private String primaryRecordId;

    /** The secondary record id. */
    @Index(name="indexSecondaryRecordId")
    private String secondaryRecordId;

    /** The tertiary record id. */
    @Index(name="indexTertiaryRecordId")
    private String tertiaryRecordId;

    /** The value. */
    @NotNull
    @Size(min = 1, max = 255)
    private String value;

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
     * Gets the formatted value.
     *
     * @return the formatted value
     */
    public final String getFormattedValue() {
        String unformattedValue = "";
        String formattedValue = "";

        if (this.getDefinition() == null) {
            throw new NullPointerException("A valid definition is required");
        }

        if (StringUtils.isNotBlank(this.getValue())) {
            unformattedValue = this.getValue();
        }

        if (this.getDefinition().getDataType() == DataType.TYPE_STRING) {
            formattedValue = unformattedValue + appendUnitOfMeasure();
        }
        if (this.getDefinition().getDataType() == DataType.TYPE_BOOLEAN) {
            formattedValue = unformattedValue;
        }
        if (this.getDefinition().getDataType() == DataType.TYPE_NUMBER) {
            double dblValue = 0;
            try {
                dblValue = Double.parseDouble(unformattedValue);
            } catch (NumberFormatException nfe) {
                // Error parsing double
            }

            DecimalFormat df = new DecimalFormat("#.######");
            formattedValue = df.format(dblValue);
            if (StringUtils.endsWithIgnoreCase(formattedValue, ".000000")) {
                formattedValue = StringUtils.substring(formattedValue, 0,
                        formattedValue.length() -6);
            }
            formattedValue += appendUnitOfMeasure();
        }
        if (this.getDefinition().getDataType() == DataType.TYPE_CURRENCY) {
                double dblValue = 0;
                try {
                    dblValue = Double.parseDouble(unformattedValue);
                } catch (NumberFormatException nfe) {
                    // Error parsing double
                }

                DecimalFormat df = new DecimalFormat("$###,###,###,##0.00");
                formattedValue = df.format(dblValue) + appendUnitOfMeasure();
        }
        if (this.getDefinition().getDataType() == DataType.TYPE_PERCENTAGE) {
            double dblValue = 0;
            try {
                dblValue = Double.parseDouble(unformattedValue);
            } catch (NumberFormatException nfe) {
                // Error parsing double
            }

            NumberFormat percentFormatter = NumberFormat.getPercentInstance(
                    LocaleContextHolder.getLocale());
            formattedValue = percentFormatter.format(dblValue) +
                    appendUnitOfMeasure();
        }
        return formattedValue;
    }

    /**
     * Find the submitted fields for the supplied parameters.
     *
     * @param def the def
     * @param primaryId the primary id
     * @param secondaryId the secondary id
     * @param tertiaryId the tertiary id
     * @return the list
     */
    public static List<SubmittedField> findSubmittedFields(final Definition def,
            final String primaryId, final String secondaryId, final String tertiaryId) {

        if (def == null) {
            throw new IllegalArgumentException("A valid defintion is required");
        }
        if (StringUtils.isBlank(primaryId)) {
            throw new IllegalArgumentException("The primaryId argument is required");
        }

        Map<String, Object> variables = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s FROM SubmittedField AS s JOIN s.definition d");
        sql.append(" JOIN s.submission sub WHERE d.id = :definitionId");
        variables.put("definitionId", def.getId());

        sql.append(" AND LOWER(s.primaryRecordId) = LOWER(:primaryRecordId)");
        variables.put("primaryRecordId", primaryId);

        if (def.getApplicability() == Applicability.RECORD_SECONDARY) {
            if (StringUtils.isNotBlank(secondaryId)) {
                sql.append(" AND LOWER(s.secondaryRecordId) = LOWER(:secondaryRecordId)");
                variables.put("secondaryRecordId", secondaryId);
            } else {
                sql.append(" AND (s.secondaryRecordId IS NULL");
                sql.append(" OR s.secondaryRecordId = '')");
            }
        }
        if (def.getApplicability() == Applicability.RECORD_TERTIARY) {
            if (StringUtils.isNotBlank(tertiaryId)) {
                sql.append(" AND LOWER(s.tertiaryRecordId) = LOWER(:tertiaryRecordId)");
                variables.put("tertiaryRecordId", tertiaryId);
            } else {
                sql.append(" AND (s.tertiaryRecordId IS NULL");
                sql.append(" OR s.tertiaryRecordId = '')");
            }
        }
        sql.append(" ORDER BY sub.created");

        TypedQuery<SubmittedField> q = entityManager().createQuery(sql.toString(),
                SubmittedField.class);
        for (String key : variables.keySet()) {
            q.setParameter(key, variables.get(key));
        }
        return q.getResultList();
    }

    /**
     * Append unit of measure.
     *
     * @return the string
     */
    private String appendUnitOfMeasure() {

        String value = "";

        if (this.getDefinition() != null
                && this.getDefinition().getDescription() != null) {

            String unitOfMeasure = this.getDefinition().getDescription()
                    .getUnitOfMeasure();

            if (StringUtils.isNotBlank(unitOfMeasure)) {
                value += " " + StringUtils.trim(unitOfMeasure);
            }
        }
        return value;
    }
}
