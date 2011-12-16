package com.sfs.metahive.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Record;
import com.sfs.metahive.web.model.RecordFilter;
import com.sfs.metahive.web.model.RecordForm;


@RequestMapping("/records")
@Controller
public class RecordController extends BaseController {

	/** The default page size. */
	private final static int DEFAULT_PAGE_SIZE = 50;
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid RecordForm record, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
		Person user = loadUser(request);
        
		if (user == null) {
			// A valid user is required
	        FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
			return "redirect:/records";
		}
		
        if (StringUtils.isNotBlank(record.getRecordId())) {
        	Record newRecord = record.newRecord(loadPreferences());    
        	newRecord.persist();
        }
        
        if (StringUtils.isNotBlank(record.getRecordIds())) {
        	
        	Collection<Record> records = record.parseRecords(loadPreferences());
        	        	
        	for (Record newRecord : records) {
            	System.out.println("Record Id: " + newRecord.getRecordId());
        		newRecord.persist();
        	}
        }
        
        uiModel.asMap().clear();    
        
        FlashScope.appendMessage(
        		getMessage("metahive_create_complete", Record.class), request);
                
        return "redirect:/records";
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("record", new RecordForm());
        return "records/create";
    }
	
	@RequestMapping(params = "bulk", method = RequestMethod.GET)
    public String bulkForm(Model uiModel) {
        uiModel.addAttribute("record", new RecordForm());
        return "records/bulk";
    }

	/**
	 * Show the record.
	 *
	 * @param id the id
	 * @param uiModel the ui model
	 * @return the string
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
 	public String show(@PathVariable("id") Long id, Model uiModel,
 			HttpServletRequest request) {
		
		Record record = Record.findRecord(id);
				
		uiModel.addAttribute("record", record);
		
		return "records/show";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {
		
		Person user = loadUser(request);
        
		if (user == null) {
			// A valid user is required
	        FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
			return "redirect:/records";
		}
		
		Record.findRecord(id).remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
        		getMessage("metahive_delete_complete", Record.class), request);
        
        return "redirect:/records";
    }

	/**
	 * List the records.
	 *
	 * @param recordId the record id
	 * @param page the page
	 * @param size the size
	 * @param uiModel the ui model
	 * @param request the http servlet request
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String list(
    		@RequestParam(value = "recordId", required = false) String recordId,
    		@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size,
    		Model uiModel, HttpServletRequest request) {
		
		int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
		int pageNo = page == null ? 0 : page.intValue() - 1;
		
		RecordFilter filter = new RecordFilter();		
		filter.setEncoding(request.getCharacterEncoding());
		
		if (StringUtils.isNotBlank(recordId)) {
			filter.setRecordId(recordId);
		}
		
        uiModel.addAttribute("records", Record.findRecordEntries(
        		filter, pageNo * sizeNo, sizeNo));
            
        float nrOfPages = (float) Record.countRecords(filter) / sizeNo;

        uiModel.addAttribute("page", pageNo + 1);
        uiModel.addAttribute("size", sizeNo);
        uiModel.addAttribute("filter", filter);
        uiModel.addAttribute("maxPages", 
        		(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) 
        		? nrOfPages + 1 : nrOfPages));
        
        return "records/list";
    }
	
}
