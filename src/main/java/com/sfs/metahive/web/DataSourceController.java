package com.sfs.metahive.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.ConditionOfUse;
import com.sfs.metahive.model.DataSource;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Organisation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/datasources")
@Controller
public class DataSourceController extends BaseController {
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String create(@Valid DataSource dataSource, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("dataSource", dataSource);

            FlashScope.appendMessage(
            		getMessage("metahive_object_validation", DataSource.class), request);
            
            return "datasources/create";
        }
        
        Definition definition = Definition.findDefinition(
        		dataSource.getDefinition().getId());
        Organisation organisation = Organisation.findOrganisation(
        		dataSource.getOrganisation().getId());
        
        uiModel.asMap().clear();
        
        definition.addDataSource(dataSource);
        organisation.addDataSource(dataSource);
        
        dataSource.persist();

        FlashScope.appendMessage(
        		getMessage("metahive_create_complete", DataSource.class), request);

        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String createForm(
    		@RequestParam(value = "definition", required = true) Long definitionId,
    		@RequestParam(value = "organisation", required = true) Long organisationId,
    		Model uiModel, HttpServletRequest request) {
		
		Person user = loadUser(request);

		Definition definition = Definition.findDefinition(definitionId);
		Organisation organisation = Organisation.findOrganisation(organisationId);

		boolean validOrganisation = false;
		
		if (user != null && user.getOrganisations() != null) {
			for (Organisation parent : user.getOrganisations()) {
				if (parent.getId() == organisation.getId()) {
					validOrganisation = true;
				}
			}
		}

		if (!validOrganisation) {
            FlashScope.appendMessage(
            		getMessage("metahive_no_valid_organisation"), request);
			
	        return "redirect:/definitions/" 
	        		+ encodeUrlPathSegment(definition.getId().toString(), request);
		}
		
		DataSource dataSource = new DataSource();
		
		dataSource.setDefinition(definition);
		dataSource.setOrganisation(organisation);
		
        uiModel.addAttribute("dataSource", dataSource);
        return "datasources/create";
    }
	
	@RequestMapping(method = RequestMethod.PUT)	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String update(@Valid DataSource dataSource, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
		Person user = loadUser(request);

		Definition definition = Definition.findDefinition(
				dataSource.getDefinition().getId());
		Organisation organisation = Organisation.findOrganisation(
				dataSource.getOrganisation().getId());

		boolean validOrganisation = false;
		
		if (user != null && user.getOrganisations() != null) {
			for (Organisation parent : user.getOrganisations()) {
				if (parent.getId() == organisation.getId()) {
					validOrganisation = true;
				}
			}
		}

		if (!validOrganisation) {
            FlashScope.appendMessage(
            		getMessage("metahive_no_valid_organisation"), request);
			
	        return "redirect:/definitions/" 
	        		+ encodeUrlPathSegment(definition.getId().toString(), request);
		}
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("dataSource", dataSource);

            FlashScope.appendMessage(
            		getMessage("metahive_object_validation", DataSource.class), request);
            
            return "datasources/update";
        }
        
        uiModel.asMap().clear();
        definition.addDataSource(dataSource);        
        dataSource.merge();
        
        FlashScope.appendMessage(
        		getMessage("metahive_edit_complete", DataSource.class), request);

        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("dataSource", DataSource.findDataSource(id));
        return "datasources/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {
        DataSource dataSource = DataSource.findDataSource(id);
        Definition definition = Definition.findDefinition(
        		dataSource.getDefinition().getId());
		
		dataSource.remove();
                
        uiModel.asMap().clear();

        FlashScope.appendMessage(
        		getMessage("metahive_delete_complete", DataSource.class), request);

        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }
	
    @ModelAttribute("conditionsOfUse")
    public Collection<ConditionOfUse> populateConditionsOfUse() {
    	return ConditionOfUse.findAllConditionOfUses();
    }
}
