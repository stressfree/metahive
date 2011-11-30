package com.sfs.metahive.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class PrimaryRecord extends Record {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "primaryRecord")
	private Set<SecondaryRecord> secondaryRecords = new HashSet<SecondaryRecord>();
	
}
