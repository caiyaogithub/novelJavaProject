package com.novel.service;

import com.novel.service.impl.ClientServiceImpl;
import com.novel.service.impl.ServerServiceImpl;

/**
 * @author cy 
 *
 * @date 2015年7月23日 上午10:53:52
 *
 * @Description 用于产生服务的实现类
 * 
 * 
 * 靠，这个玩艺竟然没有用
 * 
 */
public class ServiceFactory {
	/**
	 * 创建客户端功能类
	 * @return 
	 */
	public static ClientService createClientService(){
		return new ClientServiceImpl() ;
	}
	/**
	 * 创建服务器端功能实现类 
	 * @return
	 */
	public static ServerService createServerService(){
		return new ServerServiceImpl() ;
	}
}
