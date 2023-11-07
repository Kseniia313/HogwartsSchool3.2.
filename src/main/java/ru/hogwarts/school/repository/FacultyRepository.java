package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;


import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    public Collection<Faculty> findAllByColor(String color);

    public Collection<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
