/*package com.wpw.dream.core.fastjson;

import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FastJsonTest {
	
	@Test
	public void test1() {
		Student stu = new Student();
		 stu.setId(1L);
		 stu.setName("wpw");
		 stu.setSex("男");
		 stu.setAge(23);
		 Course course = new Course();
		 course.setId(2L);
		 course.setName("语文");
		 stu.setCourse(course);
		 
		 String jsonString = JSON.toJSONString(stu);
		 System.out.println(jsonString);
	}
	
	@Test
	public void test2() {
		 String stuStr = "{\"age\":23,\"course\":{\"id\":2,\"name\":\"语文\"},\"id\":1,\"name\":\"wpw\",\"sex\":\"男\"}";
		 JSON json2 = JSON.parseObject(stuStr);
		 Map map = JSON.toJavaObject(json2, Map.class);
		 Integer age = (Integer) map.get("age");
		 Map c = (Map) map.get("course");
		 Object id = c.get("id"); //Integer
		 System.out.println(map);
	}
	
	@Test
	public void test3() {
		String stuStr = "{\"age\":23,\"course\":{\"id\":2,\"name\":\"语文\"},\"id\":1,\"name\":\"wpw\",\"sex\":\"男\"}";
		JSON json = JSON.parseObject(stuStr);
		Student student = JSON.toJavaObject(json, Student.class);
		Map object = (Map) student.getCourse();
		System.out.println(object);
	}
	
	@Test
	public void test4() {
		String stuStr = "{\"age\":23,\"course\":{\"id\":2,\"name\":\"语文\"},\"id\":1,\"name\":\"wpw\",\"sex\":\"男\"}";
		Student student = JSONObject.toJavaObject(JSON.parseObject(stuStr), Student.class);
		System.out.println(student.getCourse());
	}

	
}
*/