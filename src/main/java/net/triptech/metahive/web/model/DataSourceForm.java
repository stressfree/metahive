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
package net.triptech.metahive.web.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import net.triptech.metahive.model.Comment;
import net.triptech.metahive.model.CommentType;
import net.triptech.metahive.model.ConditionOfUse;
import net.triptech.metahive.model.DataSource;
import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.Organisation;
import net.triptech.metahive.model.Person;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;


/**
 * The Class DataSourceForm.
 */
@RooJavaBean
public class DataSourceForm extends BackingForm {

    /** The id. */
    private long id;

    /** The version. */
    private long version;

    /** The definition. */
    @NotNull
    private Definition definition;

    /** The source of the collected data. */
    private String collectionSource;

    /** The organisation. */
    private Organisation organisation;

    /** The details. */
    private String details;

    /** The condition of use. */
    @NotNull
    private ConditionOfUse conditionOfUse;

    /** The date the data was collected */
    @DateTimeFormat(pattern = "d/M/yyyy")
    private Date collectionDate;

    /** The people. */
    private List<Person> pointsOfContact = new ArrayList<Person>();

    /** The log message. */
    private String logMessage;


    /**
     * Creates a new data source object from the form data.
     *
     * @param user the user
     * @return the definition
     */
    public final DataSource newDataSource(Person user) {
        return buildDataSource(new DataSource(), user);
    }

    /**
     * Merge the form data with the existing data source object.
     *
     * @param dataSource the data source
     * @param user the user
     * @return the data source
     */
    public final DataSource mergedDataSource(final DataSource dataSource,
            final Person user) {

        if (dataSource != null) {
            return buildDataSource(dataSource, user);
        }

        return dataSource;
    }

    /**
     * Parses the data source and returns a data source form.
     *
     * @param dataSource the data source
     * @return the data source form
     */
    public static DataSourceForm parseDataSource(final DataSource dataSource) {

        DataSourceForm dataSourceForm = new DataSourceForm();

        if (dataSource != null) {
            dataSourceForm.setId(dataSource.getId());
            dataSourceForm.setCollectionSource(dataSource.getCollectionSource());
            dataSourceForm.setDefinition(dataSource.getDefinition());
            dataSourceForm.setOrganisation(dataSource.getOrganisation());
            dataSourceForm.setDetails(trim(dataSource.getDetails()));
            dataSourceForm.setConditionOfUse(dataSource.getConditionOfUse());
            dataSourceForm.setCollectionDate(dataSource.getCollectionDate());
            dataSourceForm.setPointsOfContact(dataSource.getPointsOfContact());
        }

        return dataSourceForm;
    }

    /**
     * Builds the comment object from the form data.
     *
     * @param commentPrefix the comment prefix
     * @param dataSource the data source
     * @param user the user
     * @return the definition
     */
    public final Comment newComment(final CommentType commentType,
            final DataSource dataSource, final Person user) {

        Comment comment = new Comment();

        if (dataSource != null && user != null) {
            comment.setCommentType(commentType);
            comment.setMessage(trim(this.getLogMessage()));
            comment.setDefinition(dataSource.getDefinition());
            comment.setDataSourceId(dataSource.getId());
            comment.setPerson(user);
        }
        return comment;
    }

    /**
     * Builds the data source object from the form data.
     *
     * @param dataSource the data source
     * @param user the user
     * @return the data source
     */
    private final DataSource buildDataSource(final DataSource dataSource,
            final Person user) {

        if (dataSource != null) {

            if (dataSource.getId() == null) {
                // These relationships should only be set for a new data source
                dataSource.setDefinition(this.getDefinition());
                dataSource.setOrganisation(this.getOrganisation());
            }
            dataSource.setCollectionSource(this.getCollectionSource());
            dataSource.setDetails(trim(this.getDetails()));
            dataSource.setConditionOfUse(this.getConditionOfUse());
            dataSource.setPointsOfContact(this.getPointsOfContact());
            dataSource.setCollectionDate(this.getCollectionDate());
        }

        return dataSource;
    }

}
