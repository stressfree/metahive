package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class ConditionOfUse.
 */
@RooJavaBean
@RooToString
@RooEntity
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
	private Set<DataSource> dataSources = new HashSet<DataSource>();
	
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
