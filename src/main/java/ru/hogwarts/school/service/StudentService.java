package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
private Map<Long, Student> students = new HashMap<>();
private Long generateStudentId = 1L;


    public Student createStudent(Student student) {
        students.put(generateStudentId, student);
        generateStudentId++;
        return student;
    }

    public Student getStudentById(Long studentId) {
        return students.get(studentId);
    }

    public Student updateStudent(Long studentId, Student student) {
        students.put(studentId, student);
        return student;
    }

    public Student removeStudent(Long studentId) {
        return students.remove(studentId);
    }
    }
