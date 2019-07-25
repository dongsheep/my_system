package com.dong.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dong.dao.IGoodsDao;
import com.dong.entity.Goods;
import com.dong.entity.Item;
import com.dong.repository.ItemRepository;
import com.dong.service.IGoodsService;

@Service
public class GoodsServiceImpl implements IGoodsService {

	@Resource
	private IGoodsDao goodsDao;

	@Resource
	private ItemRepository itemRepository;

	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Transactional
	@Override
	public void testES() {
		List<Goods> lst = goodsDao.getAllGoods();
		if (lst != null && lst.size() > 0) {
			lst.stream().forEach(obj -> {
				Item i = new Item();
				BeanUtils.copyProperties(obj, i);
				i.setPrice(obj.getPrice().doubleValue());
				itemRepository.save(i);
			});
		}
	}

	@Override
	public List<Item> findByName(String name, Pageable pageable) {
//		//按name进行搜索
		MatchQueryBuilder builder = QueryBuilders.matchQuery("goodsName", name);
	    //如果实体和数据的名称对应就会自动封装，pageable分页参数
		Page<Item> pages = itemRepository.search(builder, pageable);
		List<Item> rec = pages.getContent();
		
//		MatchQueryBuilder builder = QueryBuilders.matchQuery("goodsName", name);
//		SearchQuery sq = new NativeSearchQuery(builder);
//		Page<Item> pages = elasticsearchTemplate.queryForPage(sq, Item.class);
//		List<Item> rec = pages.getContent();
		
		return rec;
	}

}
