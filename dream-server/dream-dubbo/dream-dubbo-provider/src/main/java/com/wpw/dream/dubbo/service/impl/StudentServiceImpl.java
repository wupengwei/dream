package com.wpw.dream.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wpw.dream.dubbo.dao.StudentDao;
import com.wpw.dream.dubbo.entity.Student;
import com.wpw.dream.dubbo.service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;
	
	@Override
	public Student getById(Long id) {
		return studentDao.getById(id);
	}

}
