package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class DataType.
 */
@RooJavaBean
@RooToString
@RooEntity
public class DataType {

	/** The name. */
	@NotNull
	@Column(unique = true)
	@Size(min = 1, max = 100)
	private String name;

	/** The definitions. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dataType")
	private Set<Definition> definitions = new HashSet<Definition>();
}