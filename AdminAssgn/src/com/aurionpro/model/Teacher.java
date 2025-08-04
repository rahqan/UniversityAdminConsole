package com.aurionpro.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
	private int id;
	private String name;
	private String qualification;
	private List<Subject> subjects = new ArrayList<>();

	public Teacher(int id, String name, String qualification, List<Subject> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.qualification = qualification;
		this.subjects = subjects;
	}
	
	public Teacher(int id, String name, String qualification) {
		this.id = id;
		this.name = name;
		this.qualification = qualification;
	}

	public Teacher() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
