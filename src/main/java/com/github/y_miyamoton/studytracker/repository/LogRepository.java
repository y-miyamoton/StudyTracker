package com.github.y_miyamoton.studytracker.repository;

import com.github.y_miyamoton.studytracker.entity.LogEntity;
import com.github.y_miyamoton.studytracker.entity.SubjectMinuteEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface LogRepository {

    List<LogEntity> selectByPeriod(
            @Param("userId") Long userId,
            @Param("from")LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("subjectId") Long subjectId
    );
    Optional<LogEntity> selectById(@Param("logId") Long logId, @Param("userId") Long userId);
    void insert(@Param("log") LogEntity newEntity);
    void update(@Param("log") LogEntity entity);
    void delete(@Param("logId") Long logId, @Param("userId") Long userId);
    Integer sumMinutesByPeriod(@Param("userId") Long userId,
                               @Param("from") LocalDateTime from,
                               @Param("to") LocalDateTime to);

    List<SubjectMinuteEntity> sumBySubject(@Param("userId") Long userId,
                                           @Param("from") LocalDateTime from,
                                           @Param("to") LocalDateTime to);
}
