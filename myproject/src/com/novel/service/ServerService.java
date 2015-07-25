package com.novel.service;

import java.util.List;

import com.novel.entity.DataPack;
import com.novel.entity.Novel;
import com.novel.entity.User;

/**
 * @author cy 
 *
 * @date 2015年7月23日 上午10:52:30
 *
 * @Description 定义服务器端需要实现的功能列表
 */
public interface ServerService {
	/**
	 * 处理登陆
	 * @param dataPack 登陆请求的数据包
 	 * @return 处理结果数据包
	 */
	public DataPack login(DataPack<User> dataPack ) ;
	/**
	 * 处理注册功能
	 * @param dataPack 注册请求数据包，用户注册信息存放在该类的Object obj 变量中
	 * @return 注册结果， 存放于字段success , resultInfo中
	 */
	public DataPack register(DataPack<User> dataPack ) ;
	/**
	 * 处理用户下载
	 * @param dataPack 用户小说下载请求数据包 ，使用小说id下载，小说id存放在resultInfo字段中
	 * @return 服务器返回数据，小说对象存放在Object obj中，如果不存在则该属性为null
	 */
	public DataPack<Novel> download(DataPack dataPack ) ;
	/**
	 * 处理上传小说
	 * @param dataPack 上传小说的请求包，小说信息放在dataPack的obj属性中，id由服务器端生成。
	 * @return 上传结果。 放在success中。
	 */
	public DataPack upload(DataPack<Novel> dataPack );
	/**
	 * 获取某类别下的所有小说信息
	 * @param dataPack 在resultInfo中存储类别字符串
	 * @return 小说列表
	 */
	public DataPack<List<Novel>> getListByCategory(DataPack<Novel> dataPack );
	/**
	 * 检查小说是否存在
	 * @param dataPack 在resultInfo中存放的是小说id
	 * @return DataPack 中success字段存放的是判断结果
	 */
	public DataPack checkExist(DataPack dataPack ) ;
}
