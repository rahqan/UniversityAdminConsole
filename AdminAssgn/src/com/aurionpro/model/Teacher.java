package com.aurionpro.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
	private int id;
	private String name;
	private List<Subject> subjects = new ArrayList<>();
	private TeacherProfile profile;

	public Teacher(int id, String name, List<Subject> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.subjects = subjects;
	}

	public Teacher(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Teacher(int id, String name, TeacherProfile profile) {
		this.id = id;
		this.name = name;
		this.profile = profile;
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

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public TeacherProfile getProfile() {
		return profile;
	}

	public void setProfile(TeacherProfile profile) {
		this.profile = profile;
	}

}
