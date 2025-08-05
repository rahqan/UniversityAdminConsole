package com.aurionpro.model;

public class Subject {
	private int subjectId;
	private String subjectName;

	public Subject(int subjectId, String subjectName) {
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}
	public Subject() {
		
	}

	public int getSubjectId() {
		return subjectId;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName=subjectName;
	}
	
	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectId(int subjectId) {
		this.subjectId=subjectId;
	}
}
