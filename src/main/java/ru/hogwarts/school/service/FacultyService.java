package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private Map<Long, Faculty> faculties = new HashMap<>();
    private Long generateFacultyId = 1l;

    public Faculty createFaculty(Faculty faculty) {
        faculties.put(generateFacultyId, faculty);
        generateFacultyId++;
        return faculty;
    }

    public Faculty getFacultyById(Long facultyId) {
        return faculties.get(facultyId);
    }

    public Faculty upDateFaculty(Long facultyId, Faculty faculty) {
        faculties.put(generateFacultyId, faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long facultyId) {
        return faculties.remove(facultyId);
    }



}
