package com.novel.socket;

import java.util.List;
import java.util.Scanner;

import com.novel.constant.Constant;
import com.novel.entity.DataPack;
import com.novel.entity.Novel;
import com.novel.entity.User;
import com.novel.service.impl.ClientServiceImpl;
import com.novel.util.FileUtils;
import com.novel.util.SingltonScanner;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����3:58:05
 *
 * @Description �ͻ��������߳� 
 */
public class ClientStartThread implements Runnable {
	@Override
	public void run() {
		displayMainMenu() ;
	}
	/**
	 * ��ʾ������
	 */
	public void displayMainMenu(){
		System.out.println("��½��1     ע��: 2  �˳���0 ");
		Scanner mainMenuInput  = SingltonScanner.instance() ;
		while(true){
			String choise = mainMenuInput.next() ;
			if(choise.equals("0")){
				System.exit(0);
			}else if(choise.matches("[12]")){
				handleMainMenuInput(choise) ;
				break ;
			}else{
				System.out.println("�����ʽ���ԣ����������룺");
			}
		}
	}
	/**
	 * �������������û������� 
	 * @param mainInput �û�����
	 */
	public void handleMainMenuInput(String mainInput ) {
		if(mainInput.equals("1")){
			displayLoginMenu() ;
		}else if(mainInput.equals("2")){
			displayRegisterMenu() ;
		}
	}
	public void displayFunctionMenu(){
		 System.out.println("�ϴ�  1  ����   2  �˳�  0 ") ;
		 Scanner functionMenuInput = SingltonScanner.instance() ;
		 while(true){
				String choise = functionMenuInput.next() ;
				if(choise.equals("0")){
					System.exit(0);
				}else if(choise.matches("[12]")){
					handleFunctionMenuInput(choise) ;
					break ;
				}else{
					System.out.println("�����ʽ���ԣ����������룺");
				}
			}
	}
	public void handleFunctionMenuInput(String choise ) {
		/**
		 * ��Щ�����治�����
		 */
		if(choise.equals("1")){
			displayUploadMenu() ;
		}else if(choise.equals("2")){
			displayDownloadMenu() ;
		}
	}
	/**
	 * ��ʾ�ϴ����� 
	 */
	public void displayUploadMenu(){
		Scanner sc = new Scanner(System.in) ;
		System.out.println("�����룺 ");
		System.out.print("���ࣺ ");
		String category = sc.next() ;
		System.out.print("���� �� ") ;
		String name = sc.next() ;
		System.out.print("���� �� ") ;
		String author = sc.next() ;
		System.out.print("���� �� ") ;
		String description = sc.next() ;
		System.out.print("����·���� ");
		String localPath = sc.next() ;
		/*public Novel(int id, String category, String name, String author,
				String description, String content) {*/
		Novel novel = 
				new Novel( category , name , author , description , FileUtils.getStringFromFile(localPath)) ;
		
		handleUploadMenuInput(novel);
	}
	/**
	 * �������ϴ����������
	 * @param novel С˵
	 */
	public void handleUploadMenuInput(Novel novel) {
		DataPack requestPack = new DataPack() ;
		requestPack.setObject(novel);
		requestPack.setCommond(Constant.UPLOAD_CMD);
		boolean success = (new ClientServiceImpl()).sendRequestToServer(requestPack).isSuccess() ;
		System.out.println("�ϴ�״̬��" + (success ? "�ϴ��ɹ�" : "�ϴ�ʧ��"));
		// �ϴ���ɣ��ص�������
		displayFunctionMenu() ;
	}
	/**
	 * ��ʾ����С˵�ķ���
	 */
	public void displayDownloadMenu() {
		/**
		 * TODO: ����б�Ҳ����ͨ����ȡxml�е���������ʾ 
		 */
		System.out.println("1. ����  2. ����   0. �ص���һ��  ");
		Scanner sc = SingltonScanner.instance() ;
		while(true){
			String choise = sc.next() ;
			if(choise.equals("0")){
				displayFunctionMenu(); 
				return ;
			}
			if(choise.matches("[12]")){
				handleDownloadMenuInput(choise) ;
				return ;
			}else{
				System.out.println("�����ʽ���ԣ����������룺 ");
			}
		}
		
	}
	public void handleDownloadMenuInput(String choise ) {
		DataPack requestPack = new DataPack() ;
		requestPack.setCommond(Constant.GET_LIST_BY_CATEGORY_CMD) ;
		requestPack.setResultInfo((choise.equals("1") ? "����" : "����")) ;
		
		// ��ȡָ������С˵�б����
		System.out.println(getNovelListStringByCategory(requestPack) + "   \n�ص���һ��  0 ") ;
		Scanner sc = SingltonScanner.instance() ;
		while(true){
			System.out.println("�����뽫Ҫ���ص�С˵ID����0�˳���");
			String novelChoise = sc.next() ;
			if(novelChoise.equals("0")){
				displayDownloadMenu() ;
				return ;
			}else if(novelChoise.matches("[0-9]+")){
				handleNovelIdInput(novelChoise) ;
				return ;
			}else{
				System.out.println("�����ʽ���ԣ����������룺");
			}
		}
	}
	/**
	 * ����С˵id��������������
	 * @param chovelChoise С˵id��
	 */
	public void handleNovelIdInput(String novelChoise) {
			if(checkNovelExist(novelChoise)){
				handleNovelDownload(novelChoise) ;
				return ;
			}else{
				System.out.println("�Բ��𣬸�С˵������ ��");
				displayDownloadMenu();
				return ;
			}
	}
	/**
	 * ͨ��С˵id���С˵�Ƿ���� 
	 * @param novelChoise С˵id��
	 * @return �Ƿ����
	 */
	public boolean checkNovelExist(String novelChoise){
		DataPack requestPack = new DataPack() ;
		requestPack.setCommond(Constant.CHECK_EXIST_CMD );
		requestPack.setResultInfo(novelChoise);
		return (new ClientServiceImpl()).sendRequestToServer(requestPack).isSuccess() ;
	}
	/**
	 * ��ȡĳ���С˵�б�ArrayList<Novel>��
	 * @param dataPack
	 * @return
	 */
	public String getNovelListStringByCategory(DataPack<List<Novel>> dataPack ){
		List<Novel> novels = (List<Novel>)(new ClientServiceImpl()).sendRequestToServer(dataPack).getObject() ;
		StringBuffer novelList = new StringBuffer() ;
		for(Novel novel : novels ){
			novelList.append("id :" +novel.getId() + "\n" + novel.getName() + "\n" + novel.getAuthor() + "\n" + novel.getCategory() + "\n" + novel.getDescription() ) ;
			novelList.append("\n************************\n") ;
		}
		return novelList.toString() ;
	}
	/**
	 * ����С˵���� 
	 * @param novelChoise С˵id
	 */
	public void handleNovelDownload(String novelChoise){
		System.out.println(downloadNovel(novelChoise) ? "���سɹ�" : "����ʧ��" );
		// ������ɣ� �ص�����ҳ��
		displayDownloadMenu();
	}
	/**
	 * ����С˵
	 * @param novelChoise С˵id��
	 * @return �Ƿ�ɹ�
	 */
	public boolean downloadNovel(String novelChoise ){
		DataPack requestPack = new DataPack() ;
		requestPack.setCommond(Constant.DOWNLOAD_CMD);
		requestPack.setResultInfo(novelChoise) ;
		Novel novel = (Novel)(new ClientServiceImpl()).sendRequestToServer(requestPack).getObject() ;
		try{
			if(novel == null ){
				throw new Exception("û�л�ȡ��С˵����") ;
			}
			FileUtils.writeStringToFile(novel.getContent() , "download/" + novel.getName() + ".txt") ;
			return true ;
		}catch(Exception e ){
			e.printStackTrace();
			return false ;
		}
		
	}
	/**
	 * ��ʾע��ҳ��
	 */
	public void displayRegisterMenu() {
		Scanner sc = new Scanner(System.in) ;
		System.out.println("�������û�����");
		String userName = sc.next() ;
		System.out.println("���������룺");
		String passwd = sc.next() ;
		User user = new User(userName , passwd ) ;
		handleRegisterInput(user) ;
	}
	/**
	 * ����ע�������
	 * @param user ע����û���Ϣ����
	 */
	public void handleRegisterInput(User user) {
		DataPack<User> registerPack = new DataPack<User>() ;
		registerPack.setCommond(Constant.REGISTER_CMD);
		registerPack.setObject(user);
		DataPack registerResult = (new ClientServiceImpl()).sendRequestToServer(registerPack) ;
		/**
		 * TODO������ע�������ݰ�
		 */
		handleReturnRegister(registerResult) ;
	}
	/**
	 * �����������ע�᷵�ص�����
	 * @param registerResult ���ݰ� 
	 */
	public void handleReturnRegister(DataPack registerResult) {
		if(registerResult.isSuccess()){
			/**
			 * ��������ע��ɹ��Ժ�Ҫ����Ϣд��users Map��
			 */
			displayFunctionMenu() ;
		}else{
			/**
			 * ��½δ�죬�ᵽ������
			 */
			displayMainMenu() ;
		}
	}
	/**
	 * ��ʾ��½�˵�
	 */
	public void displayLoginMenu() {
		Scanner sc = SingltonScanner.instance() ;
		System.out.println("�������û�����");
		String userName = sc.next() ;
		System.out.println("���������룺");
		String passwd = sc.next() ;
		User user = new User(userName , passwd ) ;
		handleLoginInput(user) ;
	}
	/**
	 * �����½���������
	 * @param user ��½���û���Ϣ
	 */
	public void handleLoginInput(User user) {
		DataPack<User> loginPack = new DataPack<User>() ;
		loginPack.setCommond(Constant.LOGIN_CMD);
		loginPack.setObject(user);
		DataPack loginResult = (new ClientServiceImpl()).sendRequestToServer(loginPack) ;
		handleReturnLogin(loginResult) ;
	}
	/**
	 * ����������Ե�½���صĽ��
	 * @param loginResult ������ݰ� 
	 */
	public void handleReturnLogin(DataPack loginResult) {
		if(loginResult.isSuccess()){
			displayFunctionMenu() ;
		}else{
			displayLoginMenu() ;
		}
	}
}
