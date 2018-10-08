package com.readin.robot.service;

import com.readin.robot.mapping.UserIn;

public interface UserInService {
	public UserIn userInfo(String email);

	public boolean isExistName(String parameter);

	public int save(UserIn userIn);
}
