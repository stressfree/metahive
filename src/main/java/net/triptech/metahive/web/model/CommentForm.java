/*******************************************************************************
 * Copyright (c) 2012 David Harrison, Triptech Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     David Harrison, Triptech Ltd - initial API and implementation
 ******************************************************************************/
package net.triptech.metahive.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.triptech.metahive.model.Comment;
import net.triptech.metahive.model.CommentType;
import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.Person;
import net.triptech.metahive.model.Record;

import org.springframework.roo.addon.javabean.RooJavaBean;


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
    private Definition definition;

    /** The record. */
    private Record record;

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
            comment.setPerson(user);

            if (this.getDefinition() != null
    				&& this.getDefinition().getId() != null
    				&& this.getDefinition().getId() > 0) {

            	Definition def = Definition.findDefinition(this.getDefinition().getId());

            	if (def != null) {
            		def.addComment(comment);
            	}
            }
            if (this.getRecord() != null
            		&& this.getRecord().getId() != null
            		&& this.getRecord().getId() > 0) {

            	Record rec = Record.findRecord(this.getRecord().getId());

            	if (rec != null) {
            		rec.addComment(comment);
            	}
            }
        }
        return comment;
    }

}
