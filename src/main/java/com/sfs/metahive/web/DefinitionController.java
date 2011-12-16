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
import com.sfs.metahive.model.RecordType;
import com.sfs.metahive.web.model.CommentForm;
import com.sfs.metahive.web.model.DefinitionFilter;
import com.sfs.metahive.web.model.DefinitionForm;

import org.apache.commons.lang.StringUtils;
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
		
	/** The default page size. */
	private final static int DEFAULT_PAGE_SIZE = 50;
	
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
	@PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
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
	@PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
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
		
		Definition definition = Definition.findDefinition(id);
		
		CommentForm commentForm = new CommentForm();
		commentForm.setDefinition(definition);
		
		uiModel.addAttribute("definition", definition);
		uiModel.addAttribute("comment", commentForm);
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
    public String delete(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {

		Person user = loadUser(request);
        
		if (user == null) {
			// A valid user is required
	        FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
			return "redirect:/definitions";
		}
		
		Definition.findDefinition(id).remove();
        uiModel.asMap().clear();
        
        FlashScope.appendMessage(
        		getMessage("metahive_delete_complete", Definition.class), request);

        return "redirect:/definitions";
    }
	
	/**
	 * List the definitions.
	 *
	 * @param name the name
	 * @param category the category
	 * @param page the page
	 * @param size the size
	 * @param uiModel the ui model
	 * @param request the http servlet request
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String list(
    		@RequestParam(value = "name", required = false) String name,
    		@RequestParam(value = "category", required = false) String category,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size,
    		Model uiModel, HttpServletRequest request) {
		
		int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
		int pageNo = page == null ? 0 : page.intValue() - 1;
		
		
		DefinitionFilter filter = new DefinitionFilter();		
		filter.setEncoding(request.getCharacterEncoding());
		
		if (StringUtils.isNotBlank(name)) {
			filter.setName(name);
		}
		if (StringUtils.isNotBlank(category) 
				&& !StringUtils.equalsIgnoreCase(category, "-")) {
			filter.setCategory(category);
		}
		
        uiModel.addAttribute("definitions", Definition.findDefinitionEntries(
        		filter, pageNo * sizeNo, sizeNo));
            
        float nrOfPages = (float) Definition.countDefinitions(filter) / sizeNo;

        uiModel.addAttribute("page", pageNo + 1);
        uiModel.addAttribute("size", sizeNo);    
        uiModel.addAttribute("filter", filter);
        uiModel.addAttribute("maxPages", 
        		(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) 
        		? nrOfPages + 1 : nrOfPages));
        
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
     * Populate record types.
     *
     * @return the collection
     */
    @ModelAttribute("recordTypes")
    public Collection<RecordType> populateRecordTypes() {        
        return RecordType.findAllRecordTypes();
    }
    
    /**
     * Populate the data types.
     *
     * @return the data types
     */
    @ModelAttribute("datatypes")
    public Collection<DataType> populateDataTypes() {
    	Collection<DataType> dataTypes = new ArrayList<DataType>();
    	for (DataType dataType : DataType.values()) {
    		dataTypes.add(dataType);
    	}        
        return dataTypes;
    }
    
}
