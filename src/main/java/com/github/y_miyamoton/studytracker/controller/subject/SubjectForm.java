package com.github.y_miyamoton.studytracker.controller.subject;

import com.github.y_miyamoton.studytracker.entity.SubjectEntity;
import jakarta.validation.constraints.NotBlank;


public record SubjectForm(
        @NotBlank(message = "科目名は必須です")
        String name,
        String colorCode,
        String description
) {

    public static SubjectForm fromEntity(SubjectEntity subjectEntity) {
        return new SubjectForm(
                subjectEntity.name(),
                subjectEntity.colorCode(),
                subjectEntity.description()
        );
    }

    public SubjectEntity toEntity(Long userId) {
        return new SubjectEntity(null, userId, name(), colorCode(), null, false, null, null);
    }

    public SubjectEntity toEntity(Long id, Long userId) {
        return new SubjectEntity(id, userId, name(), colorCode(), null, false, null, null);
    }
}
