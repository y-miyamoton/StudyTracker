package com.github.y_miyamoton.studytracker.service;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.entity.TimerEntity;
import com.github.y_miyamoton.studytracker.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;
    private final UserContext userContext;

    public Long startFocus(TimerEntity entity) {
        var active = timerRepository.findActive(userContext.currentUserId());
        if (active != null) {
            throw new IllegalStateException("すでにタイマーが起動中です。停止してから開始してください。");
        }
        timerRepository.startFocus(entity);
        return entity.getTimerId();
    }

    public void stopFocus() {
        var active = timerRepository.findActive(userContext.currentUserId());
        if (active == null) {
            throw new IllegalStateException("起動中のタイマーがありません。");
        }
        timerRepository.stopFocus(active.getTimerId(), userContext.currentUserId());
    }

    public void startBreak() {
        var active = timerRepository.findActive(userContext.currentUserId());
        if (active == null) {
            throw new IllegalStateException("起動中のタイマーがありません。");
        }
    }

    public void stopBreak() {
        var active = timerRepository.findActive(userContext.currentUserId());
        if (active == null) {
            throw new IllegalStateException("起動中のタイマーがありません。");
        }
    }

    public TimerEntity currentActive() {
        return timerRepository.findActive(userContext.currentUserId());
    }

}