package com.dong;

import com.dong.download.MultiTheradDownLoad;

public class DownLoadTest {

	public static void main(String[] args) {
		int threadNum = 4;
		String filepath = "http://down.sandai.net/thunder9/Thunder9.1.40.898.exe";

		MultiTheradDownLoad load = new MultiTheradDownLoad(filepath, threadNum);
		load.downloadPart();
	}

}
