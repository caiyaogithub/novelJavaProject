package com.novel.start;

import com.novel.socket.ClientStartThread;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����3:56:19
 *
 * @Description �����ͻ���
 */
public class StartClient {
	public static void main(String[] args) {
		new Thread(new ClientStartThread()).start(); 
	}
}
