package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class StudentServiceImplTest {
    private StudentService underTest= new StudentServiceImpl();

    @BeforeEach
    void beforeEach() {
        underTest = new StudentServiceImpl();
    }

    private Student student = new Student("Anna", 1, 20);


    @Test
    void createStudent_shouldCreateStudentAndReturnStudent() {
        Student result = underTest.createStudent(student);

        assertEquals(student,result);
    }

    @Test
    void createStudent_shouldThrowExceptionWhenStudentIsAlreadyExists() {
        underTest.createStudent(student);
        StudentAlreadyExistException studentAlreadyExistException = assertThrows(StudentAlreadyExistException.class,
                () -> underTest.createStudent(student));
    }

    @Test
    void readStudentById_shouldGetStudentByIdAndReturnStudent() {

        Student result = underTest.createStudent(student);
        Student expectedStudent = underTest.readStudentById(1);
        assertEquals(expectedStudent,result);
    }

    @Test
    void readStudentById_shouldThrowExceptionWhenStudentIsNotFound() {
        Student student1 = new Student("Anna", 2, 20);
        underTest.createStudent(student);
        StudentNotFoundException studentNotFoundException = assertThrows(StudentNotFoundException.class,
                () -> underTest.readStudentById(2));
    }

    @Test
    void updateStudent_shouldChangeStudentAndReturnStudent() {

        underTest.createStudent(student);
        student.setAge(21);
        Student result = underTest.updateStudent(student);

        assertEquals(student,result);
    }

    @Test
    void updateStudent_shouldThrowExceptionWhenStudentIsNotFound() {
        Student student1 = new Student("Anna", 2, 20);
        underTest.createStudent(student);
        StudentNotFoundException studentNotFoundException=assertThrows(StudentNotFoundException.class,
                ()-> underTest.updateStudent(student1));
    }

    @Test
    void removeStudent_shouldRemoveStudentAndReturnRemovedStudent() {
        underTest.createStudent(student);
        Student result = underTest.removeStudent(1);
        assertEquals(student, result);
    }

    @Test
    void removeStudent_shouldThrowExceptionWhenStudentNotFound() {
        underTest.createStudent(student);
        underTest.removeStudent(1);
        StudentNotFoundException studentNotFoundException = assertThrows(StudentNotFoundException.class,
                () -> underTest.removeStudent(2));
    }

    @Test
    void readByAge_shouldGetStudentsByAgeAndReturnUnmodifiableList() {
     int age = 25;
        Student student1 = new Student("Ekaterina", 1,25);
        List<Student> students = List.of(student, student1);
        students.stream().filter(student2 -> student2.getAge() == age);

    Student result = (Student) underTest.readByAge(age);

        assertEquals(students,result);











    }
}