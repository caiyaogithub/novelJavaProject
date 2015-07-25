package com.novel.util;

import java.util.Scanner;

/**
 * @author cy
 *
 * @date 2015��7��24�� ����8:59:05
 *
 * @Description ����̨�������Scanner�ĵ���
 */
public class SingltonScanner {
	private static Scanner sc = new Scanner(System.in);

	private SingltonScanner() {
	}

	public static Scanner instance() {
		return sc;
	}
}
