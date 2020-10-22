package com.dong;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibTests {

	public static void main(String[] args) {
		Enhancer hancer = new Enhancer();
		hancer.setSuperclass(CglibTests.class);
		hancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println("Before method run...");
				proxy.invokeSuper(obj, args);
				System.out.println("After method run...");
				return obj;
			}
		});
		CglibTests obj = (CglibTests) hancer.create();
		obj.test();
	}

	public void test() {
		System.out.println("Hello World...");
	}

}
