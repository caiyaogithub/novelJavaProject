package com.novel.util;

import java.util.Map;

import com.novel.entity.User;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����2:45:09
 *
 * @Description ��ʼ��User,��xml�е�User��Ϣ�����ڴ�
 */
public class InitUser {
	public static Map<String , User> users ;
	static {
		XmlUtil.initUser() ;
	}
}
