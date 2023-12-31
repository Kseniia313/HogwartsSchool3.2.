package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;


import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFaculty(@PathVariable long id) {
        return facultyService.getFacultyById(id);
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.upDateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty deleteFaculty(@PathVariable long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("/color")
    public Collection<Faculty> getByColor
            (@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping("/nameOrColor")
    public Collection<Faculty> getByNameOrColor(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String color) {
        return facultyService.findByNameOrColor(name, color);
    }

    @GetMapping("/facultyNameWithMaxLength")
    public ResponseEntity<String> getFacultyNameWithMaxLength() {
        return facultyService.getFacultyNameWithMaxLength();
    }
}
