package com.dong;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dong.common.TaskExecutorConfig;
import com.dong.task.TaskExecutorService;

public class TaskExecutorTests {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
		TaskExecutorService service = context.getBean(TaskExecutorService.class);
		System.out.println("主线程111...");
		// CountDownLatch、CyclicBarrier(拦截所有线程后再继续执行)、Semaphore(控制线程的并发数量)、concurrentHashMap和BlockingQueue(通常用于一个线程生产对象，而另外一个线程消费这些对象的场景)
		CountDownLatch countDownLatch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
			service.task1(i, countDownLatch);
//			service.task2(i);
		}
		countDownLatch.await();
		context.close();
		System.out.println("主线程222...");
		
//		AopContext.currentProxy()

	}

}
