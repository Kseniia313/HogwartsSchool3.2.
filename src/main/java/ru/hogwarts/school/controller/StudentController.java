package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student readStudent(@PathVariable long id) {
        return studentService.readStudentById(id);
    }

    @PutMapping()
    public Student updateStudent
            (@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable long id) {
        return studentService.removeStudent(id);
    }

    @GetMapping("/byAge")
    public Collection<Student> findStudentsByAge
            (@RequestParam int age) {
        return studentService.readByAge(age);
    }

    @GetMapping("/ageBetween")
    public Collection<Student> findByAgeBetween(@RequestParam int min,
                                                @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/getFacultyByStudentId")
    public Faculty getFacultyByIdStudent(@RequestParam long studentId) {
        return studentService.getFacultyByStudentId(studentId);
    }

    @GetMapping("/getStudentsByFacultyId")
    Collection<Student> findAllByFacultyId(@RequestParam long facultyId) {
        return studentService.getStudentsByFacultyId(facultyId);
    }

    @GetMapping("/getCountAllStudents")
    Integer getCountAllStudents() {
        return studentService.getCountAllStudents();
    }

    @GetMapping("/getAverageAgeOfStudents")
    Double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/getLastFiveStudents")
    Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/studentsWithLetter")
    Collection<Student> getStudentsStartsWithM() {
        return studentService.getAllStudentsNameStartsWithM();
    }

    @GetMapping("/AverageAge")
    Double AverageAge() {
        return studentService.averageAfeOfStudents();
    }

    @GetMapping("/print-parallel")
    public void getNames() {
        studentService.getStudentNames();
    }

    @GetMapping("/print-synchronized")
    public void getNamesSync() throws InterruptedException {
        studentService.getStudentNamesSync();
    }

    @GetMapping("/print-synchronized-wait")
    public void getNamesSyncWaitNotify() throws InterruptedException {
        studentService.getStudentNamesSyncWaitNotify();
    }
}

