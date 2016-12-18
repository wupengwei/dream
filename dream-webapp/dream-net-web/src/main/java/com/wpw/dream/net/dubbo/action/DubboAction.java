package com.wpw.dream.net.dubbo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wpw.dream.dubbo.entity.Student;
import com.wpw.dream.dubbo.service.DubboService;
import com.wpw.dream.dubbo.service.StudentService;

@Controller
@RequestMapping("dubbo")
public class DubboAction {
	
	@Autowired
	private DubboService dubboService;
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("/index")
	@ResponseBody
	public Object index() {
		String sayHello = dubboService.sayHello();
		Student student = studentService.getById(1L);
		System.out.println(student.getName());
		return sayHello;
		//return sayHello;
	}

}
