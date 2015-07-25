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
 * @date 2015年7月24日 下午2:40:04
 *
 * @Description 小说id生成器
 */
public class IdGenerator {
	/**
	 * @return 新的小说id 
	 */
	public static int nextId(){
		/**
		 * 获取所有小说列表，找出最大的小说id加1
		 */
		List<Novel> novels = null ;
		try {
			novels = XmlUtil.getNovelListFromXml("config/novelsInfo.xml");
			if(novels.size() == 0  ){
				/**
				 * 没有小说信息，id为1 小说ID从1开始 
				 */
				return 1 ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// 将List中的Novel按照id从小到大排序
		Collections.sort(novels, new Comparator<Novel>(){
			@Override
			public int compare(Novel o1, Novel o2) {
				/**
				 * 第一个小于第二个 返回 负数
				 * 相等  返回 0
				 * 第一个大于第二个  正数  
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
