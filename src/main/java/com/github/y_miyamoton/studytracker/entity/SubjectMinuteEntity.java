package com.github.y_miyamoton.studytracker.entity;

public record SubjectMinuteEntity(
        Long subjectId,
        String subjectName,
        Integer totalMinutes,
        String colorCode
) {
}
