package com.sfs.metahive.web.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.DataType;

/**
 * The Class DefinitionForm.
 */
@RooJavaBean
public class DefinitionForm {

	/** The name. */
	@NotNull
	@Size(min = 1, max = 100)
	private String name;

	/** The data type. */
	@NotNull
	private DataType dataType;
	
	/** The categories. */
	private Set<Category> categories = new HashSet<Category>();

	/** The description. */
	private String description;
	
	/** The key value determination. */
	private String keyValueDetermination;
	
	/** The example values. */
	private String exampleValues;
	
}
