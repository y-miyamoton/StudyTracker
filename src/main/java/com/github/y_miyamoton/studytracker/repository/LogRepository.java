package com.github.y_miyamoton.studytracker.repository;

import com.github.y_miyamoton.studytracker.entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LogRepository {
    List<LogEntity> findByPeriod(@Param("userId") Long userId,
                                 @Param("from")LocalDateTime from,
                                 @Param("to") LocalDateTime to,
                                 @Param("subjectId") Long subjectId
    );
}
