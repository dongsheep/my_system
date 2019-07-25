package com.dong;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.dong.entity.Item;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTests {
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	public void createIndex() {
	}
	
}
