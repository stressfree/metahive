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
package net.triptech.metahive.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;


/**
 * The Class DataSource.
 */
@RooJavaBean
@RooJpaActiveRecord
public class DataSource {

    /** The source of the collected data. */
    private String collectionSource;

    /** The related condition of use. */
    @NotNull
    @ManyToOne
    private ConditionOfUse conditionOfUse;

    /** The related definition. */
    @NotNull
    @ManyToOne
    private Definition definition;

    /** The related organisation */
    @ManyToOne
    private Organisation organisation;

    /** The date the data was collected */
    @DateTimeFormat(pattern = "d/M/yyyy")
    private Date collectionDate;

    /** The details of the data source */
    @Lob
    private String details;

    /** The points of contact within the organisation. */
    @ManyToMany
    private List<Person> pointsOfContact = new ArrayList<Person>();


    /**
     * Adds a point of contact.
     *
     * @param person the person
     */
    public void addPointOfContact(Person person) {
        getPointsOfContact().add(person);
        person.getDataSources().add(this);
    }

}
