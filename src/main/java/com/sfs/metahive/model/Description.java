package com.sfs.metahive.model;

import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Description.
 */
@RooJavaBean
@RooToString
@RooEntity
public class Description {

    /** The related definition. */
    @NotNull
    @ManyToOne
    private Definition definition;

    /** The description. */
    @Lob
    private String description;

    /** The example values. */
    private String exampleValues;

	/** The person who created the description. */
	@NotNull
	@ManyToOne
	private Person person;
	
    /** The created timestamp. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date created;
}
