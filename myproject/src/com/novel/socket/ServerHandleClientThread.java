package com.novel.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.novel.constant.Constant;
import com.novel.entity.DataPack;
import com.novel.service.ServerService;
import com.novel.service.ServiceFactory;
import com.novel.service.impl.ServerServiceImpl;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����11:22:36
 *
 * @Description �������˴���һ���ͻ���������߳�
 */
public class ServerHandleClientThread extends Thread {
	private Socket socket ;
	private ServerService serverService ;
	

	public ServerHandleClientThread(Socket socket ) {
		this.socket = socket;
		this.serverService = ServiceFactory.createServerService() ; // ÿһ���߳�ʹ��һ��ServerService��
	}
	@Override
	public void run() {
		/**
		 * 1. ��ȡ�ͻ��˷�������DataPack����
		 * 2. ������������󣬵�����Ӧ��service��ķ�����ȡ���
		 * 3. ����ȡ�Ľ�����͵��ͻ���
		 */
		try{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream()) ;
			DataPack dataPack = (DataPack)in.readObject() ;
			int commond = dataPack.getCommond() ;
			switch( commond ){
				case Constant.CMD_LOGIN :
					DataPack returnLoginData = 
							serverService.login(dataPack) ;
					sendObjectToClient(returnLoginData) ;
					break ;
				case Constant.CMD_REGISTER :
					DataPack returnRegisterData = 
							serverService.register(dataPack) ;
					sendObjectToClient(returnRegisterData);
					break ;
				case Constant.CMD_DOWNLOAD :
					/**
					 * ����С˵id����С˵��С˵id�����resultInfo��
					 */
					DataPack returnDownloadData = 
							serverService.download(dataPack) ;
					sendObjectToClient(returnDownloadData) ;
					break ;
				case Constant.CMD_UPLOAD :
					/**
					 * �ϴ�С˵���ɷ�����������id��
					 */
					DataPack returnUploadData = 
							serverService.upload(dataPack) ;
					sendObjectToClient(returnUploadData) ;
					break; 
				case Constant.CMD_GET_LIST_BY_CATEGORY :
					/**
					 * ��ȡָ��С˵����µ�����С˵(��װ��DtaPack�е�obj���ԣ�ArrayList<Novle>())
					 */
					DataPack returnGetListData = 
							serverService.getListByCategory(dataPack) ;
					sendObjectToClient(returnGetListData) ;
					break; 
				case Constant.CMD_CHECK_EXIST :
					/**
					 * ���С˵�Ƿ���� ������ʱС˵id��resultInfo��
					 * �����Ƿ�ɹ� ��success �ֶ���
					 */
					DataPack returnCheckExistData = 
							serverService.checkExist(dataPack) ;
					sendObjectToClient(returnCheckExistData) ;
					break ;
				default :
					break ;
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
	public void sendObjectToClient(DataPack dataPack ){
		ObjectOutputStream out = null ;
		try{
			out = new ObjectOutputStream(socket.getOutputStream()) ;
			out.writeObject(dataPack) ;
			out.flush();
		}catch(Exception e ){
			e.printStackTrace(); 
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
