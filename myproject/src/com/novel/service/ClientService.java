package com.novel.service;

import java.util.List;

import com.novel.entity.DataPack;
import com.novel.entity.Novel;
import com.novel.entity.User;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����10:51:26
 *
 * @Description ����ͻ�����Ҫ�Ĺ����б�
 * 
 */
public interface ClientService {
	/**
	 * �ͻ���������������������ݰ������ھ��ǵ�½��ע��
	 * @param requestPack �������ݰ�
	 * @return ���������ص����ݰ� 
	 */
	public DataPack sendRequestToServer(DataPack<User> requestPack ) ;
}
