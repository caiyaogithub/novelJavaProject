package com.novel.test;

import java.io.IOException;

import org.junit.Test;

import com.novel.util.FileUtils;

public class FileUtilsTest {

	@Test
	public void testGetStringFromFile() {
		System.out.println(FileUtils.getStringFromFile("C:\\Users\\hello world\\Desktop\\��������ء�.TXT")) ;
	}

	@Test
	public void testWriteStringToFile() {
		try {
			FileUtils.writeStringToFile("���������ļ�����", "C:\\Users\\hello world\\Desktop\\new.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
