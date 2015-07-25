package com.novel.start;

import com.novel.socket.ServerStartThread;

/**
 * @author cy 
 *
 * @date 2015年7月23日 上午11:09:22
 *
 * @Description  启动Server端 
 */
public class StartServer {
	public static void main(String[] args) {
		new Thread(new ServerStartThread()).start(); 
	}
}
