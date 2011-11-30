package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class SecondaryRecord extends Record {
	
    /** The primary record. */
    @NotNull
    @ManyToOne
    private PrimaryRecord primaryRecord;

	/** The tertiary records. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "secondaryRecord")
	private Set<TertiaryRecord> tertiaryRecords = new HashSet<TertiaryRecord>();
    
}
