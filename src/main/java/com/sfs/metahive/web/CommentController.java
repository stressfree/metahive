package com.sfs.metahive.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.web.model.CommentForm;

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
		
		Definition definition = Definition.findDefinition(
				commentForm.getDefinition().getId());
		
		if (user == null) {
			// A valid user is required
			FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
		}
		if (bindingResult.hasErrors()) {
            FlashScope.appendMessage(
        			getMessage("metahive_object_validation", Comment.class), request);
        } else {
        	uiModel.asMap().clear();
        
        	Comment comment = commentForm.newComment(user);
        	definition.addComment(comment);
        	comment.persist();
        
        	FlashScope.appendMessage(
        			getMessage("metahive_create_complete", Comment.class), request);
        }
		
        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {		
        Comment comment = Comment.findComment(id);
        Definition definition = comment.getDefinition();
        comment.remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
        		getMessage("metahive_delete_complete", Comment.class), request);

        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }
}
