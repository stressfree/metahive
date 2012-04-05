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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;

/**
 * The Class ConditionOfUse.
 */
@RooJavaBean
@RooJpaActiveRecord
@RooJson
public class ConditionOfUse {

    /** The name. */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String name;

    /** The usage details. */
    @Lob
    private String details;

    /** The definitions. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conditionOfUse")
    private List<DataSource> dataSources = new ArrayList<DataSource>();

    /**
     * Find all conditions of use ordered by their name.
     *
     * @return an ordered list of conditions of use
     */
    public static List<ConditionOfUse> findAllConditionOfUses() {
        return entityManager().createQuery("SELECT o FROM ConditionOfUse o ORDER BY name",
                ConditionOfUse.class).getResultList();
    }
}
