/*
 * 
 */
package com.sfs.metahive.web.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.CommentType;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.DefinitionType;
import com.sfs.metahive.model.Description;
import com.sfs.metahive.model.KeyValueGenerator;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Applicability;
import com.sfs.metahive.model.UserRole;

/**
 * The Class DefinitionForm.
 */
@RooJavaBean
public class DefinitionForm extends BackingForm {

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
	private DefinitionType definitionType = DefinitionType.STANDARD;
	
	/** The related definition. */
	private List<Definition> relatedDefinitions = new ArrayList<Definition>();
		
	/** The data type. */
	@NotNull
	private DataType dataType = DataType.TYPE_STRING;
	
	/** The key value generator. */
	@NotNull
	private KeyValueGenerator keyValueGenerator = KeyValueGenerator.NEWEST;
	
	/** The key value access. */
	@NotNull
	private UserRole keyValueAccess = UserRole.ANONYMOUS;

	/** The category. */
	@NotNull
	private Category category;
	
	/** The applicability. */
	@NotNull
	private Applicability applicability;
	
	/** The unit of measure. */
	private String unitOfMeasure;

	/** The description. */
	private String description;
		
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
			definitionForm.setName(trim(definition.getName()));
			definitionForm.setDefinitionType(definition.getDefinitionType());
			definitionForm.setRelatedDefinitions(definition.getRelatedDefinitions());
			definitionForm.setCategory(definition.getCategory());
			definitionForm.setApplicability(definition.getApplicability());
			definitionForm.setDataType(definition.getDataType());
			definitionForm.setKeyValueGenerator(definition.getKeyValueGenerator());
			definitionForm.setKeyValueAccess(definition.getKeyValueAccess());
			
			if (definition.getDescription() != null) {
				Description dsc = definition.getDescription();
				definitionForm.setUnitOfMeasure(dsc.getUnitOfMeasure());
				definitionForm.setDescription(trim(dsc.getDescription()));
				definitionForm.setExampleValues(trim(dsc.getExampleValues()));
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
			comment.setMessage(trim(this.getLogMessage()));
			comment.setDefinition(definition);
			if (definition.getDescription() != null) {
				comment.setDescriptionId(definition.getDescription().getId());
			}
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
	        
			definition.setName(trim(this.getName()));
	        definition.setDefinitionType(this.getDefinitionType());
	        definition.setRelatedDefinitions(this.getRelatedDefinitions());
	        definition.setDataType(this.getDataType());
	        definition.setCategory(this.getCategory());
	        definition.setKeyValueGenerator(this.getKeyValueGenerator());
	        definition.setKeyValueAccess(this.getKeyValueAccess());
	        definition.setApplicability(this.getApplicability());

	        description.setUnitOfMeasure(this.getUnitOfMeasure());
	        description.setDescription(trim(this.getDescription()));
	        description.setExampleValues(trim(this.getExampleValues()));
	        description.setPerson(user);   
	        
	        definition.addDescription(description);
		}
		
		return definition;
	}
	
}
