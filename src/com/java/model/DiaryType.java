package com.java.model;

public class DiaryType {
	
	private int diaryTypeId;
	private String typeName;
	private int diaryCount;
	
	
	public DiaryType(String typeName) {
		super();
		this.typeName = typeName;
	}
	public DiaryType() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public int getDiaryTypeId() {
		return diaryTypeId;
	}
	public void setDiaryTypeId(int diaryTypeId) {
		this.diaryTypeId = diaryTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}
	
}
