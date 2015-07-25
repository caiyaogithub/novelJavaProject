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
 * @date 2015��7��23�� ����10:56:48
 *
 * @Description ServerService ��ʵ����
 */
public class ServerServiceImpl implements ServerService {

	@Override
	public DataPack login(DataPack<User> dataPack) {
		DataPack returnData = new DataPack();
		if (!InitUser.users
				.containsKey(((User) dataPack.getObject()).getName())) {
			returnData.setSuccess(false);
			returnData.setResultInfo("���û������ڣ�");
		} else {
			returnData.setSuccess(true);
			returnData.setResultInfo("��½�ɹ�!");
		}
		return returnData;
	}

	@Override
	public DataPack register(DataPack<User> dataPack) {
		DataPack returnPack = new DataPack();
		User user = dataPack.getObject();
		if (InitUser.users.containsKey(user.getName())) {
			returnPack.setSuccess(false);
			returnPack.setResultInfo("���û��Ѿ����ڣ�");
		} else {
			if (XmlUtil.writeUserToXml(user)) {
				returnPack.setSuccess(true);
				returnPack.setResultInfo("���û�ע��ɹ�");
				// ����InitUser.users�е�����
				try {
					Class.forName("com.novel.util.InitUser");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				returnPack.setSuccess(false);
				returnPack.setResultInfo("�û���Ϣд��ʧ��");
			}
		}
		return returnPack;
	}

	@Override
	public DataPack<Novel> download(DataPack dataPack) {
		/**
		 * ͨ��id�Ų�ѯ��С˵�Ƿ���� ������ڽ����ļ�д����� ��װ��DataPack������
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
			failDataPack.setResultInfo("��ȡС˵�б���ִ���");
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
		returnPack.setResultInfo("С˵�Ѿ�д��DataPack����");
		return returnPack;
	}

	@Override
	public DataPack upload(DataPack<Novel> dataPack) {
		Novel novel = dataPack.getObject() ;
		novel.setId(IdGenerator.nextId());
		DataPack returnUpload = new DataPack() ;
		if(XmlUtil.writeNovelToFile(novel)){
			returnUpload.setSuccess(true);
			returnUpload.setResultInfo("�ϴ��ɹ�");
		}else{
			returnUpload.setSuccess(false);
			returnUpload.setResultInfo("�ϴ�ʧ��");
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
			 * TODO ������ط�û���ж�
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
		returnGetList.setResultInfo("�ɹ���ȡ" + category + "������С˵����Ϣ"); 
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
