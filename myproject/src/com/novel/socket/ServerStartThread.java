package com.novel.socket;

import java.net.ServerSocket;
import java.net.Socket;

import com.novel.util.XmlUtil;

/**
 * @author cy
 *
 * @date 2015��7��23�� ����11:12:15
 *
 * @Description �������˽���
 */
public class ServerStartThread implements Runnable {

	@Override
	public void run() {
		/**
		 * 1. ����ServerSocket����8800�˿� 2. ͨ������ѭ��һֱ�����ͻ������� 3. �����̣߳�����ͻ�������Socket
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
