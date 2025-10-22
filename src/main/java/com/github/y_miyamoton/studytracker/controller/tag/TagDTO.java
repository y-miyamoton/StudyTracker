package com.github.y_miyamoton.studytracker.controller.tag;

import com.github.y_miyamoton.studytracker.entity.TagEntity;

import java.time.LocalDateTime;

public record TagDTO(
        long tagId,
        long userId,
        String name,
        String colorCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TagDTO toDTO(TagEntity entity) {
        return new TagDTO(
                entity.tagId(),
                entity.userId(),
                entity.name(),
                entity.colorCode(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }
}
