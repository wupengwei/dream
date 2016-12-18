package com.wpw.dream.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpw.dream.dao.UserDao;
import com.wpw.dream.datatable.action.Pagination;
import com.wpw.dream.entity.User;
import com.wpw.dream.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	public Pagination<User> queryUserPage(int pageNo, int pageSize,
			String orderColName, String orderDir, User user) {
		  PageHelper.startPage(pageNo, pageSize);
		  if (StringUtils.isNotBlank(orderColName)) {
			  PageHelper.orderBy(orderColName + " " + orderDir);
		  }
		  Map<String, Object> paramMap = new HashMap<>();
		  paramMap.put("userName", user.getUserName());
		  List<User> list = userDao.listBy(paramMap);
          PageInfo<User> page = new PageInfo(list);
          Pagination<User> pagination = 
        		  new Pagination<User>(pageNo, pageSize, (int)page.getTotal(), page.getList());
          return pagination;
	}

}
