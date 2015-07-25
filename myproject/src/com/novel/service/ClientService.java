package com.novel.service;

import java.util.List;

import com.novel.entity.DataPack;
import com.novel.entity.Novel;
import com.novel.entity.User;

/**
 * @author cy 
 *
 * @date 2015年7月23日 上午10:51:26
 *
 * @Description 定义客户端需要的功能列表
 * 
 */
public interface ClientService {
	/**
	 * 客户端向服务器发送请求数据包，现在就是登陆和注册
	 * @param requestPack 请求数据包
	 * @return 服务器返回的数据包 
	 */
	public DataPack sendRequestToServer(DataPack<User> requestPack ) ;
}
