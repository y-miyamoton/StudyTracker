package com.github.y_miyamoton.studytracker.service;

import com.github.y_miyamoton.studytracker.repository.AppAuditRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppAuditService {

    private final AppAuditRepository appAuditRepository;
    private static final Logger log  = LoggerFactory.getLogger(AppAuditService.class);

    public void log(Long userId, String action, String entity, Long entityId, String detail) {
        log.info("[AUDIT] {} on {} (ID={}) userId={} detail={}", action, entity, entityId, userId, detail);
        appAuditRepository.insert(userId, action, entity, entityId, detail);
    }
}
