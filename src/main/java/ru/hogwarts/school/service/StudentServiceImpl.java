package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
private final Map<Long, Student> repository = new HashMap<>();
    private Long idCounter = 0L;


    @Override
    public Student createStudent(Student student) {
              if (repository.containsValue(student)) {
            throw new StudentAlreadyExistException("Cтудент " +student+ " уже существует");
        }
              long id = ++idCounter;
              student.setId(id);
        repository.put(id, student);

        return student;
    }

    @Override
    public Student readStudentById(long id) {
        Student student = repository.get(id);

        if (student== null) {
            throw new StudentNotFoundException("Студент c id " + id + " не найден");
        }
        return student;
    }

    @Override
    public Student updateStudent(Student student) {

        if (!repository.containsKey(student.getId())) {
            throw new StudentNotFoundException("Студент c id " +student.getId()+" не найден");
        }
      repository.put(student.getId(), student);
        return student;
    }

    @Override
    public Student removeStudent(long id) {
        Student student = repository.remove(id);

        if (student== null) {
                throw new StudentNotFoundException("Студент c id " + id + " не найден");
        }
        return student;
    }

    @Override
    public Collection<Student> readByAge(int age) {
     return   repository.values().stream()
                .filter(student -> student.getAge() ==age)
                .collect(Collectors.toUnmodifiableList());
    }

    }
