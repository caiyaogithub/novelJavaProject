package com.novel.entity;

import java.io.Serializable;

/**
 * @author cy 
 *
 * @date 2015年7月23日 上午11:39:49
 *
 * @Description 封装用户信息，用于注册和登陆 
 */
public class User  implements Serializable{
	private String name ;
	private String passwd ;
	public User() {
		super();
	}
	public User(String name, String passwd) {
		super();
		this.name = name;
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", passwd=" + passwd + "]";
	}
	
}
