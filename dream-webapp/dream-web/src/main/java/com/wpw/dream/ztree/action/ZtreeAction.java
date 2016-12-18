package com.wpw.dream.ztree.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wpw.dream.base.BaseAction;

@Controller
public class ZtreeAction extends BaseAction {
	
	@RequestMapping("/ztree/index.do")
	public String index(ModelMap model) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		for (int i = 0; i < 4; i++) {
			map = new HashMap<>();
			map.put("id", 1);
			map.put("parentId", 2);
			map.put("name", "小明" + i);
			map.put("isParent", true);
			list.add(map);
		}
		model.addAttribute("dataJson", JSON.toJSONString(list));
		return "ztree/index";
	}
	
	
	@RequestMapping("/ztree/queryDepts.do")
	@ResponseBody
	public Object queryDepts(Long parentId) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		for (int i = 0; i < 4; i++) {
			map = new HashMap<>();
			map.put("id", 1);
			map.put("parentId", 2);
			map.put("name", "小明" + i);
			map.put("isParent", true);
			list.add(map);
		}
		return list;
		
	}

}
