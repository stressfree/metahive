package com.sfs.metahive.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sfs.metahive.model.Submission;
import com.sfs.metahive.web.model.SubmissionFilter;


@RequestMapping("/submissions")
@Controller
public class SubmissionController extends BaseController {

	/** The default page size. */
	private final static int DEFAULT_PAGE_SIZE = 25;
	
	/**
	 * List the submissions.
	 *
	 * @param name the name
	 * @param category the category
	 * @param page the page
	 * @param size the size
	 * @param uiModel the ui model
	 * @param request the http servlet request
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String list(
    		@RequestParam(value = "personId", required = false) Long personId,
    		@RequestParam(value = "organisationId", required = false) Long organisationId,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size,
    		Model uiModel, HttpServletRequest request) {
		
		int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
		int pageNo = page == null ? 0 : page.intValue() - 1;
		
		
		SubmissionFilter filter = new SubmissionFilter();		
		filter.setEncoding(request.getCharacterEncoding());
		
		if (personId != null) {
			filter.setPersonId(personId);
		}
		if (organisationId != null) {
			filter.setOrganisationId(organisationId);
		}
		
        uiModel.addAttribute("submissions", Submission.findSubmissionEntries(
        		filter, pageNo * sizeNo, sizeNo));
            
        float nrOfPages = (float) Submission.countSubmissions(filter) / sizeNo;

        uiModel.addAttribute("page", pageNo + 1);
        uiModel.addAttribute("size", sizeNo);    
        uiModel.addAttribute("filter", filter);
        uiModel.addAttribute("maxPages", 
        		(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) 
        		? nrOfPages + 1 : nrOfPages));
        
        return "submissions/list";
    }
	
}
