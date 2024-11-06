package com.mcms.music.controller;

import com.mcms.music.dto.StudentDTO;
import com.mcms.music.dto.TeacherDTO;
import com.mcms.music.service.Impl.StudentServiceImpl;
import com.mcms.music.service.Impl.TeacherServiceImpl;
import com.mcms.music.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music-api/v1/students")
@CrossOrigin()
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private StudentDTO student;

    @PostMapping("")
    public ResponseEntity<StandardResponse> createStudent(@RequestBody StudentDTO studentDTO) {
        student = studentService.addStudent(studentDTO);

        if (student != null) {
            return ResponseEntity.ok(new StandardResponse(201, "success", student));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updateStudent(@PathVariable("id") Long studentId, @RequestBody StudentDTO studentDTO) {
        student = studentService.updateStudent(studentId, studentDTO);

        if (student != null) {
            return ResponseEntity.ok(new StandardResponse(200, "success", student));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> deleteStudent(@PathVariable("id") Long studentId) {
        boolean deleted = studentService.deleteStudent(studentId);

        if (deleted) {
            return ResponseEntity.ok(new StandardResponse(200, "success", deleted));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", false));
        }

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> getStudent(@PathVariable("id") Long studentId){
        student = studentService.getStudent(studentId);

        if (student != null) {
            return ResponseEntity.ok(new StandardResponse(200, "success", student));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @GetMapping(path = "")
    public ResponseEntity<StandardResponse> getAllStudents(){
        List<StudentDTO> students = studentService.getAllStudents();

        if (students != null || !students.isEmpty()) {
            return ResponseEntity.ok(new StandardResponse(200, "success", students));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }
}
