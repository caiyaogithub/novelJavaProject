package com.novel.socket;

import java.net.ServerSocket;
import java.net.Socket;

import com.novel.util.XmlUtil;

/**
 * @author cy
 *
 * @date 2015年7月23日 上午11:12:15
 *
 * @Description 服务器端进程
 */
public class ServerStartThread implements Runnable {

	@Override
	public void run() {
		/**
		 * 1. 创建ServerSocket监听8800端口 2. 通过无限循环一直监听客户端请求 3. 创建线程，处理客户端请求Socket
		 */
		try {
			ServerSocket server = new ServerSocket(XmlUtil.getServerPort());

			while (true) {
				Socket socket = server.accept();
				new ServerHandleClientThread(socket).start();
				;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
