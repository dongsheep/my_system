package com.dong.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.dong.entity.Item;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, Integer> {

}
