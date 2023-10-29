package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
@Mock
private FacultyService facultyService;
@InjectMocks
private FacultyRepository facultyRepository;

    private Faculty faculty = new Faculty(1, "Hogwarts", "green");

    @Test
    void createFaculty_shouldCreateFacultyAndSaveFaculty() {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty result = facultyRepository.save(faculty);
        assertEquals(faculty, result);
    }

//    @Test
//    void createFaculty_shouldThrowExceptionWhenFacultyIsAlreadyExists() {
//        underTest.createFaculty(faculty);
//        FacultyAlreadyExistException facultyAlreadyExistException = assertThrows(FacultyAlreadyExistException.class,
//                () -> underTest.createFaculty(faculty));
//    }
//
//    @Test
//    void getFacultyById_shouldGetFacultyByIdAndReturnFaculty() {
//        Faculty result = underTest.createFaculty(faculty);
//        Faculty expectedFaculty = underTest.getFacultyById(1);
//        assertEquals(expectedFaculty, result);
//    }
//
//    @Test
//    void getFacultyById_shouldThrowExceptionWhenFacultyIsNotFound() {
//        Faculty faculty1 = new Faculty(2, "Slizerin", "red");
//        underTest.createFaculty(faculty);
//        FacultyNotFoundException facultyNotFoundException = assertThrows(FacultyNotFoundException.class,
//                () -> underTest.getFacultyById(2));
//    }
//
//    @Test
//    void updateFaculty_shouldChangeFacultyAndReturnFaculty() {
//
//        underTest.createFaculty(faculty);
//        faculty.setColor("red");
//        Faculty result = underTest.upDateFaculty(faculty);
//
//        assertEquals(faculty, result);
//    }
//
//    @Test
//    void updateFaculty_shouldThrowExceptionWhenFacultyIsNotFound() {
//        Faculty faculty1 = new Faculty(2, "Slizerin", "red");
//        underTest.createFaculty(faculty);
//        FacultyNotFoundException facultyNotFoundException = assertThrows(FacultyNotFoundException.class,
//                () -> underTest.upDateFaculty(faculty1));
//    }
//
//    @Test
//    void deleteFaculty_shouldRemoveFacultyAndReturnRemovedFaculty() {
//        underTest.createFaculty(faculty);
//        Faculty result = underTest.deleteFaculty(1);
//        assertEquals(faculty, result);
//    }
//
//    @Test
//    void removeFaculty_shouldThrowExceptionWhenFacultyNotFound() {
//        underTest.createFaculty(faculty);
//        underTest.deleteFaculty(1);
//        FacultyNotFoundException facultyNotFoundException = assertThrows(FacultyNotFoundException.class,
//                () -> underTest.deleteFaculty(2));
//    }
}

//    @Test
//    void findByColor_shouldGetFacultyByColorAndReturnUnmodifiableList() {
//        String color = "red";
//        Faculty faculty1 = new Faculty(2, "Slizerin", "red");
//        List<Faculty> faculties =List.of(faculty, faculty1);
//        faculties.;


