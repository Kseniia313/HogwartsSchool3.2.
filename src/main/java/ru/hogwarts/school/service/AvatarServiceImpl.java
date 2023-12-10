package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    private final String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(@Value("${path.to.avatars.folder}") String avatarsDir,
                             StudentService studentService,
                             AvatarRepository avatarRepository) {
        this.avatarsDir = avatarsDir;
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }


    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile)
            throws IOException {
        Student student = studentService.readStudentById(studentId);
        Path filePath = saveToFile(student, avatarFile);
        saveToDB(filePath, avatarFile, student);

    }

    private Path saveToFile(Student student, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for saving avatar to filePath");
        Path filePath = Path.of(avatarsDir,
                student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return filePath;
    }

    private void saveToDB(Path filePath, MultipartFile avatarFile, Student student) throws IOException {
        logger.info("Was invoked method for saving avatar to DataBase");
        Avatar avatar = avatarRepository.findByStudent_id(student.getId()).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setData(avatarFile.getBytes());
        avatar.setMediaType(avatarFile.getContentType());

        avatarRepository.save(avatar);
    }

    @Override
    public Avatar readFromDB(long id) {
        if (!avatarRepository.existsById(id))
            logger.error("Аватар для студента c id {} не найден", id);
        return avatarRepository.findByStudent_id(id)
                .orElseThrow(() -> new AvatarNotFoundException("Аватар не найден"));
    }

    @Override
    public File readFromFile(long id) throws IOException {
        logger.info("Was invoked method for reading avatar from File");
        Avatar avatar = readFromDB(id);
        return new File(avatar.getFilePath());

    }

    @Override
    public Collection<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for getting Collection of all avatars ");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
