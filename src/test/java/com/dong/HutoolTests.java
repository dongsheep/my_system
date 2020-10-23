package com.dong;

import java.util.Date;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.HashUtil;

public class HutoolTests {

	public static void main(String[] args) {
		
		double a = 67556.324;
		//结果为："陆万柒仟伍佰伍拾陆元叁角贰分"
		String digitUppercase = Convert.digitToChinese(a);
		System.out.println(digitUppercase);
		
//		String dateStr = "2017-03-01";
//		Date date = DateUtil.parse(dateStr, "yyyy-MM-dd");
		
//		HashUtil.apHash("");
		
//		Singleton.get("con");
		
		Console.log("Hello World...");
		
		
	}
	
}
