package com.dong.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import com.dong.entity.User;

//@CacheNamespace
@Mapper
public interface IUserDao {

	List<User> getAllUser();
	
	User findById(User user);

	int update(User user);
	
}
