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
 * @date 2015年7月23日 上午11:22:36
 *
 * @Description 服务器端处理一个客户端请求的线程
 */
public class ServerHandleClientThread extends Thread {
	private Socket socket ;
	private ServerService serverService ;
	

	public ServerHandleClientThread(Socket socket ) {
		this.socket = socket;
		this.serverService = ServiceFactory.createServerService() ; // 每一个线程使用一个ServerService类
	}
	@Override
	public void run() {
		/**
		 * 1. 获取客户端发过来的DataPack对象
		 * 2. 解析里面的请求，调用相应的service里的方法获取结果
		 * 3. 将获取的结果发送到客户端
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
					 * 根据小说id下载小说，小说id存放在resultInfo中
					 */
					DataPack returnDownloadData = 
							serverService.download(dataPack) ;
					sendObjectToClient(returnDownloadData) ;
					break ;
				case Constant.CMD_UPLOAD :
					/**
					 * 上传小说，由服务器端生成id号
					 */
					DataPack returnUploadData = 
							serverService.upload(dataPack) ;
					sendObjectToClient(returnUploadData) ;
					break; 
				case Constant.CMD_GET_LIST_BY_CATEGORY :
					/**
					 * 获取指定小说类别下的所有小说(封装在DtaPack中的obj属性，ArrayList<Novle>())
					 */
					DataPack returnGetListData = 
							serverService.getListByCategory(dataPack) ;
					sendObjectToClient(returnGetListData) ;
					break; 
				case Constant.CMD_CHECK_EXIST :
					/**
					 * 检查小说是否存在 ，发送时小说id在resultInfo中
					 * 返回是否成功 在success 字段中
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
