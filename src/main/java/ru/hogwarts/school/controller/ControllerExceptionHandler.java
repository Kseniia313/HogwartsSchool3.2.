package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.exception.*;
import ru.hogwarts.school.service.StudentServiceImpl;

@ControllerAdvice
public class ControllerExceptionHandler {
    private long id;
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @ExceptionHandler({StudentAlreadyExistException.class, FacultyAlreadyExistException.class})
    public ResponseEntity<String> handleException(RuntimeException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({StudentNotFoundException.class, FacultyNotFoundException.class, AvatarNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());

    }
}
