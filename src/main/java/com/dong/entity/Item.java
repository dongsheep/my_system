package com.dong.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Elasticsearch demo entity
 * 
 * @author xiedongxiao
 *
 */

@Document(indexName = "item", type = "doc", shards = 1, replicas = 0)
public class Item {

	@Id
	private Integer id;

	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String goodsName; // 商品名称

	@Field(type = FieldType.Keyword)
	private String goodsDesc;// 商品详情

	@Field(type = FieldType.Double)
	private Double price; // 价格

	@Field(index = false, type = FieldType.Keyword)
	private Integer num; // 库存

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
