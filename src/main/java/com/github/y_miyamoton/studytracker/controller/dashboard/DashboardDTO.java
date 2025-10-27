package com.github.y_miyamoton.studytracker.controller.dashboard;

import com.github.y_miyamoton.studytracker.entity.SubjectMinuteEntity;

import java.util.List;

public record DashboardDTO(
        int todayMinutes,
        int weekMinutes,
        int monthMinutes,
        List<SubjectMinuteEntity> bySubject
) {
}
