package com.github.y_miyamoton.studytracker.repository;

import com.github.y_miyamoton.studytracker.entity.SubjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SubjectRepository {

    List<SubjectEntity> selectAllActive(@Param("userId") Long userId);
    Optional<SubjectEntity> selectById(@Param("id") Long id, @Param("userId") Long userId);
    void insert(@Param("subjects") SubjectEntity newEntity);
    void update(@Param("subjects") SubjectEntity entity);
}
