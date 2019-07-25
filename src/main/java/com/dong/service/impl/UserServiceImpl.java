package com.dong.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dong.common.RedisUtil;
import com.dong.dao.IUserDao;
import com.dong.entity.User;
import com.dong.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	private static Logger log = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource
	private IUserDao userDao;
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private RedisUtil redisUtil;
	
	/**
	 * 这里使用集成Mybatis
	 */
	@Override
	public List<User> getAllUser() {
		List<User> users = userDao.getAllUser();
		if (users != null && users.size() > 0) {
			log.debug("The users count is : " + users.stream().count());
		}
		return users;
	}

	@Override
	public List<User> addUser() {
		List<User> list = new ArrayList<>();
		User user = new User();
		for (int i = 0; i < 5; i++) {
			user.setPassword(UUID.randomUUID().toString().replace("-", ""));
			user.setName("jack" + i);
			if (i % 2 == 0) {
				user.setRemark("男");
			} else {
				user.setRemark("女");
			}
			user.setAge(20 + i);
			list.add(user);
		}
		return list;
	}

	@Transactional
	@Override
	public User findById(User user) {
//		ValueOperations opsForValue = redisTemplate.opsForValue();
//		if (opsForValue.get("user:" + user.getUserId()) != null) {
//			System.err.println("在缓存中找到");
//			return (User) opsForValue.get("user:" + user.getUserId());
//		}
		String key = "user:" + user.getUserId();
		if (redisUtil.get(key) != null) {
			System.err.println("在缓存中找到");
			return (User) redisUtil.get(key);
		}
//		System.err.println("第一次查询");
		User u1 = userDao.findById(user);
//		System.err.println("第一次查询结果：" + u1.getName());
//		System.err.println("第二次查询");
//		User u2 = userDao.findById(user);
//		System.err.println("第二次查询结果：" + u2.getName());
//		redisTemplate.opsForValue().set("user:" + u1.getUserId(), u1);
		redisUtil.set(key, u1);
		return u1;
	}

	@Override
	public int saveOrUpdate(User user) {
		int rec = 0;
		if (user.getUserId() != 0) {
			rec = userDao.update(user);
		}
		return rec;
	}

}
