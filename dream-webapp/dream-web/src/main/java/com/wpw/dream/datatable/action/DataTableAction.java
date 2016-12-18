package com.wpw.dream.datatable.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wpw.dream.entity.User;
import com.wpw.dream.user.service.UserService;

@Controller
@RequestMapping("/datatable")
public class DataTableAction {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index.do")
	public String toIndex() {
		//System.out.println("dddd");
		return "datatable/index";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Object queryMediaList(Model model, String condition, User user) {
		
		DataTablesParameters tables = DataTablesParameters.newInstance();
		String[] orderColumns = { "userName", "password", "sex", "email"};
		Pagination<User> pagination = userService.queryUserPage(tables.getPage(),
				tables.getLength(), tables.getOrderColName(orderColumns), tables.getOrderDir(),user);
		return tables.getDataTablesReply(pagination);
	} 

	@RequestMapping("/listTest.do")
	@ResponseBody
	public Object queryMediaListTest(Model model, String condition, User user) {
		
		Pagination<User> pagination = userService.queryUserPage(1,
				10, "", "", user);
		//return tables.getDataTablesReply(pagination);
		return pagination;
	} 
}
