package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.SubjectAlreadyExistsException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с предметами
 */
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final CourseService courseService;

    /**
     * Получение всех предметов, которые есть на указанном курсе
     *
     * @param courseNumber фильтр курса
     * @param pageSize     размер страницы
     * @param pageNumber   номер текущей страницы
     * @return список предметов
     */

    @Transactional
    public Page<SubjectEntity> findAllByCourseNumber(Integer pageNumber, Integer pageSize, Integer courseNumber) {
        return subjectRepository.findByCourse_CourseNumber(courseNumber, PageRequest.of(pageNumber, pageSize, Sort.by("name")));
    }

    /**
     * Получение списка всех предметов
     *
     * @return список всех предметов
     */
    @Transactional
    public List<SubjectEntity> findAll() {
        return subjectRepository.findAll();
    }

    /**
     * Создание нового предмета
     *
     * @param subject предмет, который надо создать
     * @return сохраненный предмет
     * @throws SubjectAlreadyExistsException если предмет с таким названием уже существует
     */
    @Transactional
    public SubjectEntity createSubject(SubjectEntity subject) throws SubjectAlreadyExistsException, EntityModelNotFoundException {
        if (subjectRepository.existsSubjectByNameAndCourse_CourseNumber(subject.getName(), subject.getCourse().getCourseNumber())) {
            throw new SubjectAlreadyExistsException(subject.getName());
        }
        subject.setCourse(courseService.getCourse(subject.getCourse().getCourseNumber()));
        return subjectRepository.save(subject);
    }

    public SubjectEntity getSubject(Long id) throws EntityModelNotFoundException {
        return subjectRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("Предмета", "id", Long.toString(id)));
    }

    @Transactional
    public void deleteSubject(Long id) {
        subjectRepository.delete(getSubject(id));
    }
}
