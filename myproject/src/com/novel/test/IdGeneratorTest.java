package com.novel.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.novel.util.IdGenerator;

/**
 * @author cy 
 *
 * @date 2015年7月24日 下午2:52:17
 *
 * @Description 测试id生成
 */
public class IdGeneratorTest {

	@Test
	public void testNextId() {
		IdGenerator.nextId() ;
	}
	
}
