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
import com.sfs.metahive.model.ConditionOfUse;

@RequestMapping("/conditions")
@Controller
public class ConditionOfUseController extends BaseController {

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid ConditionOfUse conditionOfUse,
            BindingResult bindingResult, Model uiModel,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("conditionOfUse", conditionOfUse);

            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", ConditionOfUse.class),
                    request);

            return "conditions/create";
        }
        uiModel.asMap().clear();
        conditionOfUse.persist();
        conditionOfUse.flush();

        FlashScope.appendMessage(
                getMessage("metahive_create_complete", ConditionOfUse.class), request);

        return "redirect:/lists";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("conditionOfUse", new ConditionOfUse());
        return "conditions/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Valid ConditionOfUse conditionOfUse,
            BindingResult bindingResult, Model uiModel,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("conditionOfUse", conditionOfUse);

            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", ConditionOfUse.class),
                    request);

            return "conditions/update";
        }
        uiModel.asMap().clear();
        conditionOfUse.merge();

        FlashScope.appendMessage(
                getMessage("metahive_edit_complete", ConditionOfUse.class), request);

        return "redirect:/lists";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("conditionOfUse", ConditionOfUse.findConditionOfUse(id));
        return "conditions/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
            HttpServletRequest request) {
        ConditionOfUse.findConditionOfUse(id).remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
                getMessage("metahive_delete_complete", ConditionOfUse.class), request);

        return "redirect:/lists";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody String list() {
        return ConditionOfUse.toJsonArray(ConditionOfUse.findAllConditionOfUses());
    }
}
