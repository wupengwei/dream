package com.wpw.dream.pagination.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wpw.dream.datatable.action.Pagination;
import com.wpw.dream.entity.User;
import com.wpw.dream.user.service.UserService;

@Controller
@RequestMapping("pagination")
public class PaginationAction {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public String index(ModelMap model, User user) {
		Pagination<User> page = userService.queryUserPage(1, 5, "", "",user);
		model.addAttribute("pagination", page);
		return "pagination/index";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView list(Integer pageNo, Integer pageSize, User user) {
		ModelAndView modelAndView = new ModelAndView();
		Pagination<User> page = userService.queryUserPage(pageNo, pageSize, "", "", user);
		modelAndView.setViewName("pagination/index-list-page");
		modelAndView.addObject("pagination", page);
		return modelAndView;
		 
	}

}
