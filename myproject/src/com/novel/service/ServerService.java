package com.novel.service;

import java.util.List;

import com.novel.entity.DataPack;
import com.novel.entity.Novel;
import com.novel.entity.User;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����10:52:30
 *
 * @Description �������������Ҫʵ�ֵĹ����б�
 */
public interface ServerService {
	/**
	 * �����½
	 * @param dataPack ��½��������ݰ�
 	 * @return ���������ݰ�
	 */
	public DataPack login(DataPack<User> dataPack ) ;
	/**
	 * ����ע�Ṧ��
	 * @param dataPack ע���������ݰ����û�ע����Ϣ����ڸ����Object obj ������
	 * @return ע������ ������ֶ�success , resultInfo��
	 */
	public DataPack register(DataPack<User> dataPack ) ;
	/**
	 * �����û�����
	 * @param dataPack �û�С˵�����������ݰ� ��ʹ��С˵id���أ�С˵id�����resultInfo�ֶ���
	 * @return �������������ݣ�С˵��������Object obj�У�����������������Ϊnull
	 */
	public DataPack<Novel> download(DataPack dataPack ) ;
	/**
	 * �����ϴ�С˵
	 * @param dataPack �ϴ�С˵���������С˵��Ϣ����dataPack��obj�����У�id�ɷ����������ɡ�
	 * @return �ϴ������ ����success�С�
	 */
	public DataPack upload(DataPack<Novel> dataPack );
	/**
	 * ��ȡĳ����µ�����С˵��Ϣ
	 * @param dataPack ��resultInfo�д洢����ַ���
	 * @return С˵�б�
	 */
	public DataPack<List<Novel>> getListByCategory(DataPack<Novel> dataPack );
	/**
	 * ���С˵�Ƿ����
	 * @param dataPack ��resultInfo�д�ŵ���С˵id
	 * @return DataPack ��success�ֶδ�ŵ����жϽ��
	 */
	public DataPack checkExist(DataPack dataPack ) ;
}
