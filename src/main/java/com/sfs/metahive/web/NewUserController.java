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


@RequestMapping("/newuser")
@Controller
public class NewUserController {

    
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Person person, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("person", person);
            return "users/update";            
        }
        uiModel.asMap().clear();
        person.merge();
        
		return "redirect:/";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel, HttpServletRequest request) {
		
		String page = "redirect:/";
		
		if (request.getUserPrincipal() != null
				&& StringUtils.isNotBlank(request.getUserPrincipal().getName())) {
			
			List<Person> people = Person.findPeopleByOpenIdIdentifier(
					request.getUserPrincipal().getName()).getResultList();
			
			Person person = people.size() == 0 ? null : people.get(0);

			if (person != null && person.getUserRole() == UserRole.ROLE_NEWUSER) {
				// Reset the first, last and email address values
				person.setFirstName("");
				person.setLastName("");
				person.setEmailAddress("");
				uiModel.addAttribute("person", person);
				
				page = "newuser/update";
			}
		}		
        return page;
    }

	
}
