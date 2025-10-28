package com.github.y_miyamoton.studytracker.service;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.controller.subject.SubjectNotFoundException;
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
        return subjectRepository.selectById(subjectId, userContext.currentUserId());
    }

    @Transactional
    public void create(SubjectEntity newEntity) {
        subjectRepository.insert(newEntity);
        appAuditService.log(userContext.currentUserId(), "CREATE","SUBJECT", newEntity.getSubjectId(), "name=" + newEntity.getName());
    }

    @Transactional
    public void update(SubjectEntity entity) {
        int rows = subjectRepository.update(entity);
        if (rows == 0) {
            throw new SubjectNotFoundException();
        }
        appAuditService.log(userContext.currentUserId(), "UPDATE","SUBJECT", entity.getSubjectId(), "name=" + entity.getName());
    }

    @Transactional
    public void archive(long subjectId) {
        int rows = subjectRepository.archive(subjectId, userContext.currentUserId());
        if (rows == 0) {
            throw new SubjectNotFoundException();
        }
        appAuditService.log(userContext.currentUserId(), "ARCHIVE","SUBJECT", subjectId, null);
    }
}
