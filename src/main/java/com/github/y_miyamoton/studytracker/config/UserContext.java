package com.github.y_miyamoton.studytracker.config;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public Long currentUserId() {
        return 1L;
    }
}
