package com.github.y_miyamoton.studytracker.entity;

import java.time.LocalDateTime;

public record AppAuditEntity(
        Long appId,
        Long userId,
        String action, // CREATE / UPDATE / DELETE / TIMER_START / TIMER_STOP / BREAK_START / BREAK_STOP
        String entity, // SUBJECT / TAG / LOG / POMODORO
        Long entityId,
        String detail,
        LocalDateTime createdAt
) {
}
