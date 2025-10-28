package com.github.y_miyamoton.studytracker.service;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.entity.TimerEntity;
import com.github.y_miyamoton.studytracker.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;
    private final AppAuditService appAuditService;
    private final UserContext userContext;

    @Transactional
    public void startFocus(TimerEntity entity) {
        var active = timerRepository.findActive(userContext.currentUserId());
        if (active != null) {
            throw new IllegalStateException("すでにタイマーが起動中です。停止してから開始してください。");
        }
        timerRepository.startFocus(entity);
        appAuditService.log(userContext.currentUserId(), "TIMER_START","TIMER", entity.getSubjectId(), "focus=" + entity.getFocusMinutes());
    }

    @Transactional
    public void stopFocus() {
        var active = timerRepository.findActive(userContext.currentUserId());
        if (active == null) {
            throw new IllegalStateException("起動中のタイマーがありません。");
        }
        var now = java.time.LocalDateTime.now();
        var actualMinutes = (int) Duration.between(active.getStartAt(), now).toMinutes();
        timerRepository.stopActive(userContext.currentUserId());
        appAuditService.log(userContext.currentUserId(), "TIMER_STOP","TIMER", active.getSubjectId(), "actualMinutes=" + actualMinutes);
    }

    public TimerEntity currentActive() {
        return timerRepository.findActive(userContext.currentUserId());
    }

}