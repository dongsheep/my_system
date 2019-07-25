package com.dong.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dong.entity.Item;

public interface IGoodsService {

	void testES();

	List<Item> findByName(String name, Pageable pageable);

}
