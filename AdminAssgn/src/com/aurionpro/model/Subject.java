package com.aurionpro.model;

public class Subject {
	private int subjectId;
	private String name;

	public Subject(int subjectId, String name) {
		this.subjectId = subjectId;
		this.name = name;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public String getName() {
		return name;
	}
}
