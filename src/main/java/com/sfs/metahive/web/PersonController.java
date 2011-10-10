package com.sfs.metahive.web;

import java.util.ArrayList;
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
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;


@RequestMapping("/people")
@Controller
public class PersonController {

    
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Person person, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("person", person);
            return "people/update";            
        }
        uiModel.asMap().clear();
        person.merge();
        
		return "redirect:/people";
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("person", Person.findPerson(id));
                
        return "people/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(
    		value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Person.findPerson(id).remove();
        uiModel.asMap().clear();

        return "redirect:/people";
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String list() {
		return Person.toJsonArray(Person.findAllPeople());
	}

	@RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "people/list";
    }

    @ModelAttribute("userroles")
    public Collection<UserRole> populateUserRoles() {
    	Collection<UserRole> userRoles = new ArrayList<UserRole>();
    	for (UserRole userRole : UserRole.values()) {
    		userRoles.add(userRole);
    	}        
        return userRoles;
    }
    
    @ModelAttribute("userstatuses")
    public Collection<UserStatus> populateUserStatuses() {
    	Collection<UserStatus> userStatuses = new ArrayList<UserStatus>();
    	for (UserStatus userStatus : UserStatus.values()) {
    		userStatuses.add(userStatus);
    	}        
        return userStatuses;
    }

}
