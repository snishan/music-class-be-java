package com.mcms.music.service;

import com.mcms.music.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {
    public TeacherDTO addTeacher(TeacherDTO teacherDTO);
    public TeacherDTO updateTeacher(Long teacherId, TeacherDTO teacherDTO);
    public boolean deleteTeacher(Long teacherId);
    public TeacherDTO getTeacher(Long teacherId);
    public List<TeacherDTO> getAllTeachers();

}
