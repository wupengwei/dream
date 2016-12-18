package com.wpw.dream.dubbo.service.impl;

import org.springframework.stereotype.Service;

import com.wpw.dream.dubbo.service.DubboService;

@Service("dubboService")
public class DubboServiceImpl implements DubboService {

	@Override
	public String sayHello() {
		System.out.println("hello world");
		return "helllo world";
	}


}
