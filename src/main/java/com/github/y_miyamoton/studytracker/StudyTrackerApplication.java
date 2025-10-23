package com.github.y_miyamoton.studytracker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.y_miyamoton.studytracker.repository")
public class StudyTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyTrackerApplication.class, args);
	}

}
