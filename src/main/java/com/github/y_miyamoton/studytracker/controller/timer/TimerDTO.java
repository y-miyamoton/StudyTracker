package com.github.y_miyamoton.studytracker.controller.timer;

import java.time.LocalDateTime;

public record TimerDTO (
    Long timerId,
    Long userId,
    Long subjectId,
    Integer focusMinutes,
    Integer breakMinutes,
    LocalDateTime startAt,
    LocalDateTime endAt
) {
}
