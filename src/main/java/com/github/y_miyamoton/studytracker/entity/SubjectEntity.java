package com.github.y_miyamoton.studytracker.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubjectEntity {
    private Long subjectId;
    private Long userId;
    private String name;
    private String colorCode;
    private String description;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
