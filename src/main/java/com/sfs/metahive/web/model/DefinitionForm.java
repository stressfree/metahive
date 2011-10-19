package com.sfs.metahive.web.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.CommentType;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Description;
import com.sfs.metahive.model.Person;

/**
 * The Class DefinitionForm.
 */
@RooJavaBean
public class DefinitionForm {

	/** The id. */
	private long id;
	
	/** The version. */
	private long version;
	
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
	
	/** The log message. */
	private String logMessage;

	
	/**
	 * Creates a new definition object from the form data.
	 *
	 * @param user the user
	 * @return the definition
	 */
	public final Definition newDefinition(Person user) {        
		return buildDefinition(new Definition(), user);
	}
	
	/**
	 * Merge the form data with the existing definition object.
	 *
	 * @param definition the definition
	 * @param user the user
	 * @return the definition
	 */
	public final Definition mergedDefinition(final Definition definition,
			final Person user) {
		
		if (definition != null) {
			return buildDefinition(definition, user);
		}
		
		return definition;
	}
	
	/**
	 * Parses the definition and returns a definition form.
	 *
	 * @param definition the definition
	 * @return the definition form
	 */
	public static DefinitionForm parseDefinition(final Definition definition) {
				
		DefinitionForm definitionForm = new DefinitionForm();
		
		if (definition != null) {
			definitionForm.setId(definition.getId());
			definitionForm.setName(StringUtils.strip(definition.getName()));
			definitionForm.setCategories(definition.getCategories());
			definitionForm.setDataType(definition.getDataType());
			
			if (definition.getDescription() != null) {
				Description dsc = definition.getDescription();
				definitionForm.setDescription(StringUtils.strip(dsc.getDescription()));
				definitionForm.setExampleValues(
						StringUtils.strip(dsc.getExampleValues()));
				definitionForm.setKeyValueDetermination(
						StringUtils.strip(dsc.getKeyValueDetermination()));
			}
		}
		
		return definitionForm;
	}
	
	/**
	 * Builds the comment object from the form data.
	 *
	 * @param commentType the comment type
	 * @param definition the definition
	 * @param user the user
	 * @return the definition
	 */
	public final Comment newComment(final CommentType commentType, 
			final Definition definition, final Person user) {
		
		Comment comment = new Comment();
		
		if (definition != null && description != null && user != null) {
			comment.setCommentType(commentType);
			comment.setMessage(StringUtils.strip(this.getLogMessage()));
			comment.setDefinition(definition);
			comment.setDescription(definition.getDescription());
			comment.setPerson(user);
		}
		return comment;
	}
	
	/**
	 * Builds the definition object from the form data.
	 *
	 * @param definition the definition
	 * @param user the user
	 * @return the definition
	 */
	private final Definition buildDefinition(final Definition definition,
			final Person user) {
		
		if (definition != null) {

	    	Description description = new Description();
	        
			definition.setName(StringUtils.strip(this.getName()));
	        definition.setDataType(this.getDataType());
	        definition.setCategories(this.getCategories());
	            	
	        description.setDescription(StringUtils.strip(this.getDescription()));
	        description.setExampleValues(StringUtils.strip(this.getExampleValues()));
	        description.setKeyValueDetermination(
	        		StringUtils.strip(this.getKeyValueDetermination()));
	        description.setPerson(user);   
	        
	        definition.addDescription(description);
		}
		
		return definition;
	}
	
}
