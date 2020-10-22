package com.dong;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommonTests {

	// 删除文件夹方法
	private void deleteDir(File file) {
		if (file.listFiles().length == 0)
			file.getAbsoluteFile().delete();
	}

	// 删除文件方法
	public void deleteFile(File file) {
		File[] temp = file.listFiles();
		for (int i = 0; i < temp.length; i++) {
			System.out.println(temp[i].getName());
			if (temp[i].isDirectory()) {
				if (temp[i].listFiles().length != 0)
					this.deleteFile(temp[i]); // 如果 文件夹里不为空 递归调用 方法
				this.deleteDir(temp[i]);
			} else {
				temp[i].delete();
			}
		}
	}

	public static void main(String[] args) {
		
		CommonTests m = new CommonTests();
		m.deleteFile(new File("D:\\share_space\\dubbo-admin234234"));

//		Map<String, Object> map = new HashMap<>();
//		Map<String, Object> cmap = new ConcurrentHashMap<>();
//		cmap.put("asd", 1);
//
//		int a = 2;
//		int b = 4;
////		int temp = 0;
////		temp = a;
////		a = b;
////		b = temp;
//		a = a ^ b;
//		b = b ^ a;
//		a = a ^ b;
//		System.out.println(a);
//		System.out.println(b);
//
//		int n = 13;
//		int count = 0;
//		while (n != 0) {
//			n &= n - 1;
//			count++;
//		}
//		System.out.println(count);

	}

}
