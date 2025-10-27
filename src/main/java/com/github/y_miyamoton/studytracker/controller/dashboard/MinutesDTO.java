package com.github.y_miyamoton.studytracker.controller.dashboard;

import com.github.y_miyamoton.studytracker.entity.SubjectMinuteEntity;

import java.util.List;

public record MinutesDTO(
        List<SubjectMinuteEntity> byDay,
        List<SubjectMinuteEntity> byWeek,
        List<SubjectMinuteEntity> byMonth
) {
}
