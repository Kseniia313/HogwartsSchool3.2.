package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student readStudentById(long id);

    Student updateStudent(Student student);

    Student removeStudent(long id);

    Collection<Student> readByAge(int age);
}
