package com.sfs.metahive.web.model;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.model.Definition;

/**
 * The Class RecordFilterVector.
 */
@RooJavaBean
public class RecordFilterVector {

	/** The definition. */
	private Definition definition;

	/** The criteria. */
	private String criteria;

	/** The constraint. */
	private String constraint;

}
