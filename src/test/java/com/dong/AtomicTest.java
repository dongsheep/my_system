package com.dong;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicTest {

	private static AtomicInteger number = new AtomicInteger(10);
	
	private static AtomicStampedReference<Integer> stampRef = new AtomicStampedReference<Integer>(10, 1);

	public static void main(String[] args) {
		// ABA问题
//		new Thread(() -> {
//			number.compareAndSet(10, 11);
//			number.compareAndSet(11, 10);
//			System.err.println(Thread.currentThread().getName() + ":10->11->10");
//		}, "thread_1").start();
//		
//		new Thread(() -> {
//			try {
//				TimeUnit.SECONDS.sleep(2);
//				number.compareAndSet(10, 12);
//				System.err.println(Thread.currentThread().getName() + ":" + number.get());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}, "thread_2").start(); 
		
		// 解决ABA问题
		new Thread(() -> {
			int stamp = stampRef.getStamp();
			System.out.println("1:" + stamp);
			stampRef.compareAndSet(10, 11, stampRef.getStamp(), stampRef.getStamp() + 1);
			System.out.println("2:" + stampRef.getStamp());
			stampRef.compareAndSet(11, 10, stampRef.getStamp(), stampRef.getStamp() + 1);
			System.out.println("3:" + stampRef.getStamp());
		}, "thread_1").start();
		
		new Thread(() -> {
			try {
				int stamp = stampRef.getStamp();
				System.out.println("1:" + stamp);
				TimeUnit.SECONDS.sleep(2);
				boolean res = stampRef.compareAndSet(10, 12, stampRef.getStamp(), stampRef.getStamp() + 1);
				System.out.println("2:" + res + "-" + stampRef.getStamp());
				System.out.println("3:" + stampRef.getReference());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "thread_2").start();
		

	}

}
