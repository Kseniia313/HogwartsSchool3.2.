package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FacultyServiceImpl implements FacultyService {

    private Map<Long, Faculty> facultiesRepository = new HashMap<>();
    private Long id = 0l;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        if (facultiesRepository.containsValue(faculty)) {
            throw new FacultyAlreadyExistException("Факультет " +faculty+"  уже существует");
        }
        long facultyId = ++id;
        faculty.setId(id);
        facultiesRepository.put(facultyId, faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyById(long facultyId) {
        Faculty faculty = facultiesRepository.get(facultyId);

        if (faculty == null) {
            throw new FacultyNotFoundException("Факультета с id " + facultyId + " не существует ");
        }

        return faculty;
    }

    @Override
    public Faculty upDateFaculty(Faculty faculty) {
        if (!facultiesRepository.containsKey(faculty.getId())) {
            throw new FacultyNotFoundException("Факультет с id " + faculty.getId() + " не найден ");
        }
        facultiesRepository.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(long facultyId) {
        Faculty faculty = facultiesRepository.remove(facultyId);
        if (faculty == null) {
            throw new FacultyNotFoundException("Факультет с id " + facultyId + " не найден ");
        }
        return faculty;
    }
@Override
    public Collection<Faculty> findByColor(String color) {
    return facultiesRepository.values().stream()
            .filter(faculty -> Objects.equals(color, faculty.getColor()))
            .collect(Collectors.toUnmodifiableList());
    }
}
