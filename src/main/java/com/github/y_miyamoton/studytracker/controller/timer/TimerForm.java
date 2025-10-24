package com.github.y_miyamoton.studytracker.controller.timer;

import com.github.y_miyamoton.studytracker.entity.TimerEntity;
import jakarta.validation.constraints.NotNull;

public record TimerForm(
        Long timerId,
        @NotNull Long subjectId,
        Integer focusMinutes,
        Integer breakMinutes
) {
    public TimerForm {
        if (focusMinutes == null || focusMinutes < 1) focusMinutes = 25;
        if (breakMinutes == null || breakMinutes < 1) breakMinutes = 5;
    }

    public TimerEntity toEntity(Long userId) {
        return new TimerEntity(
                timerId(),
                userId,
                subjectId(),
                focusMinutes(),
                breakMinutes(),
                null,
                null
        );
    }
}
