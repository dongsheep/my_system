package com.dong.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dong.entity.Item;
import com.dong.entity.ResultModel;
import com.dong.entity.User;
import com.dong.service.IGoodsService;
import com.dong.service.IUserService;

@RestController
public class TestController {

	@Resource
	private IUserService userService;
	
	@Autowired
	private IGoodsService goodsService;

	@RequestMapping("/")
	public String index() {
		return "Hello World!";
	}

	@RequestMapping("/getAllUser.do")
	public void getAllUser() {
		userService.getAllUser();
	}
	
	@RequestMapping("/findOneUser.do")
	public void findOneUser() {
		User user = new User();
		user.setUserId(20);
		System.err.println("第一次查询");
		User one = userService.findById(user);
		System.err.println("第一次查询结果" + one.getName());
		System.err.println("第二次查询");
		User two = userService.findById(user);
		System.err.println("第二次查询结果" + two.getName());
	}
	
	@RequestMapping("/updateUser.do")
	public void updateUser() {
		User user = new User();
		user.setUserId(100);
		user.setRemark("测试缓存");
		userService.saveOrUpdate(user);
	}

	@PostMapping("/es/test.do")
	public ResultModel<Object> test(){
	    ResultModel<Object> rec = new ResultModel<>();
	    try {
	    	goodsService.testES();
	    	rec.setCode("00");
	    	rec.setMsg("Test Elasticsearch Successful...");
		} catch (Exception e) {
			rec.setCode("01");
	    	rec.setMsg("Test Elasticsearch Failure...");
		}
	    return rec;
	}

	@GetMapping("/es/search.do")
	public ResultModel<Object> getByName(Pageable pageable, String name){
		ResultModel<Object> rec = new ResultModel<>();
		List<Item> lst = goodsService.findByName(name, pageable);
	    rec.setCode("00");
	    rec.setMsg("Search Successful...");
	    rec.setData(lst);
	    return rec;
	}
	
}
