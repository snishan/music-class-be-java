package com.mcms.music.service.Impl;

import com.mcms.music.dto.StudentDTO;
import com.mcms.music.dto.TeacherDTO;
import com.mcms.music.entity.Class;
import com.mcms.music.entity.Student;
import com.mcms.music.entity.Teacher;
import com.mcms.music.repository.ClassRepository;
import com.mcms.music.repository.StudentRepository;
import com.mcms.music.service.StudentService;
import com.mcms.music.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private Common common;

    public StudentDTO addStudent(StudentDTO studentDTO) {
        Student studentEntity = new Student();
        studentEntity.setStudentId(common.generateId("STD"));
        studentEntity.setStudentName(studentDTO.getStudentName());
        studentEntity.setMail(studentDTO.getMail());
        studentEntity.setContactNo(studentDTO.getContactNo());
        studentEntity.setStatus(true); // status is active by default

        Long[] classIds = studentDTO.getClassIds();

        // Retrieve classes based on the provided classIds
        Set<Class> classes = new HashSet<>();
        for (Long classId : classIds) {
            Class classEntity = classRepository.findById(classId)
                    .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));
            classes.add(classEntity);
            classEntity.getStudents().add(studentEntity); // Add student to the class
        }
        studentEntity.setClasses(classes);

        try {
            Student savedStudentEntity = studentRepository.save(studentEntity);
            return convertToDTO(savedStudentEntity);
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to add student: " + e.getMessage());
        }
    }

    public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        existingStudent.setStudentName(studentDTO.getStudentName());
        existingStudent.setMail(studentDTO.getMail());
        existingStudent.setContactNo(studentDTO.getContactNo());
        existingStudent.setStatus(studentDTO.isStatus());

        Long[] classIds = studentDTO.getClassIds();

        // Retrieve classes based on the provided classIds
        Set<Class> newClasses = new HashSet<>();
        for (Long classId : classIds) {
            Class classEntity = classRepository.findById(classId)
                    .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));
            newClasses.add(classEntity);
        }

        // Remove student association from classes not included in the updated list
        Set<Class> oldClasses = existingStudent.getClasses();
        for (Class oldClass : oldClasses) {
            if (!newClasses.contains(oldClass)) {
                oldClass.getStudents().remove(existingStudent);
            }
        }

        // Add student association to new classes
        for (Class classEntity : newClasses) {
            classEntity.getStudents().add(existingStudent);
        }

        try {
            Student updatedStudent = studentRepository.save(existingStudent);

            if (updatedStudent != null){
                return studentDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to update student: " + e.getMessage());
        }
    }

    public boolean deleteStudent(Long studentId) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        // Remove student association from all classes
        Set<Class> classes = existingStudent.getClasses();
        for (Class classEntity : classes) {
            classEntity.getStudents().remove(existingStudent);
        }

        try {
            studentRepository.delete(existingStudent);
            return true;
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to delete student: " + e.getMessage());
        }
    }

    public StudentDTO getStudent(Long studentId) {
        try {
            Optional<Student> studentEntity = studentRepository.findById(studentId);

            if(studentEntity.isPresent()){ // check student is available or not
                return convertToDTO(studentEntity.get());
            }

            return null;

        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to get student: " + e.getMessage());
        }
    }

    public List<StudentDTO> getAllStudents() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<Student> students = studentRepository.findAll();

        for(Student student: students){
            StudentDTO studentDTO = convertToDTO(student);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    // Helper method to convert Teacher entity to DTO
    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setMail(student.getMail());
        studentDTO.setContactNo(student.getContactNo());
        studentDTO.setStatus(student.isStatus());
        // Set classIds
        Set<Long> classIds = new HashSet<>();
        for (Class classEntity : student.getClasses()) {
            classIds.add(classEntity.getId());
        }
        studentDTO.setClassIds(classIds.toArray(new Long[0]));

        return studentDTO;
    }
}
