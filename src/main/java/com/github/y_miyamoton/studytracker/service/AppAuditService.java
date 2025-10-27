package com.github.y_miyamoton.studytracker.service;

import com.github.y_miyamoton.studytracker.repository.AppAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppAuditService {

    private final AppAuditRepository appAuditRepository;

    public void log(Long userId, String action, String entity, Long entityId, String detail) {
        appAuditRepository.insert(userId, action, entity, entityId, detail);
    }
}
