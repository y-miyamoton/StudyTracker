package com.github.y_miyamoton.studytracker.entity;

import java.time.LocalDateTime;

public record LogEntity(
        Long logId,
        Long userId,
        Long subjectId,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Integer minutes,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
