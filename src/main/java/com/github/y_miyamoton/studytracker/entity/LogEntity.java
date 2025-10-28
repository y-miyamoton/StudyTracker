package com.github.y_miyamoton.studytracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LogEntity{
        private Long logId;
        private Long userId;
        private Long subjectId;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
        private Integer minutes;
        private String memo;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
}
