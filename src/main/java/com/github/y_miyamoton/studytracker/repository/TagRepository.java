package com.github.y_miyamoton.studytracker.repository;

import com.github.y_miyamoton.studytracker.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TagRepository {
    List<TagEntity> selectAll(@Param("userId") Long userId);
    Optional<TagEntity> selectById(@Param("tagId") Long tagId, @Param("userId") Long userId);
    void insert(@Param("tag") TagEntity newEntity);
    void update(@Param("tag") TagEntity entity);
    void delete(@Param("tagId") long tagId, @Param("userId") long userId);
}
