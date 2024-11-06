package com.mcms.music.service;

import com.mcms.music.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    public StudentDTO addStudent(StudentDTO studentDTO);
    public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO);
    public boolean deleteStudent(Long studentId);
    public StudentDTO getStudent(Long studentId);
    public List<StudentDTO> getAllStudents();
}
