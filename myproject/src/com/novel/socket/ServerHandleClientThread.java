package com.novel.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.novel.constant.Constant;
import com.novel.entity.DataPack;
import com.novel.service.ServerService;
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

	public ServerHandleClientThread(Socket socket) {
		this.socket = socket;
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
				case Constant.LOGIN_CMD :
					DataPack returnLoginData = 
					new ServerServiceImpl().login(dataPack) ;
					sendObjectToClient(returnLoginData) ;
					break ;
				case Constant.REGISTER_CMD :
					DataPack returnRegisterData = 
					new ServerServiceImpl().register(dataPack) ;
					sendObjectToClient(returnRegisterData);
					break ;
				case Constant.DOWNLOAD_CMD :
					/**
					 * 根据小说id下载小说，小说id存放在resultInfo中
					 */
					DataPack returnDownloadData = 
					new ServerServiceImpl().download(dataPack) ;
					sendObjectToClient(returnDownloadData) ;
					break ;
				case Constant.UPLOAD_CMD :
					/**
					 * 上传小说，由服务器端生成id号
					 */
					DataPack returnUploadData = 
					new ServerServiceImpl().upload(dataPack) ;
					sendObjectToClient(returnUploadData) ;
					break; 
				case Constant.GET_LIST_BY_CATEGORY_CMD :
					/**
					 * 获取指定小说类别下的所有小说(封装在DtaPack中的obj属性，ArrayList<Novle>())
					 */
					DataPack returnGetListData = 
					new ServerServiceImpl().getListByCategory(dataPack) ;
					sendObjectToClient(returnGetListData) ;
					break; 
				case Constant.CHECK_EXIST_CMD :
					/**
					 * 检查小说是否存在 ，发送时小说id在resultInfo中
					 * 返回是否成功 在success 字段中
					 */
					DataPack returnCheckExistData = 
					new ServerServiceImpl().checkExist(dataPack) ;
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
