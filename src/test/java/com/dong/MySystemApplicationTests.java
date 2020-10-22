package com.dong;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySystemApplicationTests {

	@Test
	public void contextLoads() {
	}

	public static void main(String[] args) {
		
		ConcurrentHashMap<Object, Object> cache = new ConcurrentHashMap<Object, Object>();
		cache.put("asd", "123");
		System.out.println(cache.get("asd"));
		
	}
	
}
