/*******************************************************************************
 * Copyright (c) 2012 David Harrison, Triptech Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     David Harrison, Triptech Ltd - initial API and implementation
 ******************************************************************************/
package com.sfs.metahive.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.CommentType;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.DefinitionType;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.Applicability;
import com.sfs.metahive.web.model.CommentForm;
import com.sfs.metahive.web.model.DefinitionFilter;
import com.sfs.metahive.web.model.DefinitionForm;
import com.sfs.metahive.web.model.DefinitionJson;
import com.sfs.metahive.web.model.RelatedDefinitionJson;

import flexjson.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * The Class DefinitionController.
 */
@RequestMapping("/definitions")
@Controller
public class DefinitionController extends BaseController {

    /** The default page size. */
    private final static int DEFAULT_PAGE_SIZE = 50;

    /**
     * Creates the definition.
     *
     * @param definitionForm the definition form
     * @param bindingResult the binding result
     * @param uiModel the ui model
     * @param request the http servlet request
     * @return the string
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
    public String create(@Valid DefinitionForm definitionForm,
            BindingResult bindingResult, Model uiModel,
            HttpServletRequest request) {

        Person user = loadUser(request);

        if (user == null) {
            // A valid user is required
            FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
            return "redirect:/definitions";
        }
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("definition", definitionForm);
            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", Definition.class), request);
            return "definitions/create";
        }

        uiModel.asMap().clear();

        Definition definition = definitionForm.newDefinition(user);
        definition.persist();
        definition.flush();

        Comment comment = definitionForm.newComment(CommentType.CREATE, definition, user);
        comment.persist();

        FlashScope.appendMessage(
                getMessage("metahive_create_complete", Definition.class), request);

        return "redirect:/definitions/"
                + encodeUrlPathSegment(definition.getId().toString(), request);
    }

    /**
     * Creates the form.
     *
     * @param uiModel the ui model
     * @return the string
     */
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("definition", new DefinitionForm());
        return "definitions/create";
    }

    /**
     * Update the definition.
     *
     * @param definition the definition
     * @param bindingResult the binding result
     * @param uiModel the ui model
     * @param request the http servlet request
     * @return the string
     */
    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
    public String update(@Valid DefinitionForm definitionForm,
            BindingResult bindingResult, Model uiModel,
            HttpServletRequest request) {

        Person user = loadUser(request);

        if (user == null) {
            // A valid user is required
            FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
            return "redirect:/definitions";
        }

        // Load the existing definition
        Definition definition = Definition.findDefinition(definitionForm.getId());

        if (definition == null) {
            // A valid definition was not found
            FlashScope.appendMessage(
                    getMessage("metahive_object_not_found", Definition.class), request);
            return "redirect:/definitions";
        }

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("definition", definitionForm);
            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", Definition.class), request);
            return "definitions/update";
        }

        definition = definitionForm.mergedDefinition(definition, user);

        uiModel.asMap().clear();
        definition.merge();

        Comment comment = definitionForm.newComment(CommentType.MODIFY, definition, user);
        comment.persist();

        FlashScope.appendMessage(
                getMessage("metahive_edit_complete", Definition.class), request);

        return "redirect:/definitions/"
                + encodeUrlPathSegment(definition.getId().toString(), request);
    }

    /**
     * Show the definition.
     *
     * @param id the id
     * @param uiModel the ui model
     * @return the string
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
     public String show(@PathVariable("id") Long id, Model uiModel,
             HttpServletRequest request) {

        Person user = loadUser(request);

        Collection<Organisation> organisations = new ArrayList<Organisation>();
        if (user != null) {
            if (user.getUserRole() == UserRole.ROLE_ADMIN) {
                organisations = Organisation.findAllOrganisations();
            } else {
                organisations = user.getOrganisations();
            }
        }

        Definition definition = Definition.findDefinition(id);

        CommentForm commentForm = new CommentForm();
        commentForm.setDefinition(definition);

        uiModel.addAttribute("definition", definition);
        uiModel.addAttribute("comment", commentForm);
        uiModel.addAttribute("itemId", id);
        uiModel.addAttribute("organisations", organisations);

        return "definitions/show";
    }

    /**
     * Update the definition form.
     *
     * @param id the id
     * @param uiModel the ui model
     * @return the string
     */
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Definition definition = Definition.findDefinition(id);

        if (definition == null) {
            return "redirect:/definitions";
        }
        DefinitionForm definitionForm = DefinitionForm.parseDefinition(definition);

        uiModel.addAttribute("definition", definitionForm);

        return "definitions/update";
    }

    /**
     * Delete the definition.
     *
     * @param id the id
     * @param page the page
     * @param size the size
     * @param uiModel the ui model
     * @param request the http servlet request
     * @return the string
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
            HttpServletRequest request) {

        Person user = loadUser(request);

        if (user == null) {
            // A valid user is required
            FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
            return "redirect:/definitions";
        }

        Definition.findDefinition(id).remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
                getMessage("metahive_delete_complete", Definition.class), request);

        return "redirect:/definitions";
    }

    /**
     * List the definitions.
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
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            Model uiModel, HttpServletRequest request) {

        int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
        int pageNo = page == null ? 0 : page.intValue() - 1;


        DefinitionFilter filter = new DefinitionFilter();
        filter.setEncoding(request.getCharacterEncoding());

        if (StringUtils.isNotBlank(name)) {
            filter.setName(name);
        }
        if (StringUtils.isNotBlank(category)
                && !StringUtils.equalsIgnoreCase(category, "-")) {
            filter.setCategory(category);
        }

        uiModel.addAttribute("definitions", Definition.findDefinitionEntries(
                filter, pageNo * sizeNo, sizeNo));

        float nrOfPages = (float) Definition.countDefinitions(filter) / sizeNo;

        uiModel.addAttribute("resultCounts", resultCounts());
        uiModel.addAttribute("page", pageNo + 1);
        uiModel.addAttribute("size", sizeNo);
        uiModel.addAttribute("filter", filter);
        uiModel.addAttribute("maxPages",
                (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0)
                ? nrOfPages + 1 : nrOfPages));

        return "definitions/list";
    }

    @RequestMapping(value = "/relatedDefinitions", method = RequestMethod.GET)
    public @ResponseBody String relatedDefinitions(
            @RequestParam(value= "id", required = false) Long id,
            @RequestParam(value = "type", required = false) String type,
            Model uiModel, HttpServletRequest request) {

        Definition definition = new Definition();
        if (id != null && id > 0l) {
            definition = Definition.findDefinition(id);
        }
        if (definition == null) {
            definition = new Definition();
        }

        if (StringUtils.equalsIgnoreCase(type, "STANDARD")) {
            definition.setDefinitionType(DefinitionType.STANDARD);
        }
        if (StringUtils.equalsIgnoreCase(type, "CALCULATED")) {
            definition.setDefinitionType(DefinitionType.CALCULATED);
        }
        if (StringUtils.equalsIgnoreCase(type, "SUMMARY")) {
            definition.setDefinitionType(DefinitionType.SUMMARY);
        }

        List<Definition> definitions = Definition
                .findPotentialRelatedDefinitions(definition);

        RelatedDefinitionJson json = new RelatedDefinitionJson();
        json.setIdentifier("id");
        json.setLabel("name");

        for (Definition def : definitions) {
            json.addDefinition(new DefinitionJson(def));
        }

        return new JSONSerializer().exclude("*.class").include("items").serialize(json);
    }

    /**
     * Test calculation for AJAX purposes.
     *
     * @param calculation the calculation
     * @return the string
     */
    @RequestMapping(value = "/testCalculation", method = RequestMethod.POST)
    public @ResponseBody String testCalculation(
            String calculation) {

        Definition definition = new Definition();
        definition.setCalculation(calculation);
        definition.setDefinitionType(DefinitionType.CALCULATED);

        return definition.testCalculation();
    }

    /**
     * Populate categories.
     *
     * @return the collection
     */
    @ModelAttribute("categories")
    public Collection<Category> populateCategories() {
        return Category.findAllCategorys();
    }

    /**
     * Populate applicabilities.
     *
     * @return the collection
     */
    @ModelAttribute("applicabilities")
    public Collection<Applicability> populateRecordTypes() {
        Collection<Applicability> applicabilities = new ArrayList<Applicability>();
        for (Applicability applicability : Applicability.values()) {
            applicabilities.add(applicability);
        }
        return applicabilities;
    }

    /**
     * Populate the definition types.
     *
     * @return the definition types
     */
    @ModelAttribute("definitiontypes")
    public Collection<DefinitionType> populateDefinitionTypes() {
        Collection<DefinitionType> definitionTypes = new ArrayList<DefinitionType>();
        for (DefinitionType definitionType : DefinitionType.values()) {
            definitionTypes.add(definitionType);
        }
        return definitionTypes;
    }

    /**
     * Populate the data types.
     *
     * @return the data types
     */
    @ModelAttribute("datatypes")
    public Collection<DataType> populateDataTypes() {
        Collection<DataType> dataTypes = new ArrayList<DataType>();
        for (DataType dataType : DataType.values()) {
            dataTypes.add(dataType);
        }
        return dataTypes;
    }

    @ModelAttribute("userroles")
    public Collection<UserRole> populateUserRoles() {
        Collection<UserRole> userRoles = new ArrayList<UserRole>();
        for (UserRole userRole : UserRole.values()) {
            userRoles.add(userRole);
        }
        return userRoles;
    }

}
