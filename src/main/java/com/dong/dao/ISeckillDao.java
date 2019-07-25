package com.dong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dong.dto.GoodsDTO;

@Mapper
public interface ISeckillDao {

	List<GoodsDTO> getAllSeckillGoods();

	GoodsDTO findSeckillGoodsById(int goodsId);

	int updateSeckillGoodsSub(int goodsId);
	
}
