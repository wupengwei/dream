package com.wpw.dream.user.service;

import com.wpw.dream.datatable.action.Pagination;
import com.wpw.dream.entity.User;

public interface UserService {

	Pagination<User> queryUserPage(int page, int length, String orderColName,
			String orderDir, User user);

}
