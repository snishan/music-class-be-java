package com.mcms.music.service.Impl;

import com.mcms.music.dto.TeacherDTO;
import com.mcms.music.entity.Class;
import com.mcms.music.entity.Teacher;
import com.mcms.music.repository.ClassRepository;
import com.mcms.music.repository.TeacherRepository;
import com.mcms.music.service.TeacherService;
import com.mcms.music.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private Common common;

    public TeacherDTO addTeacher(TeacherDTO teacherDTO) {
        Teacher teacherEntity = new Teacher();
        teacherEntity.setTeacherId(common.generateId("TEC"));
        teacherEntity.setTeacherName(teacherDTO.getTeacherName());
        teacherEntity.setMail(teacherDTO.getMail());
        teacherEntity.setContactNo(teacherDTO.getContactNo());
        teacherEntity.setStatus(true); // status is active by default

        Long[] classIds = teacherDTO.getClassIds();

        // Retrieve classes based on the provided classIds
        Set<Class> classes = new HashSet<>();
        for (Long classId : classIds) {
            Class classEntity = classRepository.findById(classId)
                    .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));
            classes.add(classEntity);
            classEntity.getTeachers().add(teacherEntity); // Add teacher to the class
        }
        teacherEntity.setClasses(classes);

        try {
            Teacher savedTeacherEntity = teacherRepository.save(teacherEntity);
            return convertToDTO(savedTeacherEntity);
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to add teacher: " + e.getMessage());
        }
    }

    public TeacherDTO updateTeacher(Long teacherId, TeacherDTO teacherDTO) {
        Teacher existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + teacherId));

        existingTeacher.setTeacherName(teacherDTO.getTeacherName());
        existingTeacher.setMail(teacherDTO.getMail());
        existingTeacher.setContactNo(teacherDTO.getContactNo());
        existingTeacher.setStatus(teacherDTO.isStatus());

        Long[] classIds = teacherDTO.getClassIds();

        // Retrieve classes based on the provided classIds
        Set<Class> newClasses = new HashSet<>();
        for (Long classId : classIds) {
            Class classEntity = classRepository.findById(classId)
                    .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));
            newClasses.add(classEntity);
        }

        // Remove teacher association from classes not included in the updated list
        Set<Class> oldClasses = existingTeacher.getClasses();
        for (Class oldClass : oldClasses) {
            if (!newClasses.contains(oldClass)) {
                oldClass.getTeachers().remove(existingTeacher);
            }
        }

        // Add teacher association to new classes
        for (Class classEntity : newClasses) {
            classEntity.getTeachers().add(existingTeacher);
        }

        try {
            Teacher updatedTeacher = teacherRepository.save(existingTeacher);

            if (updatedTeacher != null){
                return teacherDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to update teacher: " + e.getMessage());
        }
    }

    public boolean deleteTeacher(Long teacherId) {
        Teacher existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + teacherId));

        // Remove teacher association from all classes
        Set<Class> classes = existingTeacher.getClasses();
        for (Class classEntity : classes) {
            classEntity.getTeachers().remove(existingTeacher);
        }

        try {
            teacherRepository.delete(existingTeacher);
            return true;
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to delete teacher: " + e.getMessage());
        }
    }

    public TeacherDTO getTeacher(Long teacherId) {
        try {
            Optional<Teacher> teacherEntity = teacherRepository.findById(teacherId);

            if(teacherEntity.isPresent()){ // check teacher is available or not
                return convertToDTO(teacherEntity.get());
            }

            return null;

        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to get teacher: " + e.getMessage());
        }
    }

    public List<TeacherDTO> getAllTeachers() {
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        List<Teacher> teachers = teacherRepository.findAll();

        for(Teacher teacher: teachers){
            TeacherDTO teacherDTO = convertToDTO(teacher);
            teacherDTOS.add(teacherDTO);
        }
        return teacherDTOS;
    }

    // Helper method to convert Teacher entity to DTO
    private TeacherDTO convertToDTO(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setTeacherId(teacher.getTeacherId());
        teacherDTO.setTeacherName(teacher.getTeacherName());
        teacherDTO.setMail(teacher.getMail());
        teacherDTO.setContactNo(teacher.getContactNo());
        teacherDTO.setStatus(teacher.isStatus());
        // Set classIds
        Set<Long> classIds = new HashSet<>();
        for (Class classEntity : teacher.getClasses()) {
            classIds.add(classEntity.getId());
        }
        teacherDTO.setClassIds(classIds.toArray(new Long[0]));

        return teacherDTO;
    }

}
