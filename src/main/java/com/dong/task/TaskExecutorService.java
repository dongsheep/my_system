package com.dong.task;

import java.util.concurrent.CountDownLatch;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//@Async
@Service
public class TaskExecutorService {
	
	@Async
	public void task1 (int i, CountDownLatch countDownLatch) {
		System.out.println("执行任务1..." + i + ":" + Thread.currentThread().getName());
		countDownLatch.countDown();
	}
	
//	@Async
	public void task2 (int i) {
		System.out.println("执行任务2..." + i + ":" + Thread.currentThread().getName());
	}

}
