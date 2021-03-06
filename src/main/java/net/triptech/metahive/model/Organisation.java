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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;

/**
 * The Class Organisation.
 */
@RooJavaBean
@RooJpaActiveRecord
@RooJson
public class Organisation {

    /** The name. */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String name;

    /** The people associated with the organisation. */
    @ManyToMany
    private List<Person> people = new ArrayList<Person>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    private List<DataSource> dataSources = new ArrayList<DataSource>();

    /** The related submissions. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    @OrderBy("created ASC")
    private List<Submission> submissions = new ArrayList<Submission>();


    /**
     * Gets the list of people names.
     *
     * @return the people names
     */
    public String getPeopleNames() {
        StringBuilder sb = new StringBuilder();

        if (people != null) {
            for (Person person : people) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(person.getFormattedName());
            }
        }
        return sb.toString();
    }

    /**
     * Adds the person.
     *
     * @param person the person
     */
    public void addPerson(Person person) {
        getPeople().add(person);
        person.getOrganisations().add(this);
    }

    /**
     * Adds a data source.
     *
     * @param dataSource the data source
     */
    public final void addDataSource(DataSource dataSource) {
        dataSource.setOrganisation(this);
        getDataSources().add(dataSource);
    }

    /**
     * Find an ordered list of organisations.
     *
     * @return an ordered list of organisations
     */
    public static List<Organisation> findAllOrganisations() {
        return entityManager().createQuery("SELECT o FROM Organisation o ORDER BY name",
                Organisation.class).getResultList();
    }

}
