package com.novel.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.novel.entity.Novel;
import com.novel.entity.User;

/**
 * @author cy
 *
 * @date 2015年7月23日 下午3:19:06
 *
 * @Description 关于xml的操作
 */
public class XmlUtil {
	/**
	 * 目标xml为 config/users.xml
	 * 
	 * @param user
	 *            将要被写入xml的User对象
	 * @return 是否成功
	 */
	public static boolean writeUserToXml(User user) {
		try {
			Document doc = getDocumentFromXml("config/users.xml");
			Element newUserElement = doc.createElement("user");
			Element newUsernameElement = doc.createElement("name");
			Text nameTextNode = doc.createTextNode("nameValue");
			nameTextNode.setNodeValue(user.getName());
			newUsernameElement.appendChild(nameTextNode);
			Element newUserPwdElement = doc.createElement("pwd");
			Text pwdTextNode = doc.createTextNode("pwdValue");
			pwdTextNode.setNodeValue(user.getName());
			newUserPwdElement.appendChild(pwdTextNode);
			newUserElement.appendChild(newUsernameElement);
			newUserElement.appendChild(newUserPwdElement);
			Element usersElement = (Element) doc.getElementsByTagName("users")
					.item(0);
			usersElement.appendChild(newUserElement);

			writeDocumentToFile(doc, "config/users.xml");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param doc
	 *            XML中的Document对象
	 * @param filePath
	 *            输出的文件路径
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	private static void writeDocumentToFile(Document doc, String filePath)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		// 写入到硬盘
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		/** 编码 */
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filePath));
		transformer.transform(source, result);
	}

	/**
	 * 加载config/users.xml中用户信息到对象中
	 * 
	 * @return 加载后的对象
	 */
	public static Map<String, User> initUser() {
		InitUser.users = new HashMap<String, User>();
		try {
			Document doc = getDocumentFromXml("config/users.xml");
			NodeList usersNodeList = doc.getElementsByTagName("user");
			for (int i = 0; i < usersNodeList.getLength(); i++) {
				Element userElement = (Element) usersNodeList.item(i);
				String userName = ((Element) (userElement
						.getElementsByTagName("name").item(0))).getFirstChild()
						.getNodeValue();
				String passwd = ((Element) (userElement
						.getElementsByTagName("pwd").item(0))).getFirstChild()
						.getNodeValue();
				InitUser.users.put(userName, new User(userName, passwd));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return InitUser.users;
		}
	}

	/**
	 * 从xml中获取服务器运行的端口
	 * 
	 * @return server.xml文件中的端口号
	 */
	public static int getServerPort() {
		try {
			Document doc = getDocumentFromXml("config/server.xml");
			int serverPort = Integer.parseInt(doc
					.getElementsByTagName("server-port").item(0)
					.getFirstChild().getNodeValue());
			return serverPort;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * @param xmlPath
	 *            xml文件的位置
	 * @return 这个xml文件相应的Document对象
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document getDocumentFromXml(String xmlPath)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlPath);
		return doc;
	}

	/**
	 * 读取xml中小说的信息到List中
	 * 
	 * @param novelId
	 *            小说id
	 * @return 小说列表
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static List<Novel> getNovelListFromXml(String filePath) throws SAXException, IOException, ParserConfigurationException {
		List<Novel> novelList = new ArrayList<Novel>();
		Document doc = getDocumentFromXml(filePath);
		NodeList novels = doc.getElementsByTagName("novel");
		for (int i = 0; i < novels.getLength(); i++) {
			Element novel = ((Element) novels.item(i));
			int id = Integer.parseInt(novel.getElementsByTagName("id").item(0)
					.getFirstChild().getNodeValue());
			String name = novel.getElementsByTagName("name").item(0)
					.getFirstChild().getNodeValue();
			String author = novel.getElementsByTagName("author").item(0)
					.getFirstChild().getNodeValue();
			String category = novel.getElementsByTagName("category").item(0)
					.getFirstChild().getNodeValue();
			String description = novel.getElementsByTagName("description")
					.item(0).getFirstChild().getNodeValue();
			
			Novel oneNovel = new Novel(id, category, name, author, description);
			novelList.add(oneNovel);
		}
		return novelList ;
	}
	/**
	 * 将Novel信息写入到config/novelsInfo.xml中并且将小说内容写入到novel文件夹下
	 * @param novel 小说对象
	 * @return 是否写入成功 
	 * TODO：确定原子操作
	 */
	public static boolean writeNovelToFile(Novel novel ) {
		/**
		 * 先将小说内容写入到novel文件夹下，再将小说信息写入到config/novelsInfo.xml中
		 */
		try{
			FileUtils.writeStringToFile(novel.getContent(), "novel/" + novel.getName() + ".txt");
			XmlUtil.writeNovelInfoToXml(novel) ;
			return true ;
		}catch(Exception e ){
			/**
			 * 如果写入小说到文件中出现问题，要将已经写入的信息删除
			 * 这段代码应该很少执行到 ~~~~
			 * 
			 */
			System.out.println("小说写入文件失败，正在回滚~~");
			FileUtils.deleteFile("novel/" + novel.getName() + ".txt") ;
			XmlUtil.deleteNovelInfoFromXml(novel) ;
			e.printStackTrace();
			return false ;
		}
	}

	/**
	 * 从config/novelsInfo.xml中删除与novel对象相对应的的novel标签，根据ID号判断是否相同
	 * 
	 * @param novel
	 *            小说对象
	 */
	public static void deleteNovelInfoFromXml(Novel novel) {
		try {
			Document doc = getDocumentFromXml("config/novelsInfo.xml");
			Element novelsElement = (Element) doc
					.getElementsByTagName("novels").item(0);
			NodeList novelElements = novelsElement
					.getElementsByTagName("novel");

			Node deleteElement = null;
			for (int i = 0; i < novelElements.getLength(); i++) {
				String id = ((Element) novelElements.item(i))
						.getElementsByTagName("id").item(0).getFirstChild()
						.getNodeValue();
				if (id.equals(String.valueOf(novel.getId()))) {
					deleteElement = novelElements.item(i);
					break;
				}
			}
			novelsElement.removeChild(deleteElement);
			writeDocumentToFile(doc, "config/novlesInfo.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将小说信息写入到config/novelsInfo.xml文件中
	 * @param novel小说对象
	 */
	public static void writeNovelInfoToXml(Novel novel){
		Document doc = null ;
		try {
			doc = getDocumentFromXml("config/novelsInfo.xml");
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
		Element novelDocument = (Element)doc.createElement("novel") ;
		// id
		Element novelIdDocument = (Element)doc.createElement("id") ;
		Text novelIdTextNode = doc.createTextNode("idValue") ;
		novelIdTextNode.setNodeValue(String.valueOf(novel.getId()));
		novelIdDocument.appendChild(novelIdTextNode);
		// name
		Element novelNameDocument = (Element)doc.createElement("name") ;
		Text novelNameTextNode = doc.createTextNode("nameValue") ;
		novelNameTextNode.setNodeValue(String.valueOf(novel.getName()));
		novelNameDocument.appendChild(novelNameTextNode);
		// author
		Element novelAuthorDocument = (Element)doc.createElement("author") ;
		Text novelAuthorTextNode = doc.createTextNode("authorValue") ;
		novelAuthorTextNode.setNodeValue(String.valueOf(novel.getAuthor()));
		novelAuthorDocument.appendChild(novelAuthorTextNode);
		// category
		Element novelCategoryDocument = (Element)doc.createElement("category") ;
		Text novelCategoryTextNode = doc.createTextNode("categoryValue") ;
		novelCategoryTextNode.setNodeValue(String.valueOf(novel.getCategory()));
		novelCategoryDocument.appendChild(novelCategoryTextNode);
		// description 
		Element novelDescriptionDocument = (Element)doc.createElement("description") ;
		Text novelDescriptionTextNode = doc.createTextNode("descriptionValue") ;
		novelDescriptionTextNode.setNodeValue(String.valueOf(novel.getDescription()));
		novelDescriptionDocument.appendChild(novelDescriptionTextNode);
		
		novelDocument.appendChild(novelIdDocument) ;
		novelDocument.appendChild(novelNameDocument) ;
		novelDocument.appendChild(novelAuthorDocument) ;
		novelDocument.appendChild(novelCategoryDocument) ;
		novelDocument.appendChild(novelDescriptionDocument) ;
		doc.getElementsByTagName("novels").item(0).appendChild(novelDocument) ;
		// 写到文件中
		try {
			writeDocumentToFile(doc, "config/novelsInfo.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
