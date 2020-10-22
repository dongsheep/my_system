package com.dong.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dong.common.RedisUtil;
import com.dong.entity.ResultModel;
import com.dong.service.ISeckillService;

/**
 * 秒杀商品-控制层
 * 
 * @author xiedongxiao
 *
 */

/* @RestController=@Controller+@ResponseBody */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

	@Resource
	private ISeckillService seckillService;

	@Resource
	private RedisUtil redisUtil;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@RequestMapping("/list.do")
	public String goToPage(HttpServletRequest request, HttpServletResponse response) {
		return "/seckill/product";
	}

	@RequestMapping("/buy.do")
	@ResponseBody
	public ResultModel<Object> seckillGoods(HttpServletRequest request, @RequestParam("goodsId") int goodsId,
			@RequestParam("userId") int userId) {
		ResultModel<Object> rec = new ResultModel<>();
		// 加锁
		long time = System.currentTimeMillis() + 1000 * 5; // 超时时间：10秒，最好设为常量
		boolean isLock = redisUtil.lock(String.valueOf(goodsId), String.valueOf(time));
		if (!isLock) {
			rec.setCode("01");
			rec.setMsg("Busy, try again...");
			return rec;
		}
		// 查询是否还有库存
		int num = seckillService.findSeckillGoodsNum(goodsId);
		if (num <= 0) {
			rec.setCode("01");
			rec.setMsg("The good is sold out, please wait for the seckill activity next time...");
			return rec;
		}
		// 查询是否已经抢购到了
		boolean isBuy = seckillService.findSeckillOrder(userId, goodsId);
		if (isBuy) {
			rec.setCode("01");
			rec.setMsg("You have brought it, please pay money in 5 minutes...");
			return rec;
		}
		// 减库存，写入秒杀订单
		seckillService.saveSeckillOrder(userId, goodsId);
		rec.setCode("00");
		rec.setMsg("successful");
		// 解锁
		redisUtil.unlock(String.valueOf(goodsId), String.valueOf(time));
		return rec;
	}
	
	@RequestMapping("/buy2.do")
	@ResponseBody
	public ResultModel<Object> seckillGoods2(HttpServletRequest request, @RequestParam("goodsId") int goodsId,
			@RequestParam("userId") int userId) {
		ResultModel<Object> rec = new ResultModel<>();
		// 查看活动是否结束
		if (redisUtil.hasKey("seckill:end")) {
			rec.setCode("01");
			rec.setMsg("The end, please wait for the seckill activity next time...");
			return rec;
		}
		// 查询是否已经抢购到了
		if (redisUtil.hasKey("seckillGoods:" + goodsId + ":" + userId)) {
			rec.setCode("01");
			rec.setMsg("You have brought it, don't submit again...");
			return rec;
		}
		// 向Rabbitmq发送消息
		rabbitTemplate.convertAndSend("goods-exchange", "goods-routing-key", goodsId + ":" + userId);
		// 等待Rabbitmq处理消息
		try {
			Thread.sleep(2000); // 睡2秒
		} catch (InterruptedException e) {
			e.printStackTrace();
			rec.setCode("02");
			rec.setMsg("System error...");
			return rec;
		}
		// 查询是否已经抢购到了
		if (redisUtil.hasKey("seckillGoods:" + goodsId + ":" + userId)) {
			rec.setCode("00");
			rec.setMsg("Congratulation...");
			return rec;
		}
		// 查看活动是否结束
		if (redisUtil.hasKey("seckill:end")) {
			rec.setCode("01");
			rec.setMsg("The end, please wait for the seckill activity next time...");
			return rec;
		}
		// Rabbitmq未处理完消息
		rec.setCode("02");
		rec.setMsg("Failure...");
		return rec;
	}

}
