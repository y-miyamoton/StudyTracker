package com.github.y_miyamoton.studytracker.controller.log;

import com.github.y_miyamoton.studytracker.entity.LogEntity;

import java.time.LocalDateTime;

public record LogDTO(
        Long logId,
        Long userId,
        Long subjectId,
        String name,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Integer minutes,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static LogDTO toDTO(LogEntity entity) {
        return new LogDTO(
                entity.logId(),
                entity.userId(),
                null,
                null,
                entity.startAt(),
                entity.endAt(),
                entity.minutes(),
                entity.memo(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }

    public static LogDTO toDTO(LogEntity entity, String subjectName) {
        return new LogDTO(
                entity.logId(),
                entity.userId(),
                entity.subjectId(),
                subjectName,
                entity.startAt(),
                entity.endAt(),
                entity.minutes(),
                entity.memo(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }
}
