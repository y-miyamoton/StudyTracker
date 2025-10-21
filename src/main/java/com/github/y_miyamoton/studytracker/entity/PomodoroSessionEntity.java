package com.github.y_miyamoton.studytracker.entity;

import java.time.LocalDateTime;

public record PomodoroSessionEntity(
        Long id,
        Long userId,
        Long subjectId,
        Integer focusMinutes,
        Integer breakMinutes,
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {
}
