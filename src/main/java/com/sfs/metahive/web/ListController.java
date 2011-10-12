package com.sfs.metahive.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/lists")
@Controller
public class ListController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "lists/list";
    }
}
