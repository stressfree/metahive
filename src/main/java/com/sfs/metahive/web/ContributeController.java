package com.sfs.metahive.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.messaging.JmsRecalculateRequest;
import com.sfs.metahive.model.DataGrid;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.Submission;
import com.sfs.metahive.model.SubmittedField;
import com.sfs.metahive.model.ValidatedDataGrid;

@RequestMapping("/contribute")
@Controller
public class ContributeController extends BaseController {

	@Autowired
	private transient JmsTemplate metahiveContributionTemplate;
	
	@Autowired
	private transient JmsTemplate keyValueGenerationTemplate;
	
	/**
	 * Begin the contribute data process.
	 *
	 * @param uiModel the ui model
	 * @param request the http servlet request
	 * @return the string
	 */
	@PreAuthorize("hasAnyRole('ROLE_CONTRIBUTOR','ROLE_EDITOR','ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
    public String begin(@RequestParam(value = "id", required = false) Long id,
    		Model uiModel, HttpServletRequest request) {		
				
		Person user = loadUser(request);
		
		String page = "contribute/noorganisation";

		Definition uniqueIdDef = Definition.findUniqueIdentifierDefinition();
		
		if (uniqueIdDef == null) {
			page = "contribute/nouniqueid";			
		}
		
		if (user != null && user.getOrganisations() != null && uniqueIdDef != null) {
			Organisation organisation = null;
			if (user.getOrganisations().size() == 1) {
				organisation = user.getOrganisations().iterator().next();
			}			
			if (user.getOrganisations().size() > 1) {
				if (id != null) {
					for (Organisation userOrg : user.getOrganisations()) {
						if (userOrg.getId().compareTo(id) == 0) {
							organisation = userOrg;
						}
					}
				} else {
					uiModel.addAttribute("organisations", user.getOrganisations());
					page = "contribute/select";	
				}
			}

			if (organisation != null) {
				
				page = "contribute/nodefinitions";				
				
				Map<String, List<Definition>> definitions = Definition.groupDefinitions(
						Definition.findSubmissionDefinitions(organisation));
				
				if (definitions != null && definitions.size() > 0) {
					uiModel.addAttribute("organisationId", organisation.getId());
					uiModel.addAttribute("uniqueIdDef", uniqueIdDef);
					uiModel.addAttribute("definitions", definitions);
					page = "contribute/begin";
				}
			}
		}
		
        return page;
    }

	@PreAuthorize("hasAnyRole('ROLE_CONTRIBUTOR','ROLE_EDITOR','ROLE_ADMIN')")
	@RequestMapping(value = "/submission", method = RequestMethod.GET)
	public String submission(@RequestParam(value = "id", required = true) Long id,
			Model uiModel, HttpServletRequest request) throws Exception {
		
		uiModel.addAttribute("organisationId", id);
		
		return "contribute/submission";
	}
	
	@RequestMapping(value = "/preview", method = RequestMethod.POST)
	public String preview(
			@RequestParam(value = "previewData", required = true) String data,
			Model uiModel, HttpServletRequest request) throws Exception {
	  				
		uiModel.addAttribute("validatedDataGrid", validateGrid(data));
		
		return "contribute/preview";
	}

	@PreAuthorize("hasAnyRole('ROLE_CONTRIBUTOR','ROLE_EDITOR','ROLE_ADMIN')")
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public String complete(
			@RequestParam(value = "submissionId", required = true) Long id,
			@RequestParam(value = "submissionData", required = true) String data,
			Model uiModel, HttpServletRequest request) throws Exception {

		Person user = loadUser(request);

		String page = "redirect:/contribute";
		
		if (user != null && user.getOrganisations() != null) {
			for (Organisation userOrg : user.getOrganisations()) {				
				if (userOrg.getId().compareTo(id) == 0) {
					// Save the submission and add it to the processing queue
					Submission submission = new Submission();
					submission.setPerson(user);
					submission.setOrganisation(userOrg);
					submission.setRawData(data);
								
					submission.persist();
					submission.flush();
					
					metahiveContributionTemplate.convertAndSend(submission.getId());
								
					FlashScope.appendMessage(
							getMessage("metahive_submissions_complete"), request);
					page = "redirect:/submissions";
				}
			}
		}		
		return page;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN','ROLE_EDITOR')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {
		
		Submission submission = Submission.findSubmission(id);
				
		List<JmsRecalculateRequest> toProcess = new ArrayList<JmsRecalculateRequest>();
		
		if (submission != null) {
			for (SubmittedField submittedField : submission.getSubmittedFields()) {
				// Build a list of JMS requests to process following the delete
				JmsRecalculateRequest req = new JmsRecalculateRequest();
	        	req.setPrimaryRecordId(submittedField.getPrimaryRecordId());
	        	req.setSecondaryRecordId(submittedField.getSecondaryRecordId());
	        	req.setTertiaryRecordId(submittedField.getTertiaryRecordId());
	        	req.setDefinitionId(submittedField.getDefinition().getId());
	        	
	        	toProcess.add(req);
			}
			submission.remove();			
		}
		
		for (JmsRecalculateRequest req : toProcess) {
			keyValueGenerationTemplate.convertAndSend(req);
		}
		
        uiModel.asMap().clear();

        FlashScope.appendMessage(
        		getMessage("metahive_delete_complete", Submission.class), request);
        
        return "redirect:/submissions";
    }
	
	@RequestMapping(value = "/template.xls", method = RequestMethod.POST)
	public ModelAndView buildTemplate(
			@RequestParam(value = "definitions", required = true) String[] definitionIds,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	  
		List<Definition> definitions = Definition.findDefinitionEntries(definitionIds);
		
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTitle("Contribution template");
		
		dataGrid.addHeaderField(loadPreferences().getPrimaryRecordName());
		
		for (Definition definition : definitions) {
			dataGrid.addHeaderField(definition.getName());
		}

		return new ModelAndView("ExcelTemplateView", "dataGrid", dataGrid);
	}
	
	/**
	 * Validate the data grid.
	 *
	 * @param data the data
	 * @return the validated data grid
	 */
	private ValidatedDataGrid validateGrid(final String data) {
		return new ValidatedDataGrid(new DataGrid(data));		
	}
	
}
