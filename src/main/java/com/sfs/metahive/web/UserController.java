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

import com.sfs.metahive.model.Principal;
import com.sfs.metahive.model.UserRole;


@RequestMapping("/users")
@Controller
public class UserController {

    
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Principal principal, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {

		System.out.println("Testing");
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("principal", principal);
            return "users/update";            
        }
        uiModel.asMap().clear();
        principal.merge();
        
		return "redirect:/users";
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("principal", Principal.findPrincipal(id));
                
        return "users/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(
    		value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Principal.findPrincipal(id).remove();
        uiModel.asMap().clear();

        return "redirect:/users";
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String list() {
		return Principal.toJsonArray(Principal.findAllPrincipals());
	}

	@RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "users/list";
    }

    @ModelAttribute("userroles")
    public Collection<UserRole> populateUserRoles() {
    	Collection<UserRole> userRoles = new ArrayList<UserRole>();
    	for (UserRole userRole : UserRole.values()) {
    		userRoles.add(userRole);
    	}        
        return userRoles;
    }
}
