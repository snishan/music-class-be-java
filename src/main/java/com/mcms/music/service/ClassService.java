package com.mcms.music.service;

import com.mcms.music.dto.ClassDTO;

import java.util.List;

public interface ClassService {
    public ClassDTO addClass(ClassDTO classDTO);
    public ClassDTO updateClass(Long classId, ClassDTO classDTO);
    public boolean deleteClass(Long classId);
    public ClassDTO getClass(Long classId);
    public List<ClassDTO> getAllClass();

}
