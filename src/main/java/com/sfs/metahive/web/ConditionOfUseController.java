package com.sfs.metahive.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfs.metahive.model.ConditionOfUse;

@RequestMapping("/conditions")
@Controller
public class ConditionOfUseController extends BaseController {

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid ConditionOfUse conditionOfUse, 
    		BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("conditionOfUse", conditionOfUse);
            return "conditions/create";
        }
        uiModel.asMap().clear();
        conditionOfUse.persist();
        return "redirect:/lists";
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("conditionOfUse", new ConditionOfUse());
        return "conditions/create";
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid ConditionOfUse conditionOfUse, 
    		BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("conditionOfUse", conditionOfUse);
            return "conditions/update";
        }
        uiModel.asMap().clear();
        conditionOfUse.merge();
        return "redirect:/lists";
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("conditionOfUse", ConditionOfUse.findConditionOfUse(id));
        return "conditions/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(
    		value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ConditionOfUse.findConditionOfUse(id).remove();
        uiModel.asMap().clear();

        return "redirect:/lists";
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String list() {
		return ConditionOfUse.toJsonArray(ConditionOfUse.findAllConditionOfUses());
	}
}
