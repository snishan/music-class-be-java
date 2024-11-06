package com.mcms.music.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class TeacherDTO {
    private Long id;
    private String teacherId;
    private String teacherName;
    private String mail;
    private String contactNo;
    private boolean status;
    private Long[] classIds;
}
