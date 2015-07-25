package com.novel.start;

import com.novel.socket.ClientStartThread;

/**
 * @author cy 
 *
 * @date 2015年7月23日 下午3:56:19
 *
 * @Description 启动客户端
 */
public class StartClient {
	public static void main(String[] args) {
		new Thread(new ClientStartThread()).start(); 
	}
}
