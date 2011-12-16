package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class RecordType.
 */
@RooJavaBean
@RooToString
@RooEntity
@RooJson
public class RecordType {

	/** The name. */
	@NotNull
	@Column(unique = true)
	@Size(min = 1, max = 100)
	private String name;

	/** The definitions. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recordType")
	private Set<Definition> definitions = new HashSet<Definition>();


	/**
	 * Find all of the categories ordered by name.
	 * 
	 * @return an ordered list of categories
	 */
	public static List<RecordType> findAllRecordTypes() {
        return entityManager().createQuery("SELECT r FROM RecordType r ORDER BY name", 
        		RecordType.class).getResultList();
    }
}
