package com.github.y_miyamoton.studytracker.service;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.entity.TagEntity;
import com.github.y_miyamoton.studytracker.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final UserContext userContext;

    public List<TagEntity> findActive() {
        return  tagRepository.selectAll(userContext.currentUserId());
    }

    public Optional<TagEntity> findById(long tagId) {
        return tagRepository.selectById(tagId, userContext.currentUserId());
    }

    @Transactional
    public void create(TagEntity newEntity) {
        tagRepository.insert(newEntity);
    }

    @Transactional
    public void update(TagEntity entity) {
        tagRepository.update(entity);
    }

    @Transactional
    public void delete(long tagId) {
        tagRepository.delete(tagId,userContext.currentUserId());
    }
}
