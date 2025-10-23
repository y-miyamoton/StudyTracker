package com.github.y_miyamoton.studytracker.controller.log;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LogNotFoundException extends RuntimeException{
}
