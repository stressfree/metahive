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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Category.
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Category {

    /** The name. */
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String name;

    /** The definitions. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Definition> definitions = new ArrayList<Definition>();


    /**
     * Find all of the categories ordered by name.
     *
     * @return an ordered list of categories
     */
    public static List<Category> findAllCategorys() {
        return entityManager().createQuery("SELECT c FROM Category c ORDER BY name",
                Category.class).getResultList();
    }
}
