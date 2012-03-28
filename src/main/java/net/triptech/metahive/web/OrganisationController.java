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
package net.triptech.metahive.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.triptech.metahive.FlashScope;
import net.triptech.metahive.model.Organisation;
import net.triptech.metahive.model.Person;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@RequestMapping("/organisations")
@Controller
public class OrganisationController extends BaseController {

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid Organisation organisation, BindingResult bindingResult,
            Model uiModel, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("organisation", organisation);

            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", Organisation.class),
                    request);

            return "organisations/create";
        }
        uiModel.asMap().clear();
        organisation.persist();
        organisation.flush();

        FlashScope.appendMessage(
                getMessage("metahive_create_complete", Organisation.class), request);

        return "redirect:/organisations";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("organisation", new Organisation());
        return "organisations/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Valid Organisation organisation, BindingResult bindingResult,
            Model uiModel, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("organisation", organisation);

            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", Organisation.class),
                    request);

            return "organisations/update";
        }
        uiModel.asMap().clear();
        organisation.merge();

        FlashScope.appendMessage(
                getMessage("metahive_edit_complete", Organisation.class), request);

        return "redirect:/organisations";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("organisation", Organisation.findOrganisation(id));

        return "organisations/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
            HttpServletRequest request) {

        Organisation.findOrganisation(id).remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
                getMessage("metahive_delete_complete", Organisation.class), request);

        return "redirect:/organisations";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody String list() {
        return Organisation.toJsonArray(Organisation.findAllOrganisations());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "organisations/list";
    }

    @ModelAttribute("people")
    public Collection<Person> populatePeople() {
        return Person.findAllPeople();
    }
}
