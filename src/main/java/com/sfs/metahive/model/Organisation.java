package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Organisation.
 */
@RooJavaBean
@RooToString
@RooEntity
@RooJson
public class Organisation {

	/** The name. */
	@NotNull
	@Column(unique = true)
	@Size(min = 1, max = 100)
	private String name;

	/** The definitions. */
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Person> people = new HashSet<Person>();
	
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
}
