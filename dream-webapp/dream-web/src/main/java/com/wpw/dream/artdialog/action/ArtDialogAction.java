package com.wpw.dream.artdialog.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wpw.dream.entity.User;

@Controller
@RequestMapping("artdialog")
public class ArtDialogAction {
	
	@RequestMapping("/index")
	public String index() {
		return "artDialog/index";
	}
	
	@RequestMapping("/toDialog")
	public String toDialog(ModelMap model) {
		
		User user = new User();
		user.setId(1L);
		user.setUserName("吴鹏伟");
		user.setPassword("baomi");
		user.setEmail("1029789047@qq.com");
		model.addAttribute("user", user);
		return "artDialog/edit";
	}

}
