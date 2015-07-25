package com.novel.service;

import com.novel.service.impl.ClientServiceImpl;
import com.novel.service.impl.ServerServiceImpl;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����10:53:52
 *
 * @Description ���ڲ��������ʵ����
 * 
 * 
 * ����������վ�Ȼû����
 * 
 */
public class ServiceFactory {
	/**
	 * �����ͻ��˹�����
	 * @return 
	 */
	public static ClientService createClientService(){
		return new ClientServiceImpl() ;
	}
	/**
	 * �����������˹���ʵ���� 
	 * @return
	 */
	public static ServerService createServerService(){
		return new ServerServiceImpl() ;
	}
}
