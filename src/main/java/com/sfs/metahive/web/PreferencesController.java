package com.sfs.metahive.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.MetahivePreferences;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/preferences")
@Controller
public class PreferencesController extends BaseController {
		
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Valid MetahivePreferences metahivePreferences, 
    		BindingResult bindingResult, Model uiModel, HttpServletRequest request) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("metahivePreferences", metahivePreferences);

            FlashScope.appendMessage(getMessage("metahive_object_validation", 
            		MetahivePreferences.class), request);
            
            return "preferences/update";
        }
        
    	uiModel.asMap().clear();
    	if (metahivePreferences.getId() != null) {
    		// Updating existing preferences
    		metahivePreferences.merge();
    	} else {
    		// No preferences exist yet
    		metahivePreferences.persist();
    		metahivePreferences.flush();
    	}
    	FlashScope.appendMessage(getMessage("metahive_preferences_edited"), request);
        
        return "redirect:/preferences";
    }

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateForm(Model uiModel) {
        uiModel.addAttribute("metahivePreferences", this.loadPreferences());
        return "preferences/update";
    }
	
    @ModelAttribute("definitions")
    public Collection<Definition> populateDefinitions() {    	
    	return Definition.findAllDefinitions();
    }

}
