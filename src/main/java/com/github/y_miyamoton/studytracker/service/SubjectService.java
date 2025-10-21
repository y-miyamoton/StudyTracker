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
    private final UserContext userContext;

    public List<SubjectEntity> findActive(){
        return subjectRepository.selectAllActive(userContext.currentUserId());
    }

    public Optional<SubjectEntity> findById(long id) {
        return subjectRepository.selectById(id, userContext.currentUserId()); // 後で確認
    }

    @Transactional
    public void create(SubjectEntity newEntity) {
        subjectRepository.insert(newEntity);
    }

    @Transactional
    public void update(SubjectEntity entity) {
        subjectRepository.update(entity);
    }

    @Transactional
    public void archive(long id) {
        subjectRepository.archive(id, userContext.currentUserId());
    }
}
