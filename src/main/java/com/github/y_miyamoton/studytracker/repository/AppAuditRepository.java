package com.github.y_miyamoton.studytracker.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppAuditRepository {
    int insert(@Param("userId") Long userId,
               @Param("action") String action,
               @Param("entity") String entity,
               @Param("entityId") Long entityId,
               @Param("detail") String detail);

    List<AppAuditRepository> recent(@Param("userId") Long userId, @Param("limit") int limit);
}
