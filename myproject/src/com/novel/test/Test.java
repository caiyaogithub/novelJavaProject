package com.novel.test;

public class Test {
	public static void main(String[] args) {
		/**
		 * 测试正则表达式是否支持中文
		 */
		String testString = "你好" ;
		System.out.println(testString.matches("(你好)|(你不好)"));
	}
}
