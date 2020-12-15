package com.dong;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FscsTests {

	public static void main(String[] args) throws IOException {

//		FileInputStream ins = new FileInputStream("C:\\Users\\XIEDONGXIAO\\Desktop\\5003.txt");

		Map<String, String> source = new HashMap<>(1500000);
		File file = new File("C:\\Users\\XIEDONGXIAO\\Desktop\\fscs\\5016_11_s4.txt");
		BufferedReader bw = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = bw.readLine()) != null) {
			source.put(line.trim(), line.trim());
		}
		bw.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\XIEDONGXIAO\\Desktop\\fscs\\5016_11_rec.txt"));
		file = new File("C:\\Users\\XIEDONGXIAO\\Desktop\\fscs\\5016_11.txt");
		bw = new BufferedReader(new FileReader(file));
		line = null;
		int count = 0;
		while ((line = bw.readLine()) != null) {
			if (source.get(line) == null) {
				writer.write(line);
				writer.newLine();
				writer.flush();
				count++;
			}
		}
		System.err.println(count);
		bw.close();
		writer.close();
		
	}

}
