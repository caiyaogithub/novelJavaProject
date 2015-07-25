package com.novel.entity;

import java.io.Serializable;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����11:30:56
 *
 * @Description �ͻ��˺ͷ��������໥�������ݵ����ݰ�
 */
public class DataPack<T> implements Serializable{
	private T object ;// ������͵����Ϳ�����User��Novel��List<Novel> 
	private boolean success ;
	/**
	 * TODO : ���������Ҫ�� 
	 */
	private String resultInfo ;
	private int commond ;
	public DataPack() {
		super();
	}
	public DataPack(T object, boolean success, String resultInfo, int commond) {
		super();
		this.object = object;
		this.success = success;
		this.resultInfo = resultInfo;
		this.commond = commond;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	public int getCommond() {
		return commond;
	}
	public void setCommond(int commond) {
		this.commond = commond;
	}
	@Override
	public String toString() {
		return "DataPack [object=" + object + ", result=" + success
				+ ", resultInfo=" + resultInfo + ", commond=" + commond + "]";
	} 
}
