package ru.hogwarts.school.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String massage) {
        super(massage);
    }
}
