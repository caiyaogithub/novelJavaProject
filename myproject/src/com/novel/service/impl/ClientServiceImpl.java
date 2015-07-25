package com.novel.service.impl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.novel.entity.DataPack;
import com.novel.entity.Novel;
import com.novel.entity.User;
import com.novel.service.ClientService;

/**
 * @author cy 
 *
 * @date 2015年7月23日 上午10:55:58
 *
 * @Description ClientService 的实现类
 */
public class ClientServiceImpl implements ClientService {

	@Override
	public DataPack sendRequestToServer(DataPack requestPack) {
		try{
			Socket socket = new Socket("localhost",8800) ;
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()) ;
			out.writeObject(requestPack);
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream()) ;
			DataPack returnPack = (DataPack)in.readObject() ;
			return returnPack ;
		}catch(Exception e ){
			e.printStackTrace(); 
			return null ;
		}
	}
}
