package com.novel.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.novel.entity.Novel;
import com.novel.util.IdGenerator;
import com.novel.util.XmlUtil;

/**
 * @author cy 
 *
 * @date 2015��7��24�� ����3:16:00
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
		novel.setCategory("����");
		novel.setContent("���ǲ�������С˵");
		novel.setDescription("����С˵����");
		novel.setId(IdGenerator.nextId());
		novel.setName("����һ������С˵��"); 
		XmlUtil.writeNovelToFile(novel) ;
		
	}

	@Test
	public void testWriteNovelInfoToXml() {
		fail("Not yet implemented");
	}

}
