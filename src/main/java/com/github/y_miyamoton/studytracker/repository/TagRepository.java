package com.github.y_miyamoton.studytracker.repository;

import com.github.y_miyamoton.studytracker.entity.TagEntity;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagRepository {
    List<TagEntity> findAll(@Param("userId") Long userId);
    TagEntity findById(@Param("id") Long id, @Param("userId") Long userId);
}
