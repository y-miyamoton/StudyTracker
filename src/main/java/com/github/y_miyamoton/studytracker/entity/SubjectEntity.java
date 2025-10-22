package com.github.y_miyamoton.studytracker.entity;

import java.time.LocalDateTime;


public record SubjectEntity (
        Long subjectId,
        Long userId,
        String name,
        String colorCode,
        String description,
        boolean archived,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
}
