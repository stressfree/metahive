package com.sfs.metahive.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.CommentType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;

/**
 * The Class DefinitionForm.
 */
@RooJavaBean
public class CommentForm extends BackingForm {

	/** The id. */
	private long id;
	
	/** The version. */
	private long version;

	/** The comment type. */
	private CommentType commentType = CommentType.GENERAL;
	
	/** The definition. */
	@NotNull
	private Definition definition;

	/** The comment message. */
	@NotNull
	@Size(min = 1)
	private String message;

	
	/**
	 * Creates a new comment object from the form data.
	 *
	 * @param user the user
	 * @return the comment
	 */
	public final Comment newComment(Person user) {
		return buildComment(new Comment(), user);
	}
	
	/**
	 * Merge the form data with the existing comment object.
	 *
	 * @param comment the comment
	 * @param user the user
	 * @return the comment
	 */
	public final Comment mergedComment(final Comment comment,
			final Person user) {
		
		if (comment != null) {
			return buildComment(comment, user);
		}
		
		return comment;
	}
	
	/**
	 * Parses the comment and returns a comment form.
	 *
	 * @param comment the comment
	 * @return the comment form
	 */
	public static CommentForm parseComment(final Comment comment) {
				
		CommentForm commentForm = new CommentForm();
		
		if (comment != null) {
			commentForm.setId(comment.getId());
			commentForm.setMessage(trim(comment.getMessage()));
		}
		
		return commentForm;
	}
	
	/**
	 * Builds the comment object from the form data.
	 *
	 * @param comment the comment
	 * @param user the user
	 * @return the comment
	 */
	private final Comment buildComment(final Comment comment, final Person user) {
		
		if (comment != null) {
	        
			comment.setCommentType(this.commentType);
			comment.setMessage(trim(this.getMessage()));
			comment.setDefinition(this.definition);

	        comment.setPerson(user);
		}
		
		return comment;
	}
	
}
