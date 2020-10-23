package com.dong.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 基于redis的自定义cache-做二级缓存
 * 
 * @author xiedongxiao
 *
 */

public class RedisCache implements Cache {

	private static Logger log = LogManager.getLogger(RedisCache.class);

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final String id;
	private RedisTemplate<String, Object> redisTemplate;

	private static final long EXPIRE_TIME_IN_MINUTES = 30; // 默认缓存对象的缓存时间为 1 分钟

	public RedisCache(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instance requires an ID");
		}
		this.id = id;
	}

	private RedisTemplate<String, Object> getRedisTemplate() {
		if (redisTemplate == null) {
			redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
		}
		return redisTemplate;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {
		RedisTemplate<String, Object> temp = this.getRedisTemplate();
		ValueOperations<String, Object> opsForValue = temp.opsForValue();
		opsForValue.set(key.toString(), value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
		log.debug("Put query result to redis");
	}

	@Override
	public Object getObject(Object key) {
		RedisTemplate<String, Object> temp = this.getRedisTemplate();
		ValueOperations<String, Object> opsForValue = temp.opsForValue();
		log.debug("Get cached query result from redis");
		return opsForValue.get(key.toString());
	}

	@Override
	public Object removeObject(Object key) {
		RedisTemplate<String, Object> temp = this.getRedisTemplate();
		temp.delete(key.toString());
		log.debug("Remove cached query result from redis");
		return key;
	}

	@Override
	public void clear() {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
				log.debug("flush redis");
				redisConnection.flushDb();
				return null;
			}
		});
//		redisTemplate.getConnectionFactory().getConnection().flushDb();
	}

	@Override
	public int getSize() {
		return redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

}
