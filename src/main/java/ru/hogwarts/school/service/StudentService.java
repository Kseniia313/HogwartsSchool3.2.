package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student readStudentById(long id);

    Student updateStudent(Student student);

    Student removeStudent(long id);

    Collection<Student> readByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Faculty getFacultyByStudentId(long studentId);

    Collection<Student> getStudentsByFacultyId(long facultyId);

    Integer getCountAllStudents();


    Double getAverageAgeOfStudents();

    Collection<Student> getLastFiveStudents();

    Collection<Student> getAllStudentsNameStartsWithM();

    Double averageAfeOfStudents();

    void getStudentNames();
    void getStudentNamesSync() ;

    void getStudentNamesSyncWaitNotify();
}
