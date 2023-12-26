package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyById(long facultyId);

    Faculty upDateFaculty(Faculty faculty);

    Faculty deleteFaculty(long facultyId);

    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findByNameOrColor(String name, String color);

    ResponseEntity<String> getFacultyNameWithMaxLength();
}
