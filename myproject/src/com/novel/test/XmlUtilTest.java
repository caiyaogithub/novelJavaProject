package com.novel.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.novel.entity.Novel;
import com.novel.util.IdGenerator;
import com.novel.util.XmlUtil;

/**
 * @author cy 
 *
 * @date 2015年7月24日 下午3:16:00
 *
 * @Description 
 */
public class XmlUtilTest {

	@Test
	public void testWriteUserToXml() {
		fail("Not yet implemented");
	}

	@Test
	public void testInitUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetServerPort() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDocumentFromXml() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNovelListFromXml() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteNovelToFile() {
		Novel novel = new Novel() ;
		novel.setAuthor("author");
		novel.setCategory("言情");
		novel.setContent("这是测试言情小说");
		novel.setDescription("这是小说描述");
		novel.setId(IdGenerator.nextId());
		novel.setName("这是一个测试小说名"); 
		XmlUtil.writeNovelToFile(novel) ;
		
	}

	@Test
	public void testWriteNovelInfoToXml() {
		fail("Not yet implemented");
	}

}
