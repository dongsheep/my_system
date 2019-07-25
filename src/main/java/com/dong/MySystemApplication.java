package com.dong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//开启定时任务,注意看源码
@EnableScheduling
//开启事务
@EnableTransactionManagement
//开启异步调用方法
@EnableAsync
public class MySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySystemApplication.class, args);
	}

}
