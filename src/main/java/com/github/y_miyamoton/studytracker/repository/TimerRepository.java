package com.github.y_miyamoton.studytracker.repository;

import com.github.y_miyamoton.studytracker.entity.TimerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TimerRepository {
    void startFocus(@Param("timer") TimerEntity entity);
    void stopActive(@Param("userId") Long userId);
    TimerEntity findActive(@Param("userId") Long userId);
}
