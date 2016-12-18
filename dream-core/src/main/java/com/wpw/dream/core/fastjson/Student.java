package com.wpw.dream.core.fastjson;

public class Student {
	
	private Long id;
	
	private String name;
	
	private String sex;
	
	private Integer age;

	private Object course;
	
	public Object getCourse() {
		return course;
	}

	public void setCourse(Object course) {
		this.course = course;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
