package com.mcms.music.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Common {
    public String generateId(String type) {
        // Generate a random UUID and extract the first part to ensure uniqueness
        String uuid = UUID.randomUUID().toString();
        String uniquePart = uuid.split("-")[0]; // Extract the first part of the UUID
        return type + "-" + uniquePart;
    }
}
