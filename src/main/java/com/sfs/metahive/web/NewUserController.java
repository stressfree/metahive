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

import com.sfs.metahive.model.Principal;
import com.sfs.metahive.model.UserRole;


@RequestMapping("/newuser")
@Controller
public class NewUserController {

    
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Principal principal, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("principal", principal);
            return "users/update";            
        }
        uiModel.asMap().clear();
        principal.merge();
        
		return "redirect:/";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel, HttpServletRequest request) {
		
		String page = "redirect:/";
		
		if (request.getUserPrincipal() != null
				&& StringUtils.isNotBlank(request.getUserPrincipal().getName())) {
			
			List<Principal> principals = Principal.findPrincipalsByOpenIdIdentifier(
					request.getUserPrincipal().getName()).getResultList();
			
			Principal principal = principals.size() == 0 ? null : principals.get(0);

			if (principal != null && principal.getUserRole() == UserRole.ROLE_NEWUSER) {
				// Reset the first, last and email address values
				principal.setFirstName("");
				principal.setLastName("");
				principal.setEmailAddress("");
				uiModel.addAttribute("principal", principal);
				
				page = "newuser/update";
			}
		}		
        return page;
    }

	
}
