package com.sfs.metahive.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.CommentType;
import com.sfs.metahive.model.ConditionOfUse;
import com.sfs.metahive.model.DataSource;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.web.model.DataSourceForm;

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
	@PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
    public String create(@Valid DataSourceForm dataSourceForm, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {

		Person user = loadUser(request);
		
		if (user == null) {
			// A valid user is required
			FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
			return "redirect:/definitions" + encodeUrlPathSegment(
					dataSourceForm.getDefinition().getId().toString(), request);
		}
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("dataSource", dataSourceForm);

            FlashScope.appendMessage(
            		getMessage("metahive_object_validation", DataSource.class), request);
            
            return "datasources/create";
        }
        
        DataSource dataSource = dataSourceForm.newDataSource(user);
        
        Definition definition = Definition.findDefinition(
        		dataSource.getDefinition().getId());
        Organisation organisation = Organisation.findOrganisation(
        		dataSource.getOrganisation().getId());
        
        uiModel.asMap().clear();
        
        definition.addDataSource(dataSource);
        organisation.addDataSource(dataSource);
        
        dataSource.persist();
        dataSource.flush();

        Comment comment = dataSourceForm.newComment(CommentType.CREATE, dataSource, user);
        comment.persist();
        
        FlashScope.appendMessage(
        		getMessage("metahive_create_complete", DataSource.class), request);

        return "redirect:/definitions/" 
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
    public String createForm(
    		@RequestParam(value = "definition", required = true) Long definitionId,
    		@RequestParam(value = "organisation", required = true) Long organisationId,
    		Model uiModel, HttpServletRequest request) {
		
		Person user = loadUser(request);
				
		Definition definition = Definition.findDefinition(definitionId);
		Organisation organisation = Organisation.findOrganisation(organisationId);	

		if (isInvalidOrganisation(organisation, user)) {
            FlashScope.appendMessage(
            		getMessage("metahive_no_valid_organisation"), request);
			
	        return "redirect:/definitions/" 
	        		+ encodeUrlPathSegment(definition.getId().toString(), request);
		}
		
		DataSourceForm dataSourceForm = new DataSourceForm();
		
		dataSourceForm.setDefinition(definition);
		dataSourceForm.setOrganisation(organisation);
		
        uiModel.addAttribute("dataSource", dataSourceForm);
        return "datasources/create";
    }
	
	@RequestMapping(method = RequestMethod.PUT)	
	@PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
    public String update(@Valid DataSourceForm dataSourceForm,
    		BindingResult bindingResult, Model uiModel, HttpServletRequest request) {
		
		Person user = loadUser(request);

		if (user == null) {
			// A valid user is required
			FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
			return "redirect:/definitions" + encodeUrlPathSegment(
					dataSourceForm.getDefinition().getId().toString(), request);
		}
		
		Definition definition = Definition.findDefinition(
				dataSourceForm.getDefinition().getId());
		Organisation organisation = Organisation.findOrganisation(
				dataSourceForm.getOrganisation().getId());

		if (isInvalidOrganisation(organisation, user)) {
            FlashScope.appendMessage(
            		getMessage("metahive_no_valid_organisation"), request);
			
	        return "redirect:/definitions/" 
	        		+ encodeUrlPathSegment(definition.getId().toString(), request);
		}

        // Load the existing data source
        DataSource dataSource = DataSource.findDataSource(dataSourceForm.getId());
        
        if (dataSource == null) {
        	// A valid data source was not found
        	FlashScope.appendMessage(
        			getMessage("metahive_object_not_found", DataSource.class), request);
        	return "redirect:/definitions" 
        			+ encodeUrlPathSegment(definition.getId().toString(), request);        	
        }
        
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("dataSource", dataSourceForm);

            FlashScope.appendMessage(
            		getMessage("metahive_object_validation", DataSource.class), request);
            
            return "datasources/update";
        }
        
        dataSource = dataSourceForm.mergedDataSource(dataSource, user);
        
        uiModel.asMap().clear();
        
        dataSource.merge();

        Comment comment = dataSourceForm.newComment(CommentType.MODIFY, dataSource, user);
        comment.persist();
        
        FlashScope.appendMessage(
        		getMessage("metahive_edit_complete", DataSource.class), request);

        return "redirect:/definitions/"
        		+ encodeUrlPathSegment(definition.getId().toString(), request);
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		
		DataSource dataSource = DataSource.findDataSource(id);
		
		if (dataSource == null) {
			return "redirect:/definitions";
		}
		
		DataSourceForm dataSourceForm = DataSourceForm.parseDataSource(dataSource);
		
        uiModel.addAttribute("dataSource", dataSourceForm);
        return "datasources/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {
				
        DataSource dataSource = DataSource.findDataSource(id);
        Definition definition = Definition.findDefinition(
        		dataSource.getDefinition().getId());
		
		Person user = loadUser(request);

		if (user == null) {
			// A valid user is required
			FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
			return "redirect:/definitions" + encodeUrlPathSegment(
					definition.getId().toString(), request);
		}

		DataSourceForm dataSourceForm = new DataSourceForm();
        Comment comment = dataSourceForm.newComment(CommentType.DELETE, dataSource, user);
        comment.persist();
        
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
    
    /**
     * Test if the organisation/user combination is valid.
     *
     * @param organisation the organisation
     * @param user the user
     * @return true, if successful
     */
    private boolean isInvalidOrganisation(final Organisation organisation, 
    		final Person user) {
    	
    	boolean isInvalidOrganisation = true;
    	
    	if (user != null && organisation != null) {
    		if (user.getUserRole() == UserRole.ROLE_ADMIN) {
    			isInvalidOrganisation = false;
    		} else {
    			if (user.getOrganisations() != null) {
    				for (Organisation parent : user.getOrganisations()) {
    					if (parent.getId() == organisation.getId()) {
    						isInvalidOrganisation = false;
    					}
    				}
    			}
    		}
		}
		return isInvalidOrganisation;
    }
}
