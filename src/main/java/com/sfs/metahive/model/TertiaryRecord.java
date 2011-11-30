package com.sfs.metahive.model;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class TertiaryRecord extends Record {
	
    /** The secondary record. */
    @NotNull
    @ManyToOne
    private SecondaryRecord secondaryRecord;
    
}
