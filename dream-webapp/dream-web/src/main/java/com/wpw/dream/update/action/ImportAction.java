package com.wpw.dream.update.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wpw.dream.entity.User;

@Controller
@RequestMapping("update")
public class ImportAction {

	@RequestMapping("/index")
	public String index() {
		return "update/index";
	}
	
	@RequestMapping(value = "/updateFile", method = RequestMethod.POST)
	@ResponseBody
	public Object update(User user,@RequestPart MultipartFile file) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String originalFilename = file.getOriginalFilename();
		String name = file.getName();
		return resMap;
	}
}
