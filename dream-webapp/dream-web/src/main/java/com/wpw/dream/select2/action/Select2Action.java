package com.wpw.dream.select2.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wpw.dream.datatable.action.Pagination;
import com.wpw.dream.entity.User;
import com.wpw.dream.user.service.UserService;

@Controller
@RequestMapping("select2")
public class Select2Action {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public String index() {
		return "select2/index";
	}
	
	@RequestMapping("/queryData")
	@ResponseBody
	public Object queryData(String name) {
		
		User user = new User();
		user.setUserName(name);
		Pagination<User> page = userService.queryUserPage(1, 10, "", "", user);
		List<User> list = page.getList();
		List<Map<String, Object>> res = new ArrayList<>();
		Map<String, Object> map;
		for (User user2 : list) {
			map = new HashMap<String, Object>();
			map.put("id", user2.getId());
			map.put("text", user2.getUserName());
			res.add(map);
		}
		return res;
	}
	
	@RequestMapping("/goDialog")
	public String goDialog() {
		return "select2/dialog";
	}

}
