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
 * @date 2015年7月23日 下午3:58:05
 *
 * @Description 客户端启动线程 
 */
public class ClientStartThread implements Runnable {
	@Override
	public void run() {
		displayMainMenu() ;
	}
	/**
	 * 显示主界面
	 */
	public void displayMainMenu(){
		System.out.println("登陆：1     注册: 2  退出：0 ");
		Scanner mainMenuInput  = SingltonScanner.instance() ;
		while(true){
			String choise = mainMenuInput.next() ;
			if(choise.equals("0")){
				System.exit(0);
			}else if(choise.matches("[12]")){
				handleMainMenuInput(choise) ;
				break ;
			}else{
				System.out.println("输入格式不对，请重新输入：");
			}
		}
	}
	/**
	 * 处理主界面下用户的输入 
	 * @param mainInput 用户输入
	 */
	public void handleMainMenuInput(String mainInput ) {
		if(mainInput.equals("1")){
			displayLoginMenu() ;
		}else if(mainInput.equals("2")){
			displayRegisterMenu() ;
		}
	}
	public void displayFunctionMenu(){
		 System.out.println("上传  1  下载   2  退出  0 ") ;
		 Scanner functionMenuInput = SingltonScanner.instance() ;
		 while(true){
				String choise = functionMenuInput.next() ;
				if(choise.equals("0")){
					System.exit(0);
				}else if(choise.matches("[12]")){
					handleFunctionMenuInput(choise) ;
					break ;
				}else{
					System.out.println("输入格式不对，请重新输入：");
				}
			}
	}
	public void handleFunctionMenuInput(String choise ) {
		/**
		 * 这些数字真不好理解
		 */
		if(choise.equals("1")){
			displayUploadMenu() ;
		}else if(choise.equals("2")){
			displayDownloadMenu() ;
		}
	}
	/**
	 * 显示上传界面 
	 */
	public void displayUploadMenu(){
		Scanner sc = new Scanner(System.in) ;
		System.out.println("请输入： ");
		System.out.print("分类： ");
		String category = sc.next() ;
		System.out.print("书名 ： ") ;
		String name = sc.next() ;
		System.out.print("作者 ： ") ;
		String author = sc.next() ;
		System.out.print("描述 ： ") ;
		String description = sc.next() ;
		System.out.print("本地路径： ");
		String localPath = sc.next() ;
		/*public Novel(int id, String category, String name, String author,
				String description, String content) {*/
		Novel novel = 
				new Novel( category , name , author , description , FileUtils.getStringFromFile(localPath)) ;
		
		handleUploadMenuInput(novel);
	}
	/**
	 * 处理在上传界面的输入
	 * @param novel 小说
	 */
	public void handleUploadMenuInput(Novel novel) {
		DataPack requestPack = new DataPack() ;
		requestPack.setObject(novel);
		requestPack.setCommond(Constant.UPLOAD_CMD);
		boolean success = (new ClientServiceImpl()).sendRequestToServer(requestPack).isSuccess() ;
		System.out.println("上传状态：" + (success ? "上传成功" : "上传失败"));
		// 上传完成，回到主界面
		displayFunctionMenu() ;
	}
	/**
	 * 显示下载小说的分类
	 */
	public void displayDownloadMenu() {
		/**
		 * TODO: 这个列表也可以通过获取xml中的数据再显示 
		 */
		System.out.println("1. 武侠  2. 言情   0. 回到上一层  ");
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
				System.out.println("输入格式不对，请重新输入： ");
			}
		}
		
	}
	public void handleDownloadMenuInput(String choise ) {
		DataPack requestPack = new DataPack() ;
		requestPack.setCommond(Constant.GET_LIST_BY_CATEGORY_CMD) ;
		requestPack.setResultInfo((choise.equals("1") ? "武侠" : "言情")) ;
		
		// 获取指定类别的小说列表并输出
		System.out.println(getNovelListStringByCategory(requestPack) + "   \n回到上一级  0 ") ;
		Scanner sc = SingltonScanner.instance() ;
		while(true){
			System.out.println("请输入将要下载的小说ID或者0退出：");
			String novelChoise = sc.next() ;
			if(novelChoise.equals("0")){
				displayDownloadMenu() ;
				return ;
			}else if(novelChoise.matches("[0-9]+")){
				handleNovelIdInput(novelChoise) ;
				return ;
			}else{
				System.out.println("输入格式不对，请重新输入：");
			}
		}
	}
	/**
	 * 处理小说id号输入界面的输入
	 * @param chovelChoise 小说id号
	 */
	public void handleNovelIdInput(String novelChoise) {
			if(checkNovelExist(novelChoise)){
				handleNovelDownload(novelChoise) ;
				return ;
			}else{
				System.out.println("对不起，该小说不存在 ！");
				displayDownloadMenu();
				return ;
			}
	}
	/**
	 * 通过小说id检查小说是否存在 
	 * @param novelChoise 小说id号
	 * @return 是否存在
	 */
	public boolean checkNovelExist(String novelChoise){
		DataPack requestPack = new DataPack() ;
		requestPack.setCommond(Constant.CHECK_EXIST_CMD );
		requestPack.setResultInfo(novelChoise);
		return (new ClientServiceImpl()).sendRequestToServer(requestPack).isSuccess() ;
	}
	/**
	 * 获取某类别小说列表（ArrayList<Novel>）
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
	 * 处理小说下载 
	 * @param novelChoise 小说id
	 */
	public void handleNovelDownload(String novelChoise){
		System.out.println(downloadNovel(novelChoise) ? "下载成功" : "下载失败" );
		// 下载完成， 回到下载页面
		displayDownloadMenu();
	}
	/**
	 * 下载小说
	 * @param novelChoise 小说id号
	 * @return 是否成功
	 */
	public boolean downloadNovel(String novelChoise ){
		DataPack requestPack = new DataPack() ;
		requestPack.setCommond(Constant.DOWNLOAD_CMD);
		requestPack.setResultInfo(novelChoise) ;
		Novel novel = (Novel)(new ClientServiceImpl()).sendRequestToServer(requestPack).getObject() ;
		try{
			if(novel == null ){
				throw new Exception("没有获取到小说对象") ;
			}
			FileUtils.writeStringToFile(novel.getContent() , "download/" + novel.getName() + ".txt") ;
			return true ;
		}catch(Exception e ){
			e.printStackTrace();
			return false ;
		}
		
	}
	/**
	 * 显示注册页面
	 */
	public void displayRegisterMenu() {
		Scanner sc = new Scanner(System.in) ;
		System.out.println("请输入用户名：");
		String userName = sc.next() ;
		System.out.println("请输入密码：");
		String passwd = sc.next() ;
		User user = new User(userName , passwd ) ;
		handleRegisterInput(user) ;
	}
	/**
	 * 处理注册的输入
	 * @param user 注册的用户信息对象
	 */
	public void handleRegisterInput(User user) {
		DataPack<User> registerPack = new DataPack<User>() ;
		registerPack.setCommond(Constant.REGISTER_CMD);
		registerPack.setObject(user);
		DataPack registerResult = (new ClientServiceImpl()).sendRequestToServer(registerPack) ;
		/**
		 * TODO：处理注册后的数据包
		 */
		handleReturnRegister(registerResult) ;
	}
	/**
	 * 处理服务器对注册返回的数据
	 * @param registerResult 数据包 
	 */
	public void handleReturnRegister(DataPack registerResult) {
		if(registerResult.isSuccess()){
			/**
			 * 服务器端注册成功以后要将信息写入users Map中
			 */
			displayFunctionMenu() ;
		}else{
			/**
			 * 登陆未遂，会到主界面
			 */
			displayMainMenu() ;
		}
	}
	/**
	 * 显示登陆菜单
	 */
	public void displayLoginMenu() {
		Scanner sc = SingltonScanner.instance() ;
		System.out.println("请输入用户名：");
		String userName = sc.next() ;
		System.out.println("请输入密码：");
		String passwd = sc.next() ;
		User user = new User(userName , passwd ) ;
		handleLoginInput(user) ;
	}
	/**
	 * 处理登陆界面的输入
	 * @param user 登陆的用户信息
	 */
	public void handleLoginInput(User user) {
		DataPack<User> loginPack = new DataPack<User>() ;
		loginPack.setCommond(Constant.LOGIN_CMD);
		loginPack.setObject(user);
		DataPack loginResult = (new ClientServiceImpl()).sendRequestToServer(loginPack) ;
		handleReturnLogin(loginResult) ;
	}
	/**
	 * 处理服务器对登陆返回的结果
	 * @param loginResult 结果数据包 
	 */
	public void handleReturnLogin(DataPack loginResult) {
		if(loginResult.isSuccess()){
			displayFunctionMenu() ;
		}else{
			displayLoginMenu() ;
		}
	}
}
