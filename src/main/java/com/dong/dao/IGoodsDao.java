package com.dong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dong.entity.Goods;

@Mapper
public interface IGoodsDao {

	List<Goods> getAllGoods();

}
