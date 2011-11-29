package com.sfs.metahive.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/")
@Controller
public class HomepageController extends BaseController {
	
	@RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "homepage/show";
    }

}
