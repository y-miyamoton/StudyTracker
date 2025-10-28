package com.github.y_miyamoton.studytracker.controller.log;

import com.github.y_miyamoton.studytracker.entity.LogEntity;

import java.time.LocalDateTime;

public record LogDTO(
        Long logId,
        Long userId,
        Long subjectId,
        String name,
        String colorCode,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Integer minutes,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static LogDTO toDTO(LogEntity entity) {
        return new LogDTO(
                entity.getLogId(),
                entity.getUserId(),
                entity.getSubjectId(),
                null,
                null,
                entity.getStartAt(),
                entity.getEndAt(),
                entity.getMinutes(),
                entity.getMemo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static LogDTO toDTO(LogEntity entity, String subjectName, String colorCode) {
        return new LogDTO(
                entity.getLogId(),
                entity.getUserId(),
                entity.getSubjectId(),
                subjectName,
                colorCode,
                entity.getStartAt(),
                entity.getEndAt(),
                entity.getMinutes(),
                entity.getMemo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}