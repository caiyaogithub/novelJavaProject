package com.novel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author cy
 *
 * @date 2015��7��24�� ����8:38:38
 *
 * @Description �����ļ���һЩ����
 */
public class FileUtils {
	/**
	 * ���ļ����������ݶ�ȡ���ַ�����
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @return �ļ�����
	 */
	public static String getStringFromFile(String filePath) {
		File file = new File(filePath) ;
		if(!file.exists()){
			 return "" ;
		}
		/**
		 * �����ļ���ȡ�������� :
		 * ֻҪ�ж����ֳ����ı���Ϳ����ˣ�GBK��UTF-8����������WindowsĬ�ϵı�����GBK������һ��ֻҪ�ж�UTF-8�����ʽ��
   		 *����UTF-8�����ʽ���ı��ļ�����ǰ3���ֽڵ�ֵ����-17��-69��-65
		 */
		try{
			byte[] firstThreeByte = new byte[3] ;
			InputStream in = new FileInputStream(file) ;
			in.read(firstThreeByte) ;
			in.close() ;
			String encoding = "" ;
			if(firstThreeByte[0] == -17 && firstThreeByte[1] == -16 && firstThreeByte[2] == -65){
				encoding = "utf-8" ;
			}else{
				encoding = "gbk" ;
			}
 			InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);      
			Long filelength = file.length() / 2 ; // �÷�����ȡ�����ļ��ֽڳ��ȣ�
												  //����Ҫ��������char���飬charռ�����ֽڣ�
												  //byteһ���ֽڣ����Գ���2��ʾ���Ǹ��ļ����ַ�����
			char[] filecontent = new char[filelength.intValue()] ; 
			read.read(filecontent) ;
			return new String(filecontent) ;
		}catch(Exception e ){
			e.printStackTrace();
			return "" ;
		}
	}

	/**
	 * ���ַ���д���ļ�
	 * 
	 * @param content
	 *            �ַ�������
	 * @param filePath
	 *            �ļ�·��
	 * @throws IOException
	 */
	public static void writeStringToFile(String content, String filePath)
			throws IOException {

		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file);
		out.write(content.getBytes());
		out.close();
	}
	/**
	 * ɾ��ָ�����ļ� 
	 * @param filePath�ļ�·�� 
	 */
	public static void deleteFile(String filePath ) {
		File file = new File(filePath) ;
		if(file.exists()){
			file.delete() ;
		}
	}
}
