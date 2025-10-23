package com.github.y_miyamoton.studytracker.controller.tag;

import com.github.y_miyamoton.studytracker.entity.TagEntity;
import jakarta.validation.constraints.NotBlank;

public record TagForm(
        @NotBlank(message = "タグ名は必須です")
        String name,
        String colorCode
) {

    public static TagForm fromEntity(TagEntity tagEntity) {
        return new TagForm(
                tagEntity.name(),
                tagEntity.colorCode()
        );
    }

    public TagEntity toEntity(Long userId) {
        return new TagEntity(
                null,
                userId,
                name(),
                colorCode(),
                null,
                null
        );
    }

    public TagEntity toEntity(Long tagId, Long userId) {
        return new TagEntity(
                tagId,
                userId,
                name(),
                colorCode(),
                null,
                null
        );
    }

}
