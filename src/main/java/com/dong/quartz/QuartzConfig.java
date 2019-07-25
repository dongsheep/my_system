package com.dong.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class QuartzConfig {

//	@Bean
	public JobDetail testQuartz() {
		return JobBuilder.newJob(TestQuartz.class).withIdentity("testQuartz").storeDurably().build();
	}

//	@Bean
	public Trigger testQuartzTrigger() {
		// cron方式，每隔5秒执行一次
		return TriggerBuilder.newTrigger().forJob(testQuartz()).withIdentity("testQuartz")
				.withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).build();
	}

}
