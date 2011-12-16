package com.sfs.metahive.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.RecordType;

@RequestMapping("/recordtypes")
@Controller
public class RecordTypeController extends BaseController {

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid RecordType recordType, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("recordtype", recordType);
            
            FlashScope.appendMessage(
        			getMessage("metahive_object_validation", RecordType.class), request);
            
            return "recordtypes/create";
        }
        uiModel.asMap().clear();
        recordType.persist();
        
        FlashScope.appendMessage(
        		getMessage("metahive_create_complete", RecordType.class), request);
        
        return "redirect:/lists";
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("recordType", new RecordType());
        return "recordtypes/create";
    }
	
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Valid RecordType recordType, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("recordType", recordType);
            
            FlashScope.appendMessage(
        			getMessage("metahive_object_validation", RecordType.class), request);
            return "recordtypes/update";
        }
        uiModel.asMap().clear();
        recordType.merge();
        
        FlashScope.appendMessage(
        		getMessage("metahive_edit_complete", RecordType.class), request);
        
        return "redirect:/lists";
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("recordType", RecordType.findRecordType(id));
        return "recordtypes/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {
        RecordType.findRecordType(id).remove();
        uiModel.asMap().clear();
        
        FlashScope.appendMessage(
        		getMessage("metahive_delete_complete", RecordType.class), request);
        
        return "redirect:/lists";
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String list() {
		return RecordType.toJsonArray(RecordType.findAllRecordTypes());
	}
}
