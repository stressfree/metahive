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
import com.sfs.metahive.model.ConditionOfUse;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.DataSource;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Description;
import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.Person;

/**
 * The Class DataSourceForm.
 */
@RooJavaBean
public class DataSourceForm {

	/** The id. */
	private long id;
	
	/** The version. */
	private long version;

	/** The definition. */
	@NotNull
	private Definition definition;
	
	/** The organisation. */
	private Organisation organisation;
	
	/** The details. */
	private String details;
	
	/** The condition of use. */
	@NotNull
	private ConditionOfUse conditionOfUse;
	
	/** The people. */
	private Set<Person> pointsOfContact = new HashSet<Person>();
	
	/** The log message. */
	private String logMessage;

	
	/**
	 * Creates a new data source object from the form data.
	 *
	 * @param user the user
	 * @return the definition
	 */
	public final DataSource newDataSource(Person user) {        
		return buildDataSource(new DataSource(), user);
	}
	
	/**
	 * Merge the form data with the existing data source object.
	 *
	 * @param dataSource the data source
	 * @param user the user
	 * @return the data source
	 */
	public final DataSource mergedDataSource(final DataSource dataSource,
			final Person user) {
		
		if (dataSource != null) {
			return buildDataSource(dataSource, user);
		}
		
		return dataSource;
	}
	
	/**
	 * Parses the data source and returns a data source form.
	 *
	 * @param dataSource the data source
	 * @return the data source form
	 */
	public static DataSourceForm parseDataSource(final DataSource dataSource) {
				
		DataSourceForm dataSourceForm = new DataSourceForm();
		
		if (dataSource != null) {
			dataSourceForm.setId(dataSource.getId());
			dataSourceForm.setDefinition(dataSource.getDefinition());
			dataSourceForm.setOrganisation(dataSource.getOrganisation());
			dataSourceForm.setDetails(StringUtils.strip(dataSource.getDetails()));
			dataSourceForm.setConditionOfUse(dataSource.getConditionOfUse());
			dataSourceForm.setPointsOfContact(dataSource.getPointsOfContact());
		}
		
		return dataSourceForm;
	}
	
	/**
	 * Builds the comment object from the form data.
	 *
	 * @param commentPrefix the comment prefix
	 * @param dataSource the data source
	 * @param user the user
	 * @return the definition
	 */
	public final Comment newComment(final CommentType commentType, 
			final DataSource dataSource, final Person user) {
		
		Comment comment = new Comment();
		
		if (dataSource != null && user != null) {
			comment.setCommentType(commentType);
			comment.setMessage(StringUtils.strip(this.getLogMessage()));
			comment.setDefinition(dataSource.getDefinition());
			comment.setDataSource(dataSource);
			comment.setPerson(user);
		}
		return comment;
	}
	
	/**
	 * Builds the data source object from the form data.
	 *
	 * @param dataSource the data source
	 * @param user the user
	 * @return the data source
	 */
	private final DataSource buildDataSource(final DataSource dataSource,
			final Person user) {
		
		if (dataSource != null) {

			if (dataSource.getId() == null) {
				// These relationships should only be set for a new data source
				dataSource.setDefinition(this.getDefinition());
				dataSource.setOrganisation(this.getOrganisation());
			}
			dataSource.setDetails(StringUtils.strip(this.getDetails()));
			dataSource.setConditionOfUse(this.getConditionOfUse());
			dataSource.setPointsOfContact(this.getPointsOfContact());
		}
		
		return dataSource;
	}
	
}
