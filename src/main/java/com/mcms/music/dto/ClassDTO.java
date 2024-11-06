package com.mcms.music.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class ClassDTO {
    private Long id;
    private String className;
    private String classDate;
    private String classStartTime;
    private boolean status;
}
