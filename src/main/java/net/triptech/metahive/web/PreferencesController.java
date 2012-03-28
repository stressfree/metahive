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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import net.triptech.metahive.FlashScope;
import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.MetahivePreferences;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/preferences")
@Controller
public class PreferencesController extends BaseController {

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Valid MetahivePreferences metahivePreferences,
            BindingResult bindingResult, Model uiModel, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("metahivePreferences", metahivePreferences);

            FlashScope.appendMessage(getMessage("metahive_object_validation",
                    MetahivePreferences.class), request);

            return "preferences/update";
        }

        uiModel.asMap().clear();
        if (metahivePreferences.getId() != null) {
            // Updating existing preferences
            metahivePreferences.merge();
        } else {
            // No preferences exist yet
            metahivePreferences.persist();
            metahivePreferences.flush();
        }
        FlashScope.appendMessage(getMessage("metahive_preferences_edited"), request);

        return "redirect:/preferences";
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateForm(Model uiModel) {
        uiModel.addAttribute("metahivePreferences", this.loadPreferences());
        return "preferences/update";
    }

    @ModelAttribute("definitions")
    public Map<String, List<Definition>> populateDefinitions() {
        return Definition.groupDefinitions(Definition.findTopLevelDefinitions());
    }

}
