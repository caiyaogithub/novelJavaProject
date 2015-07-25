package com.novel.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.novel.entity.Novel;

/**
 * @author cy 
 *
 * @date 2015��7��24�� ����2:40:04
 *
 * @Description С˵id������
 */
public class IdGenerator {
	/**
	 * @return �µ�С˵id 
	 */
	public static int nextId(){
		/**
		 * ��ȡ����С˵�б��ҳ�����С˵id��1
		 */
		List<Novel> novels = null ;
		try {
			novels = XmlUtil.getNovelListFromXml("config/novelsInfo.xml");
			if(novels.size() == 0  ){
				/**
				 * û��С˵��Ϣ��idΪ1 С˵ID��1��ʼ 
				 */
				return 1 ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// ��List�е�Novel����id��С��������
		Collections.sort(novels, new Comparator<Novel>(){
			@Override
			public int compare(Novel o1, Novel o2) {
				/**
				 * ��һ��С�ڵڶ��� ���� ����
				 * ���  ���� 0
				 * ��һ�����ڵڶ���  ����  
				 */
				return o1.getId() - o2.getId() ;
			}
		});
		/*for(Novel novel : novels ){
			System.out.println("name : " + novel.getName() + "id : " + novel.getId());
		}*/
		return novels.get(novels.size() - 1 ).getId() + 1 ;
	}
}
