package com.sfs.metahive.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.KeyValue;
import com.sfs.metahive.model.MetahivePreferences;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Record;
import com.sfs.metahive.model.SubmittedField;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.web.model.KeyValueJson;
import com.sfs.metahive.web.model.RecordFilter;
import com.sfs.metahive.web.model.RecordForm;

import flexjson.JSONSerializer;


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
        	newRecord.flush();
        }
        
        if (StringUtils.isNotBlank(record.getRecordIds())) {
        	
        	Collection<Record> records = record.parseRecords(loadPreferences());
        	        	
        	for (Record newRecord : records) {
        		newRecord.persist();
        		newRecord.flush();
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
 			@RequestParam(value = "show", required = false) Boolean show,
 			@RequestParam(value = "expand", required = false) Boolean expand,
 			HttpServletRequest request) {
		
		Person user = loadUser(request);
		UserRole role = UserRole.ANONYMOUS;
		
		boolean showAllDefinitions = false;
		boolean expandAllDefinitions = false;
		
		if (user != null) {
			role = user.getUserRole();
			showAllDefinitions = user.isShowAllDefinitions();
			expandAllDefinitions = user.isExpandAllDefinitions();
		}
		
		if (show != null) {
			showAllDefinitions = show;
		}
		if (expand != null) {
			expandAllDefinitions = expand;
		}
		
		if (user != null) {
			if (show != null) {
				user.setShowAllDefinitions(show);
			}
			if (expand != null) {
				user.setExpandAllDefinitions(expand);
			}
			if (show != null || expand != null) {
				user.merge();				
			}
		}
		
		Record record = Record.findRecord(id);
		record.setShowAllDefinitions(showAllDefinitions);
		record.loadAllKeyValues(role, this.getContext());		
		
		uiModel.addAttribute("record", record);
		uiModel.addAttribute("show", showAllDefinitions);
		uiModel.addAttribute("expand", expandAllDefinitions);
				
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
	 * Show information on the key value.
	 *
	 * @param id the id
	 * @param keyValueId the key value id
	 * @param uiModel the ui model
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping(value = "/{id}/keyvalue/{keyValueId}", method = RequestMethod.GET)
 	public String keyvalueDetail(@PathVariable("id") Long id, 
 			@PathVariable("keyValueId") Long keyValueId, Model uiModel,
 			HttpServletRequest request) {
	
		KeyValue kv = KeyValue.findKeyValue(keyValueId);
		kv.setSubmittedFields(SubmittedField.findSubmittedFields(
				kv.getDefinition(), kv.getPrimaryRecordId(), 
				kv.getSecondaryRecordId(), kv.getTertiaryRecordId()));

		uiModel.addAttribute("keyValue", kv);
		
		return "records/keyvaluedetail";
	}
	
	/**
	 * The key value as a json string.
	 *
	 * @param id the id
	 * @param keyValueId the key value id
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping(value = "/{id}/keyvalue/{keyValueId}", 
			params = "json", method = RequestMethod.GET)
    public @ResponseBody String keyvalueJson(@PathVariable("id") Long id, 
 			@PathVariable("keyValueId") Long keyValueId,
 			HttpServletRequest request) {

		UserRole userRole = UserRole.ANONYMOUS;
		
		Person user = loadUser(request);
		if (user != null && user.getUserRole() != null) {
			userRole = user.getUserRole();
		}
		
		KeyValue kv = KeyValue.findKeyValue(keyValueId);		
		KeyValueJson kvj = new KeyValueJson(kv, kv.getDefinition(), userRole);
		
		return new JSONSerializer().exclude("*.class").serialize(kvj);
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
		
		Person user = loadUser(request);
			
		List<Definition> definitions = new ArrayList<Definition>();
		if (user == null) {
			// Load the default definition list
			MetahivePreferences preferences = MetahivePreferences.load();
			definitions = preferences.getDefaultDefinitions();
		} else {
			if (user.getSearchDefinitions().size() == 0) {
				// Set the default definitions for the user
				MetahivePreferences preferences = MetahivePreferences.load();
								
				for (Definition def : preferences.getDefaultDefinitions()) {
					definitions.add(def);
				}
				user.setSearchDefinitions(definitions);
				user.persist();
			} else {
				definitions = user.getSearchDefinitions();
			}
		}
		
		UserRole userRole = UserRole.ANONYMOUS;
		if (user != null && user.getUserRole() != null) {
			userRole = user.getUserRole();
		}		
		
		int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
		int pageNo = page == null ? 0 : page.intValue() - 1;
		
		RecordFilter filter = new RecordFilter();		
		filter.setEncoding(request.getCharacterEncoding());
		
		if (StringUtils.isNotBlank(recordId)) {
			filter.setRecordId(recordId);
		}
				
		uiModel.addAttribute("definitions", definitions);				
		
        uiModel.addAttribute("records", Record.findRecordEntries(
        		filter, definitions, userRole, this.getContext(),
        		pageNo * sizeNo, sizeNo));
            
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
