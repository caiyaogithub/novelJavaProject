package com.novel.entity;

import java.io.Serializable;

/**
 * @author cy 
 *
 * @date 2015��7��23�� ����11:41:11
 *
 * @Description ��װС˵��Ϣ������С˵���ϴ�������
 */
public class Novel implements Serializable{
	private int id ;
	private String category ;
	private String name ;
	private String author ;
	private String description ;
	private String content ;
	public Novel() {
		super();
	}
	/**
	 * �ͻ����ϴ�ͼ���ʱ����Ҫ����id��id�ɷ����������� 
	 * @param category
	 * @param name
	 * @param author
	 * @param description
	 * @param content
	 */
	public Novel( String category, String name, String author,
			String description, String content) {
		super();
		this.category = category;
		this.name = name;
		this.author = author;
		this.description = description;
		this.content = content;
	}
	
	public Novel(int id, String category, String name, String author,
			String description, String content) {
		super();
		this.id = id;
		this.category = category;
		this.name = name;
		this.author = author;
		this.description = description;
		this.content = content;
	}
	/**
	 * �洢С˵��Ϣ����Ҫ�洢���ݣ� �洢������Ҫ�������ص�ʱ��  
	 */
	public Novel(int id, String category, String name, String author,
			String description) {
		super();
		this.id = id;
		this.category = category;
		this.name = name;
		this.author = author;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Novel [id=" + id + ", category=" + category + ", name=" + name
				+ ", author=" + author + ", description=" + description
				+ ", content=" + content + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null ){
			return false ;
		}
		Novel novel = (Novel)obj ;
		if(novel.getId() == this.getId() ){
			return true ;
		}else{
			return false ;
		}
	}
}
