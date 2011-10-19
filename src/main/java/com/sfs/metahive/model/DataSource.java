package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    
    /** The related organisation **/
    @ManyToOne
    private Organisation organisation;
    
    /** The details of the data source **/
    @Lob    
    private String details;
    
	/** The points of contact within the organisation. */
	@ManyToMany
	private Set<Person> pointsOfContact = new HashSet<Person>();
	
	/** The associated comment by the person who created the data source */
	@OneToOne
	private Comment comment;

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
