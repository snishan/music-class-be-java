package com.mcms.music.controller;

import com.mcms.music.dto.TeacherDTO;
import com.mcms.music.service.Impl.TeacherServiceImpl;
import com.mcms.music.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music-api/v1/teachers")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private TeacherDTO teacher;

    @PostMapping("")
    public ResponseEntity<StandardResponse> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        teacher = teacherService.addTeacher(teacherDTO);

        if (teacher != null) {
            return ResponseEntity.ok(new StandardResponse(201, "success", teacher));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updateTeacher(@PathVariable("id") Long teacherId, @RequestBody TeacherDTO teacherDTO) {
        teacher = teacherService.updateTeacher(teacherId, teacherDTO);

        if (teacher != null) {
            return ResponseEntity.ok(new StandardResponse(200, "success", teacher));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> deleteTeacher(@PathVariable("id") Long teacherId) {
        boolean deleted = teacherService.deleteTeacher(teacherId);

        if (deleted) {
            return ResponseEntity.ok(new StandardResponse(200, "success", deleted));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", false));
        }

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> getTeacher(@PathVariable("id") Long teacherId){
        teacher = teacherService.getTeacher(teacherId);

        if (teacher != null) {
            return ResponseEntity.ok(new StandardResponse(200, "success", teacher));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @GetMapping(path = "")
    public ResponseEntity<StandardResponse> getAllTeachers(){
        List<TeacherDTO> teachers = teacherService.getAllTeachers();

        if (teachers != null || !teachers.isEmpty()) {
            return ResponseEntity.ok(new StandardResponse(200, "success", teachers));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }
}
