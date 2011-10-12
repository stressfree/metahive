package com.sfs.metahive.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.model.DataType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/datatypes")
@Controller
public class DataTypeController extends BaseController {
	
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid DataType dataType, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("dataType", dataType);
            return "datatypes/create";
        }
        uiModel.asMap().clear();
        dataType.persist();
        return "redirect:/lists";
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("dataType", new DataType());
        return "datatypes/create";
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid DataType dataType, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("dataType", dataType);
            return "datatypes/update";
        }
        uiModel.asMap().clear();
        dataType.merge();
        return "redirect:/lists";
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("dataType", DataType.findDataType(id));
        return "datatypes/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(
    		value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        DataType.findDataType(id).remove();
        uiModel.asMap().clear();

        return "redirect:/lists";
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String list() {
		return DataType.toJsonArray(DataType.findAllDataTypes());
	}
	
}
