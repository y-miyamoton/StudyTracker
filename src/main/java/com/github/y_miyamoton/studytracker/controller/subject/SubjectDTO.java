package com.github.y_miyamoton.studytracker.controller.subject;

import com.github.y_miyamoton.studytracker.entity.SubjectEntity;

import java.time.LocalDateTime;

public record SubjectDTO(
        long subjectId,
        long userId,
        String name,
        String colorCode,
        String description,
        boolean archived,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static SubjectDTO toDTO(SubjectEntity entity) {
        return new SubjectDTO(
                entity.getSubjectId(),
                entity.getUserId(),
                entity.getName(),
                entity.getColorCode(),
                entity.getDescription(),
                entity.isArchived(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
