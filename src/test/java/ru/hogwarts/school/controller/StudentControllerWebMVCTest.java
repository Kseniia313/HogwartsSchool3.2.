package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
public class StudentControllerWebMVCTest {

@MockBean
    StudentRepository studentRepository;
@SpyBean
    StudentServiceImpl studentService;

@Autowired
    private MockMvc mockMvc;

@Autowired
    ObjectMapper objectMapper;
    private Student student = new Student(1L, "Olga", 16);
    private Faculty faculty = new Faculty(1L, "math", "red");
    @Test
    void createStudent_shouldReturnStudentAndStatus200() throws Exception {
        when(studentRepository.save(student))
                .thenReturn(student);

        mockMvc.perform(post("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void readStudent_shouldReturnStudentAndStatus200() throws Exception {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.id").value(student.getId())));
    }
    @Test
    void readStudent_shouldReturnStatus404() throws Exception {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Студент не найден"));
    }

    @Test
    void updateStudent_shouldReturnStudentAndStatus200() throws Exception {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));
        when(studentRepository.save(student))
                .thenReturn(student);

        mockMvc.perform(put("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()));
    }

    @Test
    void deleteStudent_shouldReturnStudentAndStatus200() throws Exception {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));
        mockMvc.perform(delete("/student/"+student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()));
    }

    @Test
    void findStudentsByAge_shouldReturnCollectionOfStudentsAndStatus200() throws Exception {
        Student student1 = new Student(2L, "Kira", 16);
        int age = student.getAge();

        when(studentRepository.findAllByAge(age))
                .thenReturn(List.of(student, student1));

        mockMvc.perform(get("/student/byAge?age=" + student.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].age").value(student.getAge()))
                .andExpect(jsonPath("$.[1].age").value(student1.getAge()));
    }

        @Test
    void findByAgeBetween_shouldReturnCollectionOfStudentsAndStatus200() throws Exception {

             Student student1 = new Student(2L, "Olga", 20);
               Student student2 = new Student(3L, "Kira", 17);
        Student student3 = new Student(4L, "Arina", 18);

        int min = student.getAge();
        int max = student1.getAge();

        when(studentRepository.findByAgeBetween(min, max))
                .thenReturn(List.of(student2,student3));

        mockMvc.perform(get("/student/ageBetween?min=" +min+ "&max="+max))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]age").value(student2.getAge()))
                .andExpect(jsonPath("$.[1]age").value(student3.getAge()));
    }
    @Test
    void getFacultyByStudentId_shouldReturnStudentAndStatus200() throws Exception {
        student.setFaculty(faculty);

        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));

       mockMvc.perform(get("/student/getFacultyByStudentId?studentId="+student.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(student.getId()));
    }


    @Test
    void getStudentsByFacultyId_shouldReturnCollectionStudentsAndStatus200() throws Exception {
        Student student1 = new Student(1L, "Kira", 17);

        student1.setFaculty(faculty);
        student.setFaculty(faculty);

        List<Student> students = List.of(student, student1);

        when(studentRepository.findAllByFaculty_Id(faculty.getId()))
                .thenReturn(students);

        mockMvc.perform(get("/student/getStudentsByFacultyId?facultyId="+faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]id").value(faculty.getId()))
                .andExpect(jsonPath("$.[1]id").value(faculty.getId()));

    }
}
