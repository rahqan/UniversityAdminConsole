package com.aurionpro.service;

import java.util.List;

import com.aurionpro.dao.TeacherDao;
import com.aurionpro.model.Subject;
import com.aurionpro.model.Teacher;
import com.aurionpro.model.TeacherProfile;

public class TeacherService {
    private final TeacherDao teacherDao = new TeacherDao();

    public boolean addTeacher(Teacher teacher) {
        if (teacher == null || teacher.getName() == null)
            throw new IllegalArgumentException("Invalid teacher data");

        return teacherDao.addTeacher(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherDao.getAllTeachers();
    }

    public Teacher getTeacherById(int teacherId) {
        if (teacherId <= 0)
            throw new IllegalArgumentException("Invalid teacher ID");

        return teacherDao.getTeacherById(teacherId);
    }

    public List<Teacher> searchTeachers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty())
            throw new IllegalArgumentException("Search keyword is empty");

        return teacherDao.searchTeachers(keyword);
    }

    public boolean deleteTeacher(int teacherId) {
        if (teacherId <= 0)
            throw new IllegalArgumentException("Invalid teacher ID");

        boolean deleted = teacherDao.deleteTeacher(teacherId);
        if (!deleted)
            throw new IllegalStateException("Teacher not found or already deleted");

        return true;
    }


    public boolean assignSubjectToTeacher(int subjectId, int teacherId) {
        if (subjectId <= 0 || teacherId <= 0)
            throw new IllegalArgumentException("Invalid subject or teacher ID");

        boolean assigned = teacherDao.assignSubjectToTeacher(subjectId, teacherId);
        if (!assigned)
            throw new IllegalStateException("Assignment failed");

        return true;
    }

    public List<Subject> getSubjectsForTeacher(int teacherId) {
        if (teacherId <= 0)
            throw new IllegalArgumentException("Invalid teacher ID");

        return teacherDao.getSubjectsForTeacher(teacherId);
    }

    public List<Teacher> getTeachersBySubjectId(int subjectId) {
        if (subjectId <= 0)
            throw new IllegalArgumentException("Invalid subject ID");

        return teacherDao.getTeacherBySubjectId(subjectId);
    }


    public boolean addTeacherProfile(TeacherProfile profile) {
        if (profile == null || profile.getTeacherId() <= 0)
            throw new IllegalArgumentException("Invalid teacher profile data");

        return teacherDao.addTeacherProfile(profile);
    }

    public boolean updateTeacherProfile(TeacherProfile profile) {
        if (profile == null || profile.getTeacherId() <= 0)
            throw new IllegalArgumentException("Invalid teacher profile data");

        return teacherDao.updateTeacherProfile(profile);
    }

    public TeacherProfile getTeacherProfile(int teacherId) {
        if (teacherId <= 0)
            throw new IllegalArgumentException("Invalid teacher ID");

        return teacherDao.getTeacherProfile(teacherId);
    }

    public boolean deleteTeacherProfile(int teacherId) {
        if (teacherId <= 0)
            throw new IllegalArgumentException("Invalid teacher ID");

        return teacherDao.deleteTeacherProfile(teacherId);
    }


    public Teacher getTeacherDetails(int teacherId) {
        if (teacherId <= 0)
            throw new IllegalArgumentException("Invalid teacher ID");

        return teacherDao.getTeacherDetails(teacherId);
    }
}
