package com.novel.start;

import com.novel.socket.ServerStartThread;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����11:09:22
 *
 * @Description  ����Server�� 
 */
public class StartServer {
	public static void main(String[] args) {
		new Thread(new ServerStartThread()).start(); 
	}
}
