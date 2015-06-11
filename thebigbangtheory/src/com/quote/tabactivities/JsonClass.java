package com.quote.tabactivities;

import android.annotation.SuppressLint;
import java.io.*;

@SuppressLint("SdCardPath")
public class JsonClass {
	public static String readData() {
		byte[] data;
		String s = "";
		File file = new File("/sdcard/quote/jsonfile");
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);

			data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			s = new String(data, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return s;
	}
}
