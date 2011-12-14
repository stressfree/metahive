package com.sfs.metahive.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Submission;
import com.sfs.metahive.model.UserRole;


@RequestMapping("/submissions")
@Controller
public class SubmissionController extends BaseController {
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_CONTRIBUTOR','ROLE_EDITOR','ROLE_ADMIN')")
	public String index() {
		return "submissions/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	String list(HttpServletRequest request) {

		Person user = loadUser(request);
		
		List<Submission> submissions = new ArrayList<Submission>();
		
		if (user != null) {
			if (user.getUserRole() == UserRole.ROLE_ADMIN) {
				submissions = Submission.findAllSubmissions();
			} else {
				submissions = Submission.findAllSubmissions(user.getOrganisations());
			}
		}
		return Submission.toJsonArray(submissions);
	}

	@RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
	public String viewData(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {
		
		Person user = loadUser(request);
		
		String page = "submissions/denied";
		
		Submission submission = Submission.findSubmission(id);
		
		if (user != null && submission != null) {
			if (submission.getPerson().getId().equals(user.getId())
					|| user.getUserRole() == UserRole.ROLE_ADMIN) {
				uiModel.addAttribute("dataGrid", submission.getRawDataGrid());
				page = "submissions/viewdata";
			}
		}
		
		return page;
	}

}