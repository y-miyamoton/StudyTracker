package com.github.y_miyamoton.studytracker.service;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.entity.SubjectEntity;
import com.github.y_miyamoton.studytracker.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final AppAuditService appAuditService;
    private final UserContext userContext;

    public List<SubjectEntity> findActive(){
        return subjectRepository.selectAllActive(userContext.currentUserId());
    }

    public Optional<SubjectEntity> findById(long subjectId) {
        return subjectRepository.selectById(subjectId, userContext.currentUserId()); // 後で確認
    }

    @Transactional
    public void create(SubjectEntity newEntity) {
        subjectRepository.insert(newEntity);
        appAuditService.log(userContext.currentUserId(), "CREATE","SUBJECT", newEntity.subjectId(), "name=" + newEntity.name());
    }

    @Transactional
    public void update(SubjectEntity entity) {
        subjectRepository.update(entity);
        appAuditService.log(userContext.currentUserId(), "UPDATE","SUBJECT", entity.subjectId(), "name=" + entity.name());
    }

    @Transactional
    public void archive(long subjectId) {
        subjectRepository.archive(subjectId, userContext.currentUserId());
        appAuditService.log(userContext.currentUserId(), "DELETE","SUBJECT", subjectId, null);
    }
}
