package com.dong.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.dong.dao.ISeckillDao;
import com.dong.dto.GoodsDTO;
import com.dong.service.ISeckillService;

@Service
public class SeckillServiceImpl implements ISeckillService {

	private static Logger log = LogManager.getLogger(SeckillServiceImpl.class);
	
	@Autowired
	private ISeckillDao seckillDao;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public List<GoodsDTO> getAllSeckillGoods() {
		List<GoodsDTO> lst = seckillDao.getAllSeckillGoods();
		log.info("Get all seckill goods successful... size: " + lst.size());
		return lst;
	}

	@Override
	public GoodsDTO findSeckillGoods(int goodsId) {
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		return (GoodsDTO) opsForValue.get("seckillGoods:" + goodsId);
	}

	@Override
	public boolean findSeckillOrder(int userId, int goodsId) {
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		Integer num = (Integer) opsForValue.get("seckillOrder:" + userId + ":" + goodsId);
		if (num != null && num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void saveSeckillOrder(int userId, int goodsId) {
		// 减少库存
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		opsForValue.decrement("seckillGoods:" + goodsId);
		// 写入秒杀订单
		opsForValue = redisTemplate.opsForValue();
		opsForValue.setIfAbsent("seckillOrder:" + userId + ":" + goodsId, 1, 5, TimeUnit.MINUTES);
		// 传去rabbitmq
		
	}

	@Override
	public int findSeckillGoodsNum(int goodsId) {
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		return (Integer) opsForValue.get("seckillGoods:" + goodsId);
	}

	@Override
	public GoodsDTO findSeckillGoodsById(int goodsId) {
		return seckillDao.findSeckillGoodsById(goodsId);
	}

	@Override
	public int updateSeckillGoodsSub(int goodsId) {
		return seckillDao.updateSeckillGoodsSub(goodsId);
	}

}
