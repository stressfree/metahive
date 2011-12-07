package com.sfs.metahive.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
				uiModel.addAttribute("definitions", Definition.findDefinitionEntries(
						organisation));
				page = "contribute/begin";
			}
		}
		
        return page;
    }
	
	
	
}
