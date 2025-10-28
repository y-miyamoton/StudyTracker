package com.github.y_miyamoton.studytracker.controller.log;

import com.github.y_miyamoton.studytracker.entity.LogEntity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;

public record LogForm(
        Long logId,

        @NotNull(message = "科目は必須です")
        Long subjectId,

        @NotNull(message = "開始時刻は必須です")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime startAt,

        @NotNull(message = "終了時刻は必須です")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime endAt,

        String memo
) {

    @AssertTrue(message = "終了は開始より後にしてください")
    public boolean isTimeRangeValid() {
        return startAt != null && endAt != null && endAt.isAfter(startAt);
    }

    public LogEntity toEntity(Long userId) {
        long minutes = Duration.between(startAt, endAt).toMinutes();
        var logEntity = new LogEntity();
        logEntity.setUserId(userId);
        logEntity.setSubjectId(subjectId());
        logEntity.setStartAt(startAt());
        logEntity.setEndAt(endAt());
        logEntity.setMinutes((int) minutes);
        logEntity.setMemo(memo());
        return logEntity;
    }

    public LogEntity toEntity(Long logId, Long userId) {
        long minutes = Duration.between(startAt, endAt).toMinutes();
        var logEntity = new LogEntity();
        logEntity.setLogId(logId);
        logEntity.setUserId(userId);
        logEntity.setSubjectId(subjectId());
        logEntity.setStartAt(startAt());
        logEntity.setEndAt(endAt());
        logEntity.setMinutes((int) minutes);
        logEntity.setMemo(memo());
        return logEntity;
    }

    public static LogForm fromEntity(LogEntity logEntity) {
        return new LogForm(
                logEntity.getLogId(),
                logEntity.getSubjectId(),
                logEntity.getStartAt(),
                logEntity.getEndAt(),
                logEntity.getMemo()
        );
    }
}