package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class DataSource.
 */
@RooJavaBean
@RooToString
@RooEntity
public class DataSource {

	/** The related condition of use. */
    @NotNull
    @ManyToOne
    private ConditionOfUse conditionOfUse;
    
	/** The related definition. */
    @NotNull
    @ManyToOne
    private Definition definition;
    
    @ManyToOne
    private Organisation organisation;
    
	/** The points of contact within the organisation. */
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Person> pointsOfContact = new HashSet<Person>();

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
