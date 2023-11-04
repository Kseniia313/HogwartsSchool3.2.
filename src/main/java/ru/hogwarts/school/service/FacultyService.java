package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyById(long facultyId);

    Faculty upDateFaculty(Faculty faculty);

    Faculty deleteFaculty(long facultyId);

    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findByNameOrColor(String name, String color);

}
