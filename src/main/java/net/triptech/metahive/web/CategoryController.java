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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.triptech.metahive.FlashScope;
import net.triptech.metahive.model.Category;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/categories")
@Controller
public class CategoryController extends BaseController {

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid Category category, BindingResult bindingResult,
            Model uiModel, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);

            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", Category.class), request);

            return "categories/create";
        }
        uiModel.asMap().clear();
        category.persist();
        category.flush();

        FlashScope.appendMessage(
                getMessage("metahive_create_complete", Category.class), request);

        return "redirect:/lists";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("category", new Category());
        return "categories/create";
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Valid Category category, BindingResult bindingResult,
            Model uiModel, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);

            FlashScope.appendMessage(
                    getMessage("metahive_object_validation", Category.class), request);
            return "categories/update";
        }
        uiModel.asMap().clear();
        category.merge();

        FlashScope.appendMessage(
                getMessage("metahive_edit_complete", Category.class), request);

        return "redirect:/lists";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("category", Category.findCategory(id));
        return "categories/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
            HttpServletRequest request) {
        Category.findCategory(id).remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
                getMessage("metahive_delete_complete", Category.class), request);

        return "redirect:/lists";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody String list() {
        return Category.toJsonArray(Category.findAllCategorys());
    }
}
