package ru.hogwarts.school.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {
    StudentService studentService = mock(StudentService.class);

    AvatarRepository avatarRepository = mock(AvatarRepository.class);

    String avatarsDir = "./src/test/resources/avatar";

    AvatarServiceImpl avatarService = new AvatarServiceImpl(
            avatarsDir, studentService, avatarRepository);

    Student student = new Student(1, "Olga", 17);

    @Test
    void uploadAvatar_avatarSavedToDbAndDirectory() throws IOException {
        String fileName = "1.jpeg";
        MultipartFile file = new MockMultipartFile(
                fileName, fileName,
                "image/jpeg", new byte[]{});
        when(studentService.readStudentById(student.getId())).thenReturn(student);
        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.empty());

        avatarService.uploadAvatar(student.getId(), file);

        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarsDir + "/" + student.getId() +
                "." + fileName.substring(fileName.lastIndexOf(".") + 1))));
    }

    @Test
    void readFromDB_ShouldFindAvatarByStudentIdAndReturnAvatarFromDB() {
        Avatar avatar = new Avatar();
        avatar.setStudent(student);

        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.of(avatar));

        Avatar result = avatarService.readFromDB(student.getId());
        assertEquals(avatar, result);
    }
//
    @Test
    void readFromDB_ShouldThrowExceptionWhenAvatarIsNotFound() {

        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.empty());

        assertThrows(AvatarNotFoundException.class,
                () -> avatarService.readFromDB(student.getId()));
    }

    @Test
    void readFromFile_ShouldFindAvatarByStudentIdAndReturnAvatarFromFile() throws IOException {
        Avatar avatar = new Avatar();
        avatar.setFilePath(avatarsDir);
        avatar.setId();
        File file = new File(avatar.getFilePath());

        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.of(avatar));

        File result = avatarService.readFromFile(student.getId());

        assertEquals(file,result);
    }
}
