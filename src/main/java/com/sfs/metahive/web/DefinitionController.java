package com.sfs.metahive.web;

import com.sfs.metahive.model.Definition;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "definitions", formBackingObject = Definition.class)
@RequestMapping("/definitions")
@Controller
public class DefinitionController {
}
