package com.sfs.metahive.model;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Index;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;


/**
 * The Class SubmittedField.
 */
@RooJavaBean
@RooToString
@RooEntity
public class SubmittedField {
		
	/** The record. */
	@ManyToOne
	@NotNull
	private Record record;
	
	/** The definition. */
	@ManyToOne
	@NotNull
	@Index(name="indexDefinition")
	private Definition definition;
	
	/** The submission. */
	@ManyToOne
	@NotNull
	private Submission submission;
	
	/** The primary record id. */
	@NotNull
	@Index(name="indexPrimaryRecordId")
	private String primaryRecordId;
	
	/** The secondary record id. */
	@Index(name="indexSecondaryRecordId")
	private String secondaryRecordId;
	
	/** The tertiary record id. */
	@Index(name="indexTertiaryRecordId")
	private String tertiaryRecordId;
	
	/** The value. */
	@NotNull
	@Size(min = 1, max = 255)
	private String value;
	
}
