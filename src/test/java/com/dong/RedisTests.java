package com.dong;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.dong.common.ApplicationContextHolder;
import com.dong.dto.GoodsDTO;
import com.dong.service.impl.SeckillServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void set() {
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		opsForValue.set("test2", "testValue456");
		ListOperations<String, Object> opsForList = redisTemplate.opsForList();
//		List<String> lst = new ArrayList<>();
//		lst.add("Jack");
//		lst.add("Tom");
		opsForList.leftPush("person", "Mike");
		redisTemplate.expire("person", 1, TimeUnit.DAYS);// 这里如果执行到上面就挂了，存在分布式锁的问题，需要深入研究
	}

	@Test
	public void remove() {
		redisTemplate.delete("test:set");
	}

	/**
	 * 写秒杀商品到redis中
	 */
	@Test
	public void writeSeckillGoodsToRedis() {
		SeckillServiceImpl bean = ApplicationContextHolder.getBean("seckillServiceImpl");
		List<GoodsDTO> lst = bean.getAllSeckillGoods();
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		for (GoodsDTO obj : lst) {
			opsForValue.setIfAbsent("seckillGoods:" + obj.getGoodsId(), obj.getNum());
		}
	}

}
