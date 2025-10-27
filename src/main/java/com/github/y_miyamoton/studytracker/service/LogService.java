package com.github.y_miyamoton.studytracker.service;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.entity.LogEntity;
import com.github.y_miyamoton.studytracker.entity.SubjectMinuteEntity;
import com.github.y_miyamoton.studytracker.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final AppAuditService appAuditService;
    private final UserContext userContext;

    public List<LogEntity> findByPeriod(LocalDateTime from, LocalDateTime to, Long subjectId) {
        return logRepository.selectByPeriod(userContext.currentUserId(), from, to, subjectId);
    }

    public Optional<LogEntity> findById(long logId) {
        return logRepository.selectById(logId, userContext.currentUserId());
    }

    @Transactional
    public void create(LogEntity newEntity) {
        logRepository.insert(newEntity);
        appAuditService.log(userContext.currentUserId(), "CREATE","LOG", newEntity.logId(), "subject=" + newEntity.subjectId() + ", minutes=" + newEntity.minutes());
    }

    @Transactional
    public void update(LogEntity entity) {
        logRepository.update(entity);
    }

    @Transactional
    public void delete(long logId) {
        logRepository.delete(logId, userContext.currentUserId());
        appAuditService.log(userContext.currentUserId(), "DELETE","LOG", logId, null);
    }

    public int totalMinutes(LocalDateTime from, LocalDateTime to) {
        return logRepository.sumMinutesByPeriod(userContext.currentUserId(), from, to);
    }

    public List<SubjectMinuteEntity> minutesBySubject(LocalDateTime from, LocalDateTime to) {
        return logRepository.sumBySubject(userContext.currentUserId(), from, to);
    }
}
