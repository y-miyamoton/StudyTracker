package com.github.y_miyamoton.studytracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TimerEntity{
    private Long timerId;
    private Long userId;
    private Long subjectId;
    private Integer focusMinutes;
    private Integer breakMinutes;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
