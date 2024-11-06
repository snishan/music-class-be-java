package com.mcms.music.controller;

import com.mcms.music.dto.ClassDTO;
import com.mcms.music.service.Impl.ClassServiceImpl;
import com.mcms.music.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music-api/v1/classes")
@CrossOrigin
public class ClassController {

    @Autowired
    private ClassServiceImpl classService;

    @Autowired
    private ClassDTO classDto;

    @Autowired
    private StandardResponse standardResponse;

    @PostMapping("")
    public ResponseEntity<StandardResponse> createTutorial(@RequestBody ClassDTO classDTO) {
        classDto = classService.addClass(classDTO);

        if (classDto != null) {
            return ResponseEntity.ok(new StandardResponse(201, "success", classDto));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updateTeacher(@PathVariable("id") Long classId, @RequestBody ClassDTO classDTO) {
        classDto = classService.updateClass(classId, classDTO);

        if (classDto != null) {
            return ResponseEntity.ok(new StandardResponse(200, "success", classDto));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> deleteClass(@PathVariable("id") Long classId) {
        boolean deleted = classService.deleteClass(classId);

        if (deleted) {
            return ResponseEntity.ok(new StandardResponse(200, "success", deleted));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", false));
        }
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> getClass(@PathVariable("id") Long classId){
        classDto = classService.getClass(classId);

        if (classDto != null) {
            return ResponseEntity.ok(new StandardResponse(200, "success", classDto));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }

    @GetMapping(path = "")
    public ResponseEntity<StandardResponse>  getAllClass(){
        List<ClassDTO> classDtos = classService.getAllClass();

        if (classDtos != null || !classDtos.isEmpty()) {
            return ResponseEntity.ok(new StandardResponse(200, "success", classDtos));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "something went wrong", null));
        }
    }
}
