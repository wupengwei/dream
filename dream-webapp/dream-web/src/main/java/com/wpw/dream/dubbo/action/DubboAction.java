/*package com.wpw.dream.dubbo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wpw.dream.dubbo.service.DubboService;

@Controller
@RequestMapping("dubbo")
public class DubboAction {
	
	@Autowired
	private DubboService dubboService;
	
	@RequestMapping("/index")
	@ResponseBody
	public Object index() {
		String sayHello = dubboService.sayHello();
		return sayHello;
		//return sayHello;
	}

}
*/