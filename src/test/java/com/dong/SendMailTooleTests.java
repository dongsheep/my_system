package com.dong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dong.common.SendMailTool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMailTooleTests {

	@Autowired
	private SendMailTool sendMailTool;

	@Test
	public void sendSimpleMail() {
		sendMailTool.sendSimpleMail("408515371@qq.com", "主题：简单邮件", "测试邮件内容");
	}
	
	@Test
	public void sendHtmlMail() {
		sendMailTool.sendHtmlMail("408515371@qq.com", "主题：html邮件", "<html><body><div><h1>魅族</h1></div></body><html>");
	}

}
