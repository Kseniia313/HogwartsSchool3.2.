package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service

public class StudentServiceImpl implements StudentService {
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private boolean marker = false;

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
        logger.info("Was invoked method for update student c id {}", student.getId());
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
        logger.info("Was invoked method for searching student between min {} and max {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyByStudentId(long studentId) {
        logger.info("Was invoked method for getting faculty by student id {}", studentId);
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

    @Override
    public Collection<Student> getAllStudentsNameStartsWithM() {
        logger.info("Was invoked method for getting all names of all students whose name starts with M");
        return studentRepository.findAll().stream().sorted(Comparator.comparing(Student::getName)).
                filter(student -> student.getName().startsWith("M"))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Double averageAfeOfStudents() {
        logger.info("Was invoked method for getting the average age of students");
        return studentRepository.findAll().stream().
                mapToDouble(Student::getAge).average().getAsDouble();
    }

    @Override
    public void getStudentNames() {
        printNames(1);
        printNames(2);

        new Thread(() -> {
            printNames(3);
            printNames(4);
        }).start();

        new Thread(() -> {
            printNames(5);
            printNames(6);
        }).start();

    }

    @Override
    public void getStudentNamesSync() {
        printNamesSync(1);
        printNamesSync(2);

        new Thread(() -> {
            printNamesSync(3);
            printNamesSync(4);
        }).start();

        new Thread(() -> {
            printNamesSync(5);
            printNamesSync(6);
        }).start();
    }

    @Override
    public void getStudentNamesSyncWaitNotify() {
        new Thread(() -> {
            printStudentNamesSyncWaitNotify1(1L, 2l);
        }).start();
        new Thread(() -> {
            printStudentNamesSyncWaitNotify2(3L, 4L);
        }).start();
    }


    private void printNames(long id) {
        String studentName = readStudentById(id).getName();
        System.out.println(studentName + " id= " + id);
    }

    private synchronized void printNamesSync(long id) {
        String studentName = readStudentById(id).getName();
        System.out.println(studentName + " id= " + id);
    }


    private synchronized void printStudentNamesSyncWaitNotify1(long id1, long id2) {
        while (marker)
            try {
                wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

        String studentName = readStudentById(id1).getName();
        String studentName1 = readStudentById(id2).getName();
        System.out.println(studentName + " id = " + id1 + studentName1 + " id= " + id2);

        marker = true;

        notify();
    }

    private synchronized void printStudentNamesSyncWaitNotify2(long id1, long id2) {
        while (!marker)
            try {
                wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

        String studentName = readStudentById(id1).getName();
        String studentName1 = readStudentById(id2).getName();
        System.out.println(studentName + " id = " + id1 + studentName1 + " id = " + id2);

    }
}

