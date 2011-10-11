package com.sfs.metahive.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;


@RequestMapping("/user")
@Controller
public class UserController {

    
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Person person, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("person", person);
            return "user/update";            
        }
        
        Person user = loadPerson(httpServletRequest);
        
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
        }
        
		return "redirect:/";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel, HttpServletRequest request) {
		
		String page = "redirect:/";
		
		Person person = loadPerson(request);

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

	/**
	 * Load the person from the request.
	 *
	 * @param request the request
	 * @return the person
	 */
	private Person loadPerson(final HttpServletRequest request) {
		
		Person person = null;
		
		if (request.getUserPrincipal() != null
				&& StringUtils.isNotBlank(request.getUserPrincipal().getName())) {
			
			List<Person> people = Person.findPeopleByOpenIdIdentifier(
					request.getUserPrincipal().getName()).getResultList();
			
			person = people.size() == 0 ? null : people.get(0);
		}
		return person;
	}
	
}
