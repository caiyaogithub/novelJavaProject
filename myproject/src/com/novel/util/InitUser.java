package com.novel.util;

import java.util.Map;

import com.novel.entity.User;

/**
 * @author cy 
 *
 * @date 2015年7月23日 下午2:45:09
 *
 * @Description 初始化User,将xml中的User信息读入内存
 */
public class InitUser {
	public static Map<String , User> users ;
	static {
		XmlUtil.initUser() ;
	}
}
