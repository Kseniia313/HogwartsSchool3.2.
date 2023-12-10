package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

import static org.hibernate.criterion.Projections.id;


@Service

public class StudentServiceImpl implements StudentService {
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student readStudentById(long id) {
        if (!studentRepository.existsById(id))
            logger.error("Студент c id {} не найден", id);
        return studentRepository.findById(id)
                .orElseThrow(() ->
                new StudentNotFoundException("Студент не найден"));
    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student c id {}",student.getId());
        readStudentById(student.getId());
        return studentRepository.save(student);
    }

    @Override
    public Student removeStudent(long id) {
        Student student = readStudentById(id);
        logger.info("Was invoked method for remove student c id {}", +id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Collection<Student> readByAge(int age) {
        logger.info("Was invoked method for searching student by age: {}", age);
        return studentRepository.findAllByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for searching student between min {} and max {}",min , max );
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyByStudentId(long studentId)
    {
        logger.info("Was invoked method for getting faculty by student id {}",studentId);
        return readStudentById(studentId).getFaculty();
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(long facultyId) {
        logger.info("Was invoked method for getting all students by faculty id {}", facultyId);
        return studentRepository.findAllByFaculty_Id(facultyId);
    }

    @Override
    public Integer getCountAllStudents() {
        logger.info("Was invoked method for counting all students");
        return studentRepository.getCountAllStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method for counting average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for getting only last five students");
        return studentRepository.getLastFiveStudents();
    }
}
