package com.sfs.metahive.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;


@RequestMapping("/user")
@Controller
public class UserController extends BaseController {
    
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Person person, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("person", person);

            FlashScope.appendMessage(
            		getMessage("metahive_object_validation", Person.class), request);
            
            return "user/update";
        }
        
        Person user = loadUser(request);
        
        if (user != null && StringUtils.equalsIgnoreCase(
        		user.getOpenIdIdentifier(), person.getOpenIdIdentifier())) {
        	// Only save the change if the logged in user is the same
        	
        	// Set some defaults from the current user
        	person.setUserStatus(user.getUserStatus());
        	person.setUserRole(user.getUserRole());
        	person.setSearchDefinitions(user.getSearchDefinitions());
        	
            uiModel.asMap().clear();
            person.merge();
            
            FlashScope.appendMessage(getMessage("metahive_user_updated"), request);
        }
        
		return "redirect:/";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel, HttpServletRequest request) {
		
		String page = "redirect:/";
		
		Person person = loadUser(request);

		if (person != null) {
			page = "user/update";	
			uiModel.addAttribute("person", person);			
		}

        return page;
    }
	
	@RequestMapping(value = "/definitions", method = RequestMethod.GET)
    public String definitions(Model uiModel, HttpServletRequest request) {
		
		Person person = loadUser(request);
		
		if (person == null) {
			return "redirect:/records";
		}		
        uiModel.addAttribute("person", person);
                
        return "user/definitions";
    }
	
	@RequestMapping(value = "/definitions", method = RequestMethod.PUT)
    public String updateDefinitions(@Valid Person person, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("person", person);

            FlashScope.appendMessage(
            		getMessage("metahive_object_validation", Person.class), request);
            
            return "user/definitions";
        }
        
        Person user = loadUser(request);
        
        if (user != null && StringUtils.equalsIgnoreCase(
        		user.getOpenIdIdentifier(), person.getOpenIdIdentifier())) {
        	// Only save the change if the logged in user is the same
        	
        	// Set some defaults from the current user
        	person.setFirstName(user.getFirstName());
        	person.setLastName(user.getLastName());
        	person.setEmailAddress(user.getEmailAddress());
        	person.setUserStatus(user.getUserStatus());
        	person.setUserRole(user.getUserRole());
        	
            uiModel.asMap().clear();
            person.merge();
            
            FlashScope.appendMessage(getMessage("metahive_user_updated"), request);
        }
        
		return "redirect:/records";
    }
	

    @ModelAttribute("definitions")
    public Map<String, List<Definition>> populateDefinitions() {    	
    	return Definition.findGroupedDefinitions();
    }
}
