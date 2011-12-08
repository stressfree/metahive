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

import com.sfs.metahive.model.DataGrid;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Organisation;


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
		
		if (user != null && user.getOrganisations() != null) {
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
				}
				uiModel.addAttribute("organisations", user.getOrganisations());
				page = "contribute/select";		
			}

			if (organisation != null) {
				
				page = "contribute/nodefinitions";
				
				List<Definition> definitions = Definition.findDefinitionEntries(
						organisation);
				
				if (definitions != null && definitions.size() > 0) {
					uiModel.addAttribute("definitions", definitions);
					page = "contribute/begin";
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
	
	
}
