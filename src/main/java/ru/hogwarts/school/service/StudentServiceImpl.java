package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;


@Service

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student readStudentById(long id) {

        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Студент не найден"));
    }

    @Override
    public Student updateStudent(Student student) {

        readStudentById(student.getId());

        return studentRepository.save(student);
    }

    @Override
    public Student removeStudent(long id) {
        Student student = readStudentById(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Collection<Student> readByAge(int age) {
        return studentRepository.findAllByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyByStudentId(long studentId) {
        return readStudentById(studentId).getFaculty();
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(long facultyId) {
        return studentRepository.findAllByFaculty_Id(facultyId);
    }
}
