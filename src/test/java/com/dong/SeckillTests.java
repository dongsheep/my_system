package com.dong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dong.common.ApplicationContextHolder;
import com.dong.service.impl.SeckillServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillTests {

	public static void main(String[] args) throws Exception {
		SeckillTests test = new SeckillTests();
		test.test1();
	}

	@Test
	public void test1() throws Exception {

		ExecutorService tPool = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 20; i++) {
			final int index = i;
			tPool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(index);
					SeckillServiceImpl seckillService = ApplicationContextHolder.getBean("seckillServiceImpl");
					// 查询是否还有库存
					int num = seckillService.findSeckillGoodsNum(1);
					if (num <= 0) {
						System.out.println("The good is sold out, please wait for the seckill activity next time...");
					}
					// 查询是否已经抢购到了
					boolean isBuy = seckillService.findSeckillOrder(index, 1);
					if (isBuy) {
						System.out.println("You have brought it, please pay money in 5 minutes...");
					}
					// 减库存，写入秒杀订单
					seckillService.saveSeckillOrder(index, 1);
				}
			});
		}

	}

}
