package com.wpw.dream.returnhtml.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
public class ReturnHtmlAction {
	
	@RequestMapping("/returnhtml/index.do")
	public String toIndex() {
		return "returnhtml/index";
	}
	
	@RequestMapping("/returnhtml/getHtml.do")
	@ResponseBody
	public ModelAndView getHtml() {
		
		List<String> list = new ArrayList<String>();
		list.add("吴鹏伟");
		list.add("吴鹏伟");
		list.add("吴鹏伟");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("returnhtml/returnhtml");
		mv.addObject("list", list);
		return mv;
	}

}
