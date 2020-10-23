package com.dong;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dong.entity.User;


/**
 * @BeforeClass：针对所有测试，只执行一次，且必须为static void 

 * @Before：初始化方法 

 * @Test：测试方法，在这里可以测试期望异常和超时时间 

 * @After：释放资源 

 * @AfterClass：针对所有测试，只执行一次，且必须为static void 

 * @Ignore：忽略的测试方法  

 * 一个单元测试用例执行顺序为： @BeforeClass –> @Before –> @Test –> @After –> @AfterClass 

 * 每一个测试方法的调用顺序为： @Before –> @Test –> @After
 * 
 * @author xiedongxiao
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssertTests {

	@Test
	public void assertT() {
		
		User user = new User();
		user.setName("Tom");
		Assert.assertEquals("assert failure...", "Jack",  user.getName());
		
	}
	
}
