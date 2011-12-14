package com.sfs.metahive.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.DataGrid;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.ValidatedDataGrid;


@RequestMapping("/contribute")
@Controller
public class ContributeController extends BaseController {

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
				
				List<Definition> definitions = Definition.findDefinitionEntries(
						organisation);
				
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
					if (validateGrid(data).processData(data, user, userOrg) > 0) {
						FlashScope.appendMessage(
								getMessage("metahive_submissions_complete"), request);
						page = "redirect:/submissions";
					} else {
						page = "contribute/nodata";
					}
				}
			}
		}
		
		return page;
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