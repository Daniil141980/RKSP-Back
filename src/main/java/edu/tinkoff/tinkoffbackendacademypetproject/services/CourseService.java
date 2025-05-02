package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.CourseEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с курсами
 */
@Service
@RequiredArgsConstructor
public class CourseService {
    /**
     * Репозиторий для работы с курсами
     */
    private final CourseRepository courseRepository;

    /**
     * Получение всех курсов
     *
     * @return список всех курсов
     */
    @Transactional
    public List<CourseEntity> findAll() {
        return courseRepository.findAll();
    }

    public CourseEntity getCourse(Integer id) throws EntityModelNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("Курса", "id", Long.toString(id)));
    }
}
