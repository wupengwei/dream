package com.wpw.dream.dao.impl;

import org.springframework.stereotype.Repository;

import com.wpw.dream.dao.UserDao;
import com.wpw.dream.entity.User;
import com.wpw.dream.mybatis.dao.impl.BaseDaoImpl;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao  {

	/*String ns = "com.wpw.dream.dao.impl.UserDaoImpl.";
	public List<User> queryList(User user) {
		return getSqlSession().selectList(ns + "queryList", user);
	}*/

}
