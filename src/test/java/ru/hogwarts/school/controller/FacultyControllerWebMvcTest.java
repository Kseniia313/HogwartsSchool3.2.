package ru.hogwarts.school.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @MockBean
    FacultyRepository facultyRepository;
    @SpyBean
    FacultyServiceImpl facultyService;

  @Autowired
    private MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

    Faculty faculty = new Faculty(1L, "math", "red");
    Faculty faculty1 = new Faculty(2L, "english", "green");

        @Test
    void createFaculty_shouldReturnFacultyAndStatus200() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);

            mockMvc.perform(post("/faculty")
                            .content(objectMapper.writeValueAsString(faculty))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(faculty.getId()))
                    .andExpect(jsonPath("$.name").value(faculty.getName()))
                    .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void getFaculty_shouldReturnFacultyAndReturnStatus200() throws Exception {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculty/"+faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }

    @Test
    void getFacultyById_shouldReturnStatus404() throws Exception {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/faculty/"+faculty.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Факультет не найден"));
    }

    @Test
    void upDateFaculty_shouldReturnFacultyAndStatus200() throws Exception {
        Faculty faculty1 = new Faculty(1L, "Math", "orange");

        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty1));

        when(facultyRepository.save(faculty1)).thenReturn(faculty1);

        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty1.getId()))
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));
    }

    @Test
    void deleteFaculty_shouldReturnFacultyAndStatus200() throws Exception {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        mockMvc.perform(delete("/faculty/"+faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    void getByColor_shouldReturnCollectionFacultiesByColorAndStatus200() throws Exception {

        when(facultyRepository.findAllByColor(faculty.getColor()))
                .thenReturn(List.of(faculty, faculty1));

        mockMvc.perform(get("/faculty/color?color=" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].color").value(faculty.getColor()))
                .andExpect(jsonPath("$.[1].color").value(faculty1.getColor()));
    }

    @Test
    void getByNameOrColor_shouldReturnCollectionFacultiesAndStatus200() throws Exception {
        when(facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(faculty.getName(), faculty1.getColor()))
                .thenReturn(List.of(faculty,faculty1));

        mockMvc.perform(get("/faculty/nameOrColor?name=" + faculty.getName()+"&color="+faculty1.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]name").value(faculty.getName()))
                .andExpect(jsonPath("$.[1]color").value(faculty1.getColor()));
    }

}
