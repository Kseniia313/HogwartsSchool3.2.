package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;


@Service
public class FacultyServiceImpl implements FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultiesRepository;

    public FacultyServiceImpl(FacultyRepository facultiesRepository) {
        this.facultiesRepository = facultiesRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for creating faculty");
        return facultiesRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(long facultyId) {
if(!facultiesRepository.existsById(facultyId))
    logger.error("Факультет c id {} не найден", facultyId);
        return facultiesRepository.findById(facultyId)
                .orElseThrow(() -> new FacultyNotFoundException("Факультет не найден"));

    }

    @Override
    public Faculty upDateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty c id {}",faculty.getId());
        getFacultyById(faculty.getId());
        return facultiesRepository.save(faculty);
    }

    @Override
    public Faculty deleteFaculty(long facultyId) {
        logger.info("Was invoked method for remove faculty c id {}",facultyId);
        Faculty faculty = getFacultyById(facultyId);
        facultiesRepository.delete(faculty);
        return faculty;
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for finding faculty by color {}",color);
        return facultiesRepository.findAllByColor(color);
    }

    @Override
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.info("Was invoked method for finding faculties by name {} or color {} ",name,color);
        return facultiesRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

}
