package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	public void createOrUpdate(User user) {
		User dbUser = userMapper.findByAccountId(user.getAccountId());
		if (dbUser != null) {
			//更新
			dbUser.setAvatarUrl(user.getAvatarUrl());
			dbUser.setName(user.getName());
			dbUser.setToken(user.getToken());
			dbUser.setGmtModified(System.currentTimeMillis());
			userMapper.update(dbUser);

		} else {
			//创建
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);

		}

	}

}
