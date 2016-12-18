package com.wpw.dream.core.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetDemoo {

	
	public static void main(String[] args) {
		/*Set<Student> ts = new TreeSet<>();
		ts.add(new Student("111", 11));
		ts.add(new Student("333", 33));
		ts.add(new Student("222", 22));
		
		for (Student student : ts) {
			System.out.println(student);
		}*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "a");
		map.put("a", "b");
		System.out.println(map);
		
		
	}
}

class Student implements Comparable {
	private String name;

	private int age;

	
	
	public Student(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int compareTo(Object o) {
		Student s = (Student) o;
		if (this.age > s.getAge()) 
			return 1;
		if (this.age < s.getAge())
			return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
	}
	
	
	

}
