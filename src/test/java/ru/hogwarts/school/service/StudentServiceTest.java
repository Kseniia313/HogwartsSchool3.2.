package ru.hogwarts.school.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.*;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;


    private Student student = new Student(1, "Anna", 25);

    private Faculty faculty = new Faculty(1, "history", "red");

    private List<Student> students = List.of((student),
            new Student(2, "Klim", 15),
            new Student(3, "Kira", 25));

    @Test
    void createStudent_shouldCreateAndSaveStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.createStudent(student);

        assertEquals(student, result);
    }

    @Test
    void readStudentById_shouldFindStudentByIdAndReturnStudent() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));

        Student result = studentService.readStudentById(student.getId());

        assertEquals(student, result);
    }

    @Test
    void readStudentById_shouldThrowExceptionWhenStudentIdIsNotFound() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.readStudentById(student.getId()));
    }

    @Test
    void updateStudent_shouldUpDateStudentAndReturnStudent() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));

        Student result = studentService.readStudentById(student.getId());

        assertEquals(student, result);
    }

    @Test
    void updateStudent_shouldThrowExceptionWhenStudentIsNotFound() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class,
                () -> studentService.readStudentById(student.getId()));
    }

    @Test
    void removeStudent_shouldRemoveStudentAndReturnRemovedStudent() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));
        Student result = studentService.removeStudent(student.getId());
        assertEquals(student, result);
    }

    @Test
    void removeStudent_shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.removeStudent(student.getId()));
    }

    @Test
    void readByAge_shouldReadStudentsByAgeAndReturnCollectionOfStudent() {
        int age = 15;

        when(studentRepository.findAllByAge(age)).thenReturn(students);

        List<Student> studentList = students.stream().filter(st -> st.getAge() == age)
                .collect(Collectors.toUnmodifiableList());

        Collection<Student> result = studentService.readByAge(age)
                .stream().filter(st -> st.getAge() == age).collect(Collectors.toUnmodifiableList());

        assertEquals(studentList, result);

    }

    @Test
    void findByAgeBetween_shouldFindStudentsBetweenAgeAndReturnCollectionOfStudents() {
        int min = 15;
        int max = 25;
        int age = student.getAge();

        when(studentRepository.findByAgeBetween(min, max)).thenReturn(students);

        Optional<Student> studentList = students.stream().filter(st -> st.getAge() == student.getAge())
                .min((Comparator.comparingInt(st -> min)
                        .thenComparingInt(st -> max)));

        Optional<Student> result = studentService.findByAgeBetween(min, max)
                .stream().filter(st -> st.getAge() == student.getAge())
                .min((Comparator.comparingInt(st -> min)
                        .thenComparingInt(st -> max)));
        assertEquals(studentList, result);

    }

        @Test
    void getStudentsByFacultyId_shouldFindStudentsByFacultyIdAndReturnCollectionOfStudents() {

      students.get(0).setFaculty(faculty);
      students.get(1).setFaculty(faculty);

        long facultyId = faculty.getId();

        when(studentRepository.findAllByFaculty_Id(facultyId)).thenReturn(students);

            List<Student> studentList = students.stream().filter(st->st.getFaculty()==faculty)
                            .collect(Collectors.toUnmodifiableList());

         List < Student > result = (List<Student>) studentService.getStudentsByFacultyId(facultyId)
                 .stream().filter(st->st.getFaculty()==faculty).collect(Collectors.toUnmodifiableList());

            assertEquals(studentList, result);
    }
    @Test
    void getFacultyByStudentId_shouldFindAndReturnFacultyByStudentId() {

            student.setFaculty(faculty);
            Faculty faculty1 = student.getFaculty();

            when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

            Faculty result = studentService.getFacultyByStudentId(student.getId());

            assertEquals(faculty1, result);
        }
    }





