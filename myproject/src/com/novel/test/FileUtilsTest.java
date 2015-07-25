package com.novel.test;

import java.io.IOException;

import org.junit.Test;

import com.novel.util.FileUtils;

public class FileUtilsTest {

	@Test
	public void testGetStringFromFile() {
		System.out.println(FileUtils.getStringFromFile("C:\\Users\\hello world\\Desktop\\《情断西藏》.TXT")) ;
	}

	@Test
	public void testWriteStringToFile() {
		try {
			FileUtils.writeStringToFile("这是中文文件内容", "C:\\Users\\hello world\\Desktop\\new.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
