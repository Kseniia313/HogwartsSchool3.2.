package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyServiceImpl facultyService;

    private Faculty faculty = new Faculty(1, "Math", "green");

    @Test
    void createFaculty_shouldCreateFacultyAndSaveFaculty() {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty result = facultyRepository.save(faculty);
        assertEquals(faculty, result);
    }

    @Test
    void getFacultyById_shouldGetFacultyByIdAndReturnFaculty() {

        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));

        Faculty result = facultyService.getFacultyById(faculty.getId());

        assertEquals(faculty, result);
    }

    @Test
    void getFacultyById_shouldThrowExceptionWhenFacultyIdIsNotFound() {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class, () ->
                facultyService.getFacultyById(faculty.getId()));

    }

    @Test
    void updateFaculty_shouldUpdateFacultyAndReturnFaculty() {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));

        Faculty result = facultyService.getFacultyById(faculty.getId());

        assertEquals(faculty, result);
    }

    @Test
    void updateFaculty_shouldThrowExceptionWhenFacultyIsNotFound() {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());
        assertThrows(FacultyNotFoundException.class,
                () -> facultyService.getFacultyById(faculty.getId()));
    }

    @Test
    void deleteFaculty_shouldRemoveFacultyAndReturnRemovedFaculty() {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));

        Faculty result = facultyService.deleteFaculty(faculty.getId());
        assertEquals(faculty, result);
    }

    @Test
    void removeFaculty_shouldThrowExceptionWhenFacultyNotFound() {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class,
                () -> facultyService.deleteFaculty(faculty.getId()));
    }


    @Test
    void findByColor_shouldGetFacultyByColorAndReturnCollectionOfFaculties() {
        List<Faculty> faculties = List.of((faculty),
                new Faculty(2, "History", "red"),
                new Faculty(3, "Chemistry", "green"));
        String color = "green";

        List<Faculty> facultyList = faculties.stream().filter(fc -> fc.getColor() == color)
                .collect(Collectors.toUnmodifiableList());

        when(facultyRepository.findAllByColor(color))
                .thenReturn(facultyList);


        Collection<Faculty> result = facultyService.findByColor(color);

        assertEquals(facultyList, result);

    }

}


