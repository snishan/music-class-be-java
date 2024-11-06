package com.mcms.music.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class StudentDTO {
    private Long id;
    private String studentId;
    private String studentName;
    private String mail;
    private String contactNo;
    private boolean status;
    private Long[] classIds;
}
