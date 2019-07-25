package com.dong.service;

import java.util.List;

import com.dong.entity.User;

public interface IUserService {

	List<User> getAllUser();

	List<User> addUser();

	User findById(User user);

	int saveOrUpdate(User user);

}
