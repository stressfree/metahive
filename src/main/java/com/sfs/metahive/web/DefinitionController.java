package com.sfs.metahive.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Description;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.web.model.DefinitionForm;

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
	 * @param httpServletRequest the http servlet request
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid DefinitionForm definitionForm, 
    		BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest) {
        
		Person user = loadUser(httpServletRequest);
		
		if (user == null) {
			// A valid user is required
			return "redirect:/definitions";
		}
		if (bindingResult.hasErrors()) {
            uiModel.addAttribute("definition", definitionForm);
            return "definitions/create";
        }		
        
        uiModel.asMap().clear();
        
        Definition definition = new Definition();
        definition.setName(definitionForm.getName());
        definition.setDataType(definitionForm.getDataType());
        definition.setCategories(definitionForm.getCategories());
             
        definition.addDescription(buildDescription(definitionForm, user));
        
        definition.persist();
        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), httpServletRequest);
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
	 * @param httpServletRequest the http servlet request
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid DefinitionForm definitionForm, 
    		BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest) {

		Person user = loadUser(httpServletRequest);
		
		if (user == null) {
			// A valid user is required
			return "redirect:/definitions";
		}
		
        // Load the existing definition
        Definition definition = Definition.findDefinition(definitionForm.getId());
        
        if (definition == null) {
        	// A valid definition was not found
			return "redirect:/definitions";        	
        }
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("definition", definitionForm);
            return "definitions/update";            
        }
        
    	definition.setDataType(definitionForm.getDataType());
    	definition.setCategories(definitionForm.getCategories());

        definition.addDescription(buildDescription(definitionForm, user));
        
        uiModel.asMap().clear();
        definition.merge();

        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), httpServletRequest);
    }
	
	/**
	 * Show the definition.
	 *
	 * @param id the id
	 * @param uiModel the ui model
	 * @return the string
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
 	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("definition", Definition.findDefinition(id));
		uiModel.addAttribute("itemId", id);

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
        uiModel.addAttribute("definition", Definition.findDefinition(id));
                
        return "definitions/update";
    }

	/**
	 * Delete the definition.
	 *
	 * @param id the id
	 * @param page the page
	 * @param size the size
	 * @param uiModel the ui model
	 * @return the string
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(
    		value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Definition.findDefinition(id).remove();
        uiModel.asMap().clear();

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
    
    /**
     * Builds the description.
     *
     * @param definitionForm the definition form
     * @param user the user
     * @return the definition
     */
    private Description buildDescription(
    		final DefinitionForm definitionForm, 
    		final Person user) {    	
    	Description description = new Description();
        description.setDescription(definitionForm.getDescription());
        description.setExampleValues(definitionForm.getExampleValues());
        description.setKeyValueDetermination(definitionForm.getKeyValueDetermination());
        description.setUser(user);   
        
        return description;
    }
	
}
