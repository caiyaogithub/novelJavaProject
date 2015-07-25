package com.novel.util;

import java.util.Scanner;

/**
 * @author cy
 *
 * @date 2015年7月24日 上午8:59:05
 *
 * @Description 控制台输入对象Scanner的单例
 */
public class SingltonScanner {
	private static Scanner sc = new Scanner(System.in);

	private SingltonScanner() {
	}

	public static Scanner instance() {
		return sc;
	}
}
