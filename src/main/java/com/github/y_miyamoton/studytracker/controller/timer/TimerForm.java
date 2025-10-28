package com.github.y_miyamoton.studytracker.controller.timer;

import com.github.y_miyamoton.studytracker.entity.TimerEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TimerForm(
        @NotNull Long subjectId,

        @Min(value = 1, message = "フォーカスは1分以上にしてください")
        @Max(value = 180, message = "フォーカスは180分以内にしてください")
        Integer focusMinutes,

        @Min(value = 1, message = "休憩は1分以上にしてください")
        @Max(value = 60, message = "休憩は60分以内にしてください")
        Integer breakMinutes
) {
    public TimerForm {
        if (focusMinutes == null || focusMinutes < 1) focusMinutes = 25;
        if (breakMinutes == null || breakMinutes < 1) breakMinutes = 5;
    }

    public TimerEntity toEntity(Long userId) {
        return new TimerEntity(
                null,
                userId,
                subjectId(),
                focusMinutes(),
                breakMinutes(),
                null,
                null
        );
    }
}
