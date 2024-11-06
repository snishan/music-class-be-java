package com.mcms.music.service.Impl;

import com.mcms.music.dto.ClassDTO;
import com.mcms.music.entity.Class;
import com.mcms.music.repository.ClassRepository;
import com.mcms.music.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    public ClassDTO addClass(ClassDTO classDTO) {

        try {
            // map DTO values to entity
            Class classEntity = new Class();
            classEntity.setClassName(classDTO.getClassName());
            classEntity.setClassDate(classDTO.getClassDate());
            classEntity.setClassStartTime(classDTO.getClassStartTime());
            classEntity.setStatus(true);

            // Save the class entity to the database
            classEntity = classRepository.save(classEntity);

            // Map the class entity back to DTO
            return mapToDTO(classEntity);
        } catch (Exception e) {
            // Handle any exceptions and rethrow or return appropriate response
            throw new RuntimeException("Failed to add class: " + e.getMessage());
        }
    }

    public ClassDTO updateClass(Long classId, ClassDTO classDTO) {
        Class existingClass = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));

        existingClass.setClassName(classDTO.getClassName());
        existingClass.setClassDate(classDTO.getClassDate());
        existingClass.setClassStartTime(classDTO.getClassStartTime());
        existingClass.setStatus(classDTO.isStatus());

        try {
            Class updatedClass = classRepository.save(existingClass);
            if (updatedClass != null){
                return classDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to update class: " + e.getMessage());
        }
    }

    public boolean deleteClass(Long classId) {
        try {
            Optional<Class> classEntityOptional = classRepository.findById(classId);
            if (classEntityOptional.isPresent()) {
                Class classEntity = classEntityOptional.get();
                classRepository.delete(classEntity);
                return true;
            } else {
                // If the class with the given ID doesn't exist
                return false;
            }
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to delete class: " + e.getMessage());
        }
    }
    public ClassDTO getClass(Long classId) {
        try {
            Optional<Class> classEntity = classRepository.findById(classId);

            if(classEntity.isPresent()){ // check class is available or not
                return mapToDTO(classEntity.get());
            }

            return null;

        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to get class: " + e.getMessage());
        }
    }

    public List<ClassDTO> getAllClass() {
        List<ClassDTO> classDTOS = new ArrayList<>();
        List<Class> classes = classRepository.findAll();

        for(Class cl: classes){
            ClassDTO classDTO = mapToDTO(cl);
            classDTOS.add(classDTO);
        }
        return classDTOS;
    }
    /*** private methods ***/
    private ClassDTO mapToDTO(Class classEntity) {
        // Map properties
        ClassDTO classDTO = new ClassDTO();
        classDTO.setId(classEntity.getId());
        classDTO.setClassName(classEntity.getClassName());
        classDTO.setClassDate(classEntity.getClassDate());
        classDTO.setClassStartTime(classEntity.getClassStartTime());
        classDTO.setStatus(classEntity.isStatus());

        return classDTO;
    }
}
