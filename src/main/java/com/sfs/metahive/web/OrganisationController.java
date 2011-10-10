package com.sfs.metahive.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.Person;


@RequestMapping("/organisations")
@Controller
public class OrganisationController {

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Organisation organisation, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("organisation", organisation);
            return "organisations/create";
        }
        uiModel.asMap().clear();
        organisation.persist();
        return "redirect:/organisations";
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("organisation", new Organisation());
        return "organisations/create";
    }
    
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Organisation organisation, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("organisation", organisation);
            return "organisations/update";            
        }
        uiModel.asMap().clear();
        organisation.merge();
        
		return "redirect:/organisations";
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("organisation", Organisation.findOrganisation(id));
                
        return "organisations/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(
    		value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Organisation.findOrganisation(id).remove();
        uiModel.asMap().clear();

        return "redirect:/organisations";
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String list() {
		return Organisation.toJsonArray(Organisation.findAllOrganisations());
	}

	@RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "organisations/list";
    }

    @ModelAttribute("people")
    public Collection<Person> populatePeople() {    	
    	return Person.findAllPeople();
    }
}
