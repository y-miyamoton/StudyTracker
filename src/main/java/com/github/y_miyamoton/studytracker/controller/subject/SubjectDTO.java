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
                entity.subjectId(),
                entity.userId(),
                entity.name(),
                entity.colorCode(),
                entity.description(),
                entity.archived(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }
}
