package com.github.y_miyamoton.studytracker.controller.log;

import com.github.y_miyamoton.studytracker.entity.LogEntity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

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
        long minutes = java.time.Duration.between(startAt, endAt).toMinutes();
        return new LogEntity(
                logId(),
                userId,
                subjectId(),
                startAt(),
                endAt(),
                (int) minutes,
                memo(),
                null,
                null
        );
    }

    public LogEntity toEntity(Long logId, Long userId) {
        long minutes = java.time.Duration.between(startAt, endAt).toMinutes();
        return new LogEntity(
                logId,
                userId,
                subjectId(),
                startAt(),
                endAt(),
                (int) minutes,
                memo(),
                null,
                null
        );
    }

    public static LogForm fromEntity(LogEntity logEntity) {
        return new LogForm(
                logEntity.logId(),
                logEntity.subjectId(),
                logEntity.startAt(),
                logEntity.endAt(),
                logEntity.memo()
        );
    }
}