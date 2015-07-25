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
 * @date 2015年7月24日 上午8:38:38
 *
 * @Description 关于文件的一些工具
 */
public class FileUtils {
	/**
	 * 将文件中所有内容读取到字符串中
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 文件内容
	 */
	public static String getStringFromFile(String filePath) {
		File file = new File(filePath) ;
		if(!file.exists()){
			 return "" ;
		}
		/**
		 * 处理文件读取乱码问题 :
		 * 只要判定两种常见的编码就可以了：GBK和UTF-8。由于中文Windows默认的编码是GBK，所以一般只要判定UTF-8编码格式。
   		 *对于UTF-8编码格式的文本文件，其前3个字节的值就是-17、-69、-65
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
			Long filelength = file.length() / 2 ; // 该方法获取的是文件字节长度，
												  //而我要创建的是char数组，char占两个字节，
												  //byte一个字节，所以除以2表示的是该文件的字符长度
			char[] filecontent = new char[filelength.intValue()] ; 
			read.read(filecontent) ;
			return new String(filecontent) ;
		}catch(Exception e ){
			e.printStackTrace();
			return "" ;
		}
	}

	/**
	 * 将字符串写入文件
	 * 
	 * @param content
	 *            字符串内容
	 * @param filePath
	 *            文件路径
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
	 * 删除指定的文件 
	 * @param filePath文件路径 
	 */
	public static void deleteFile(String filePath ) {
		File file = new File(filePath) ;
		if(file.exists()){
			file.delete() ;
		}
	}
}
