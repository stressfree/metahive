package com.sfs.metahive.web;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.UserRole;


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
        	
        	if (user.getUserRole() == UserRole.ROLE_NEWUSER) {
        		person.setUserRole(UserRole.ROLE_USER);
        	}
        	
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
			if (person.getUserRole() == UserRole.ROLE_NEWUSER) {
				// Reset the first, last and email address values
				person.setFirstName("");
				person.setLastName("");
				person.setEmailAddress("");
			}
			page = "user/update";	
			uiModel.addAttribute("person", person);			
		}

        return page;
    }
	
}
