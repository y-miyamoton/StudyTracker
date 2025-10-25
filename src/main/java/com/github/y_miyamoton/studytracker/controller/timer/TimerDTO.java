package com.github.y_miyamoton.studytracker.controller.timer;

import com.github.y_miyamoton.studytracker.entity.LogEntity;
import com.github.y_miyamoton.studytracker.entity.TimerEntity;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimerDTO(
        Long timerId,
        Long userId,
        Long subjectId,
        Integer focusMinutes,
        Integer breakMinutes,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
    public static TimerDTO fromEntity(TimerEntity timerEntity) {
        return new TimerDTO(
                timerEntity.getTimerId(),
                timerEntity.getUserId(),
                timerEntity.getSubjectId(),
                timerEntity.getFocusMinutes(),
                timerEntity.getBreakMinutes(),
                timerEntity.getStartAt(),
                timerEntity.getEndAt()
        );
    }

    public LogEntity toLogAt(LocalDateTime endAt, String memo) {
        long minutes = Duration.between(startAt, endAt).toMinutes();
        return new LogEntity(
                null,          // logId（採番）
                userId,
                subjectId,
                startAt,
                endAt,
                (int) minutes,
                memo,
                null,          // createdAt
                null           // updatedAt
        );
    }
}