package com.sfs.metahive.model;

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
    
}
