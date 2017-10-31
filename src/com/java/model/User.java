package com.java.model;

public class User {
	private int userId;
	private String userName;
	private String password;
	private String nickName;
	private String imageName;
	private String mood;

	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public User() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
}
