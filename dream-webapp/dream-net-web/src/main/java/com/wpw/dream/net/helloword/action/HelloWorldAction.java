package com.wpw.dream.net.helloword.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldAction {
	
	@RequestMapping("helloword")
	public String index(ModelMap model) {
		model.addAttribute("name", "helloword");
		return "index";
	}

}
