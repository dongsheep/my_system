package com.dong.service;

import java.util.List;

import com.dong.dto.GoodsDTO;

public interface ISeckillService {

	List<GoodsDTO> getAllSeckillGoods();

	GoodsDTO findSeckillGoods(int goodsId);

	boolean findSeckillOrder(int userId, int goodsId);

	void saveSeckillOrder(int userId, int goodsId);

	int findSeckillGoodsNum(int goodsId);

	GoodsDTO findSeckillGoodsById(int goodsId);

	int updateSeckillGoodsSub(int goodsId);

}
