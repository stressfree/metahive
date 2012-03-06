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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfs.metahive.FlashScope;
import com.sfs.metahive.model.Person;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;


@RequestMapping("/people")
@Controller
public class PersonController extends BaseController {


    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Valid Person person, BindingResult bindingResult,
            Model uiModel, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("person", person);

            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", Person.class), request);

            return "people/update";
        }
        uiModel.asMap().clear();
        person.merge();

        FlashScope.appendMessage(
                getMessage("metahive_edit_complete", Person.class), request);

        return "redirect:/people";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("person", Person.findPerson(id));

        return "people/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
            HttpServletRequest request) {

        Person.findPerson(id).remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
                getMessage("metahive_delete_complete", Person.class), request);

        return "redirect:/people";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody String list() {
        String json = Person.toJsonArray(Person.findAllPeople());

        // Translate the user role and statuses
        for (UserRole ur : UserRole.values()) {
            json = StringUtils.replace(json, ur.name(), getMessage(ur.getMessageKey()));
        }
        for (UserStatus us : UserStatus.values()) {
            json = StringUtils.replace(json, us.name(), getMessage(us.getMessageKey()));
        }
        return json;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "people/list";
    }

    @ModelAttribute("userroles")
    public Collection<UserRole> populateUserRoles() {
        Collection<UserRole> userRoles = new ArrayList<UserRole>();
        for (UserRole userRole : UserRole.values()) {
            if (StringUtils.startsWithIgnoreCase(userRole.name(), "ROLE")) {
                userRoles.add(userRole);
            }
        }
        return userRoles;
    }

    @ModelAttribute("userstatuses")
    public Collection<UserStatus> populateUserStatuses() {
        Collection<UserStatus> userStatuses = new ArrayList<UserStatus>();
        for (UserStatus userStatus : UserStatus.values()) {
            userStatuses.add(userStatus);
        }
        return userStatuses;
    }

}
