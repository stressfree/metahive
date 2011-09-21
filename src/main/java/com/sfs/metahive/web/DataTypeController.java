package com.sfs.metahive.web;

import com.sfs.metahive.model.DataType;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "datatypes", formBackingObject = DataType.class)
@RequestMapping("/datatypes")
@Controller
public class DataTypeController {
}
