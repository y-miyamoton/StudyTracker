package com.github.y_miyamoton.studytracker.entity;

import java.time.LocalDateTime;

public record TagEntity(
        Long id,
        Long userId,
        String name,
        String colorCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
