package com.dong.rabbitmq;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dong.common.RedisUtil;
import com.dong.dto.GoodsDTO;
import com.dong.service.ISeckillService;

@Component
@RabbitListener(queues = "goods-queue")
public class MQSeckillGoodsReceive {

	private static Logger log = LogManager.getLogger(MQSeckillGoodsReceive.class);

	@Resource
	private ISeckillService seckillService;

	@Resource
	private RedisUtil redisUtil;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@RabbitHandler
	public void process(String msg) {
		// 查看活动是否结束
		if (redisUtil.hasKey("seckill:end")) {
			return;
		}
		int goodsId = Integer.parseInt(msg.split(":")[0]);
		int userId = Integer.parseInt(msg.split(":")[1]);
		GoodsDTO goods = seckillService.findSeckillGoodsById(goodsId);
		// 查看库存量
		if (goods != null && goods.getNum() > 0) {
			int count = seckillService.updateSeckillGoodsSub(goodsId);
			if (count > 0) {
				// 写入redis表示已经抢购
				redisUtil.set("seckillGoods:" + goodsId + ":" + userId, "1");
			}
		} else {
			// 向redis写入活动结束标识
			redisUtil.set("seckill:end", "1");
		}
	}

}
