package com.github.y_miyamoton.studytracker.repository;

import com.github.y_miyamoton.studytracker.entity.AppAuditEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppAuditRepository {
    int insert(@Param("userId") Long userId,
               @Param("action") String action,
               @Param("entity") String entity,
               @Param("entityId") Long entityId,
               @Param("detail") String detail);

    List<AppAuditEntity> recent(@Param("userId") Long userId, @Param("limit") int limit);
}
