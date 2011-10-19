package com.sfs.metahive.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.CommentType;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.web.model.DefinitionForm;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * The Class DefinitionController.
 */
@RequestMapping("/definitions")
@Controller
public class DefinitionController extends BaseController {

	/**
	 * Creates the definition.
	 *
	 * @param definitionForm the definition form
	 * @param bindingResult the binding result
	 * @param uiModel the ui model
	 * @param request the http servlet request
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String create(@Valid DefinitionForm definitionForm, 
    		BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest request) {
        
		Person user = loadUser(request);
		
		if (user == null) {
			// A valid user is required
			FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
			return "redirect:/definitions";
		}
		if (bindingResult.hasErrors()) {
            uiModel.addAttribute("definition", definitionForm);
            FlashScope.appendMessage(
        			getMessage("metahive_object_validation", Definition.class), request);
            return "definitions/create";
        }		
        
        uiModel.asMap().clear();
        
        Definition definition = definitionForm.newDefinition(user);        
        definition.persist();

        Comment comment = definitionForm.newComment(CommentType.CREATE, definition, user);
        comment.persist();
        
        FlashScope.appendMessage(
        		getMessage("metahive_create_complete", Definition.class), request);
        
        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }

	/**
	 * Creates the form.
	 *
	 * @param uiModel the ui model
	 * @return the string
	 */
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("definition", new DefinitionForm());        
        return "definitions/create";
    }
    
	/**
	 * Update the definition.
	 *
	 * @param definition the definition
	 * @param bindingResult the binding result
	 * @param uiModel the ui model
	 * @param request the http servlet request
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String update(@Valid DefinitionForm definitionForm, 
    		BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest request) {

		Person user = loadUser(request);
        
		if (user == null) {
			// A valid user is required
	        FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
			return "redirect:/definitions";
		}
		
        // Load the existing definition
        Definition definition = Definition.findDefinition(definitionForm.getId());
        
        if (definition == null) {
        	// A valid definition was not found
        	FlashScope.appendMessage(
        			getMessage("metahive_object_not_found", Definition.class), request);
			return "redirect:/definitions";        	
        }
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("definition", definitionForm);
            FlashScope.appendMessage(
        			getMessage("metahive_object_validation", Definition.class), request);
            return "definitions/update";            
        }

    	definition = definitionForm.mergedDefinition(definition, user);
    	        
        uiModel.asMap().clear();
        definition.merge();
        
        Comment comment = definitionForm.newComment(CommentType.MODIFY, definition, user);
        comment.persist();
        
        FlashScope.appendMessage(
        		getMessage("metahive_edit_complete", Definition.class), request);

        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }
	
	/**
	 * Show the definition.
	 *
	 * @param id the id
	 * @param uiModel the ui model
	 * @return the string
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
 	public String show(@PathVariable("id") Long id, Model uiModel,
 			HttpServletRequest request) {

		Person user = loadUser(request);
		
		Collection<Organisation> organisations = new ArrayList<Organisation>();
		if (user != null) {
			if (user.getUserRole() == UserRole.ROLE_ADMIN) {
				organisations = Organisation.findAllOrganisations();
			} else {
				organisations = user.getOrganisations();
			}
		}
		
		uiModel.addAttribute("definition", Definition.findDefinition(id));
		uiModel.addAttribute("itemId", id);
		uiModel.addAttribute("organisations", organisations);
		
		return "definitions/show";
	}

	/**
	 * Update the definition form.
	 *
	 * @param id the id
	 * @param uiModel the ui model
	 * @return the string
	 */
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		Definition definition = Definition.findDefinition(id);
		
		if (definition == null) {
			return "redirect:/definitions";
		}
		DefinitionForm definitionForm = DefinitionForm.parseDefinition(definition);
		
        uiModel.addAttribute("definition", definitionForm);
                
        return "definitions/update";
    }

	/**
	 * Delete the definition.
	 *
	 * @param id the id
	 * @param page the page
	 * @param size the size
	 * @param uiModel the ui model
	 * @param request the http servlet request
	 * @return the string
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, @RequestParam(
    		value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel,
    		HttpServletRequest request) {
		Definition.findDefinition(id).remove();
        uiModel.asMap().clear();
        
        FlashScope.appendMessage(
        		getMessage("metahive_delete_complete", Definition.class), request);

        return "redirect:/definitions";
    }
	
	/**
	 * List the definitions.
	 *
	 * @param page the page
	 * @param size the size
	 * @param uiModel the ui model
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, 
    		Model uiModel) {
		
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("definitions", Definition.findDefinitionEntries(
            		page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Definition.countDefinitions() / sizeNo;
            uiModel.addAttribute("maxPages", (int) (
            		(nrOfPages > (int) nrOfPages || nrOfPages == 0.0) 
            		? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("definitions", Definition.findAllDefinitions());
        }
        return "definitions/list";
    }
	
	/**
     * Populate categories.
     *
     * @return the collection
     */
    @ModelAttribute("categories")
    public Collection<Category> populateCategories() {        
        return Category.findAllCategorys();
    }
    
    /**
     * Populate the data types.
     *
     * @return the data types
     */
    @ModelAttribute("datatypes")
    public Collection<DataType> populateDataTypes() {        
        return DataType.findAllDataTypes();
    }
	
}
