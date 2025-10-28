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
        long secs = Duration.between(startAt, endAt).getSeconds();
        int minutes = (int) Math.max(1, Math.ceil(secs / 60.0));
        LogEntity logEntity = new LogEntity();
        logEntity.setUserId(userId);
        logEntity.setSubjectId(subjectId);
        logEntity.setStartAt(startAt);
        logEntity.setEndAt(endAt);
        logEntity.setMinutes(minutes);
        logEntity.setMemo(memo);
        return logEntity;
    }
}