// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.web;

import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Description;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect DefinitionController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String DefinitionController.create(@Valid Definition definition, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("definition", definition);
            return "definitions/create";
        }
        uiModel.asMap().clear();
        definition.persist();
        return "redirect:/definitions/" + encodeUrlPathSegment(definition.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String DefinitionController.createForm(Model uiModel) {
        uiModel.addAttribute("definition", new Definition());
        List dependencies = new ArrayList();
        if (DataType.countDataTypes() == 0) {
            dependencies.add(new String[]{"datatype", "datatypes"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "definitions/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String DefinitionController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("definition", Definition.findDefinition(id));
        uiModel.addAttribute("itemId", id);
        return "definitions/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String DefinitionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("definitions", Definition.findDefinitionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Definition.countDefinitions() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("definitions", Definition.findAllDefinitions());
        }
        return "definitions/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String DefinitionController.update(@Valid Definition definition, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("definition", definition);
            return "definitions/update";
        }
        uiModel.asMap().clear();
        definition.merge();
        return "redirect:/definitions/" + encodeUrlPathSegment(definition.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String DefinitionController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("definition", Definition.findDefinition(id));
        return "definitions/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String DefinitionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Definition.findDefinition(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/definitions";
    }
    
    @ModelAttribute("categorys")
    public Collection<Category> DefinitionController.populateCategorys() {
        return Category.findAllCategorys();
    }
    
    @ModelAttribute("datatypes")
    public java.util.Collection<DataType> DefinitionController.populateDataTypes() {
        return DataType.findAllDataTypes();
    }
    
    @ModelAttribute("definitions")
    public java.util.Collection<Definition> DefinitionController.populateDefinitions() {
        return Definition.findAllDefinitions();
    }
    
    @ModelAttribute("descriptions")
    public java.util.Collection<Description> DefinitionController.populateDescriptions() {
        return Description.findAllDescriptions();
    }
    
    String DefinitionController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
