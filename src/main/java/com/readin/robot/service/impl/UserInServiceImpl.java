package com.readin.robot.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readin.robot.dao.UserInMapper;
import com.readin.robot.mapping.UserIn;
import com.readin.robot.service.UserInService;

@Service
@Transactional
public class UserInServiceImpl implements UserInService {
	@Autowired
	private UserInMapper userInMapper;
	public UserIn userInfo(String name){
		UserIn userIn = userInMapper.findByName(name);
		return userIn;
	}
	public boolean isExistName(String name) {
		UserIn userIn = userInMapper.findByName(name);
		if (userIn != null){
			return true;
		}else{
			return false;
		}
	}
	public int save(UserIn userIn) {
		int status = userInMapper.insert(userIn);
		System.out.println("用户保存状态===>");
		System.out.println(status);
		return 1;
	}
}
