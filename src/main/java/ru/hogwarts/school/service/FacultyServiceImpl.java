package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultiesRepository;

    public FacultyServiceImpl(FacultyRepository facultiesRepository) {
        this.facultiesRepository = facultiesRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultiesRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(long facultyId) {

        return facultiesRepository.findById(facultyId)
                .orElseThrow(() -> new FacultyNotFoundException("Факультета с id " + facultyId + " не существует "));

    }

    @Override
    public Faculty upDateFaculty(Faculty faculty) {
        getFacultyById(faculty.getId());

        return facultiesRepository.save(faculty);
    }

    @Override
    public Faculty deleteFaculty(long facultyId) {

       Faculty faculty = getFacultyById(facultyId);
        facultiesRepository.delete(faculty);
        return faculty;
    }
@Override
    public Collection<Faculty> findByColor(String color) {
    return facultiesRepository.findAllByColor(color);
    }
}
