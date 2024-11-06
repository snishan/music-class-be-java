package com.mcms.music.util;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class StandardResponse {
    private int code;
    private String message;
    private Object data;
}
