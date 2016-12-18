package com.wpw.dream.test.action;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.wpw.dream.base.BaseAction;

@Controller
public class TestAction extends BaseAction {

	@RequestMapping("test.do")
	public String test(ModelMap model, HttpServletRequest request) {
		model.addAttribute("name", "wpw");
		//model.addAttribute("base", request.getContextPath());
		return "index";
	}
	
	@RequestMapping("testRenderText.do")
	public void testRenderText(HttpServletResponse response, String name) {
		renderText(response, "success");
	}
	
	@RequestMapping("testRenderJson.do")
	public void testRenderJson(HttpServletResponse response, String callback, HttpServletRequest request) {
		String text = callback + "({\"message\":\"成功\"})";
		renderJson(response, text);
	}
	
	@RequestMapping("testSerializable.do")
	public void testSer(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
		ServletInputStream in = request.getInputStream();
		ObjectInputStream oi = new ObjectInputStream(in);
		User user = (User) oi.readObject();
		System.out.println(user.getName());
		//response.getWriter().write("chenggong");
		//this.renderJson(response, "cddddd");
		this.renderJson(response, JSON.toJSONString(user));
	}
}
