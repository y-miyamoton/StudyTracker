package com.github.y_miyamoton.studytracker.controller.subject;

import com.github.y_miyamoton.studytracker.entity.SubjectEntity;



public record SubjectForm(
        String name,
        String colorCode,
        String description
) {

    public SubjectEntity toEntity(Long userId) {
        return new SubjectEntity(null, userId, name(), colorCode(), null, false, null, null);
    }
}
