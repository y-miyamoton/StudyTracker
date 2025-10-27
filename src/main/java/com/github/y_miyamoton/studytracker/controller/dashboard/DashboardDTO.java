package com.github.y_miyamoton.studytracker.controller.dashboard;

public record DashboardDTO(
        int todayMinutes,
        int weekMinutes,
        int monthMinutes
) {
}
