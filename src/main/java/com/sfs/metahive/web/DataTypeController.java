package com.sfs.metahive.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.DataType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/datatypes")
@Controller
public class DataTypeController extends BaseController {
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid DataType dataType, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("dataType", dataType);

            FlashScope.appendMessage(
            		getMessage("metahive_object_validation", DataType.class), request);
            
            return "datatypes/create";
        }
        uiModel.asMap().clear();
        dataType.persist();

        FlashScope.appendMessage(
        		getMessage("metahive_create_complete", DataType.class), request);
        
        return "redirect:/lists";
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("dataType", new DataType());
        return "datatypes/create";
    }
	
	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Valid DataType dataType, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest request) {
		
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("dataType", dataType);

            FlashScope.appendMessage(
            		getMessage("metahive_object_validation", DataType.class), request);
            
            return "datatypes/update";
        }
        uiModel.asMap().clear();
        dataType.merge();
        
        FlashScope.appendMessage(
        		getMessage("metahive_edit_complete", DataType.class), request);
        
        return "redirect:/lists";
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("dataType", DataType.findDataType(id));
        return "datatypes/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
    		HttpServletRequest request) {
        DataType.findDataType(id).remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
        		getMessage("metahive_delete_complete", DataType.class), request);
        
        return "redirect:/lists";
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String list() {
		return DataType.toJsonArray(DataType.findAllDataTypes());
	}
	
}
