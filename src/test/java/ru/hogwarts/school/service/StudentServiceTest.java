package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;
    ;

    private Student student = new Student(1, "Anna", 20);

    @Test
    void createStudent_shouldCreateAndSaveStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.createStudent(student);

        assertEquals(student, result);
    }

    @Test
    void readStudentById_shouldFindStudentByIdAndReturnStudent() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));

        Student result = studentService.readStudentById(student.getId());

        assertEquals(student, result);
    }

    @Test
    void readStudentById_shouldThrowExceptionWhenStudentIdIsNotFound() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.readStudentById(student.getId()));
    }

    @Test
    void updateStudent_shouldUpDateStudentAndReturnStudent() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));

        Student result = studentService.readStudentById(student.getId());

        assertEquals(student, result);
    }

    @Test
    void updateStudent_shouldThrowExceptionWhenStudentIsNotFound() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class,
                () -> studentService.readStudentById(student.getId()));
    }

    @Test
    void removeStudent_shouldRemoveStudentAndReturnRemovedStudent() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));
        Student result = studentService.removeStudent(student.getId());
        assertEquals(student, result);
    }

    @Test
    void removeStudent_shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.removeStudent(student.getId()));
    }

    @Test
    void readByAge_shouldReadStudentsByAgeAndReturnCollectionOfStudent() {
        List<Student> students = List.of((student),
                new Student(2, "Klim", 15),
                new Student(3, "Kira", 20));
        int age = 15;

        when(studentRepository.findAllByAge(age))
                .thenReturn(students);

        List<Student> studentList = students.stream().filter(st -> st.getAge() == age)
                .collect(Collectors.toUnmodifiableList());

        Collection<Student> result = studentService.readByAge(age)
                .stream().filter(st -> st.getAge() == age).collect(Collectors.toUnmodifiableList());

        assertEquals(studentList, result);

    }
}