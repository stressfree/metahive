package com.sfs.metahive.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.Definition;

@RequestMapping("/categories")
@Controller
public class CategoryController {

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Category category, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);
            return "categories/create";
        }
        uiModel.asMap().clear();
        category.persist();
        return "redirect:/categories/" 
        		+ encodeUrlPathSegment(category.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("category", new Category());
        return "categories/create";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "categories/list";
    }

	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Category category, BindingResult bindingResult, 
    		Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);
            return "categories/update";
        }
        uiModel.asMap().clear();
        category.merge();
        return "redirect:/categories/" + encodeUrlPathSegment(
        		category.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("category", Category.findCategory(id));
        return "categories/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(
    		value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Category.findCategory(id).remove();
        uiModel.asMap().clear();

        return "redirect:/categories";
    }
	
	@RequestMapping(value = "/listdata", method = RequestMethod.GET)
	public @ResponseBody String listData() {
		return Category.toJsonArray(Category.findAllCategorys());
	}

	@ModelAttribute("definitions")
    public java.util.Collection<Definition> populateDefinitions() {
        return Definition.findAllDefinitions();
    }

	String encodeUrlPathSegment(String pathSegment, 
			HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
