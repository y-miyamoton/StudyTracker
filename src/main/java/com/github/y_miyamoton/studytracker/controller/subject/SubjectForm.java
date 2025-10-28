package com.github.y_miyamoton.studytracker.controller.subject;

import com.github.y_miyamoton.studytracker.entity.SubjectEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record SubjectForm(
        @NotBlank(message = "科目名は必須です")
        @Size(max = 100, message = "科目名は100文字以内で入力してください")
        String name,
        String colorCode,
        @Size(max = 255, message = "説明は255文字以内で入力してください")
        String description
) {

    public static SubjectForm fromEntity(SubjectEntity subjectEntity) {
        return new SubjectForm(
                subjectEntity.getName(),
                subjectEntity.getColorCode(),
                subjectEntity.getDescription()
        );
    }

    public SubjectEntity toEntity(Long userId) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setUserId(userId);
        subjectEntity.setName(name());
        subjectEntity.setColorCode(colorCode());
        subjectEntity.setDescription(description());
        subjectEntity.setArchived(false);
        return subjectEntity;
    }

    public SubjectEntity toEntity(Long subjectId, Long userId) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setSubjectId(subjectId);
        subjectEntity.setUserId(userId);
        subjectEntity.setName(name());
        subjectEntity.setColorCode(colorCode());
        subjectEntity.setDescription(description());
        subjectEntity.setArchived(false);
        return subjectEntity;
    }
}
