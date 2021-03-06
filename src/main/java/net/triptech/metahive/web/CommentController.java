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
package net.triptech.metahive.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import net.triptech.metahive.FlashScope;
import net.triptech.metahive.model.Comment;
import net.triptech.metahive.model.Person;
import net.triptech.metahive.web.model.CommentForm;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * The Class CommentController.
 */
@RequestMapping("/comments")
@Controller
public class CommentController extends BaseController {

    /**
     * Creates the comment.
     *
     * @param commentForm the comment form
     * @param bindingResult the binding result
     * @param uiModel the ui model
     * @param request the http servlet request
     * @return the string
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_EDITOR','ROLE_ADMIN')")
    public String create(@Valid CommentForm commentForm,
            BindingResult bindingResult, Model uiModel,
            HttpServletRequest request) {

        Person user = loadUser(request);

        String redirect = "redirect:/";

        if (user == null) {
            // A valid user is required
            FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
        }

        if (commentForm.getDefinition() != null || commentForm.getRecord() != null) {

        	if (bindingResult.hasErrors()) {
        		FlashScope.appendMessage(
        				getMessage("metahive_object_validation", Comment.class), request);
        	} else {
        		uiModel.asMap().clear();

        		Comment comment = commentForm.newComment(user);

        		if (comment.getDefinition() != null) {
        			redirect = "redirect:/definitions/" + encodeUrlPathSegment(
        					comment.getDefinition().getId().toString(), request);
        		}
        		if (comment.getRecord() != null) {
            		redirect = "redirect:/records/" + encodeUrlPathSegment(
            				comment.getRecord().getId().toString(), request);
        		}
        		comment.persist();
        		comment.flush();

        		FlashScope.appendMessage(
        				getMessage("metahive_create_complete", Comment.class), request);
        	}
        }

        return redirect;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
            HttpServletRequest request) {

        Comment comment = Comment.findComment(id);

        String redirect = "redirect:/";

        if (comment.getDefinition() != null) {
            redirect = "redirect:/definitions/" + encodeUrlPathSegment(
            		comment.getDefinition().getId().toString(), request);
        }
        if (comment.getRecord() != null) {
        	redirect = "redirect:/records/" + encodeUrlPathSegment(
            		comment.getRecord().getId().toString(), request);
        }

        comment.remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
                getMessage("metahive_delete_complete", Comment.class), request);

        return redirect;
    }
}
