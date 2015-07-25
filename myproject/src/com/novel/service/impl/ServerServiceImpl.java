package com.novel.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.novel.entity.DataPack;
import com.novel.entity.Novel;
import com.novel.entity.User;
import com.novel.service.ServerService;
import com.novel.util.FileUtils;
import com.novel.util.IdGenerator;
import com.novel.util.InitUser;
import com.novel.util.XmlUtil;

/**
 * @author cy
 *
 * @date 2015年7月23日 上午10:56:48
 *
 * @Description ServerService 的实现类
 */
public class ServerServiceImpl implements ServerService {

	@Override
	public DataPack login(DataPack<User> dataPack) {
		DataPack returnData = new DataPack();
		if (!InitUser.users
				.containsKey(((User) dataPack.getObject()).getName())) {
			returnData.setSuccess(false);
			returnData.setResultInfo("该用户不存在！");
		} else {
			returnData.setSuccess(true);
			returnData.setResultInfo("登陆成功!");
		}
		return returnData;
	}

	@Override
	public DataPack register(DataPack<User> dataPack) {
		DataPack returnPack = new DataPack();
		User user = dataPack.getObject();
		if (InitUser.users.containsKey(user.getName())) {
			returnPack.setSuccess(false);
			returnPack.setResultInfo("该用户已经存在！");
		} else {
			if (XmlUtil.writeUserToXml(user)) {
				returnPack.setSuccess(true);
				returnPack.setResultInfo("该用户注册成功");
				// 更新InitUser.users中的内容
				try {
					Class.forName("com.novel.util.InitUser");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				returnPack.setSuccess(false);
				returnPack.setResultInfo("用户信息写入失败");
			}
		}
		return returnPack;
	}

	@Override
	public DataPack<Novel> download(DataPack dataPack) {
		/**
		 * 通过id号查询该小说是否存在 如果存在将该文件写入对象 包装进DataPack并返回
		 */
		int id = Integer.parseInt(dataPack.getResultInfo());
		Novel newNovel = null;
		List<Novel> novels;
		try {
			novels = XmlUtil.getNovelListFromXml("config/novelsInfo.xml");
		} catch (Exception e) {
			e.printStackTrace();
			DataPack failDataPack = new DataPack();
			failDataPack.setSuccess(false);
			failDataPack.setResultInfo("读取小说列表出现错误");
			return failDataPack;
		}
		for (Novel novel : novels) {
			if (novel.getId() == id) {
				/*
				 * public Novel( String category, String name, String author,
				 * String description, String content) {
				 */
				newNovel = new Novel(novel.getCategory(), novel.getName(),
						novel.getAuthor(), novel.getDescription(),
						FileUtils.getStringFromFile("novel/" + novel.getName()
								+ ".txt"));
				break;
			}
		}
		DataPack<Novel> returnPack = new DataPack();
		returnPack.setObject(newNovel);
		returnPack.setSuccess(true);
		returnPack.setResultInfo("小说已经写入DataPack对象");
		return returnPack;
	}

	@Override
	public DataPack upload(DataPack<Novel> dataPack) {
		Novel novel = dataPack.getObject() ;
		novel.setId(IdGenerator.nextId());
		DataPack returnUpload = new DataPack() ;
		if(XmlUtil.writeNovelToFile(novel)){
			returnUpload.setSuccess(true);
			returnUpload.setResultInfo("上传成功");
		}else{
			returnUpload.setSuccess(false);
			returnUpload.setResultInfo("上传失败");
		}
		return returnUpload ;
	}

	@Override
	public DataPack<List<Novel>> getListByCategory(DataPack<Novel> dataPack) {
		String category = dataPack.getResultInfo() ;
		List<Novel> novels = new ArrayList<Novel>() ;
		List<Novel> allNovels = null ;
		try {
			allNovels = XmlUtil.getNovelListFromXml("config/novelsInfo.xml");
		} catch (Exception e) {
			e.printStackTrace();
			/**
			 * TODO ：这个地方没有判断
			 */
		}
		for(Novel novel : allNovels ){
			if(novel.getCategory().equals(category)){
				novels.add(novel) ;
			}
		}
		DataPack<List<Novel>> returnGetList = new DataPack<List<Novel>>() ;
		returnGetList.setObject(novels); 
		returnGetList.setSuccess(true);
		returnGetList.setResultInfo("成功获取" + category + "下所有小说的信息"); 
		return returnGetList ;
 	}
	@Override
	public DataPack checkExist(DataPack dataPack) {
		
		DataPack returnCheck = new DataPack() ;
		returnCheck.setSuccess(false);
		try {
			List<Novel> allNovels = XmlUtil.getNovelListFromXml("config/novelsInfo.xml");
			for(Novel novel : allNovels ){
				if(String.valueOf(novel.getId()).equals(dataPack.getResultInfo())){
					returnCheck.setSuccess(true);
					break ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnCheck ;
	} 
}
