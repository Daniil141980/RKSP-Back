package edu.tinkoff.tinkoffbackendacademypetproject.repositories;

import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с Subject-объектами
 */
@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    /**
     * Проверка существования предмета с таким названием
     *
     * @param name название предмета
     * @return факт существования
     */
    boolean existsSubjectByNameAndCourse_CourseNumber(String name, Integer courseNumber);

    Page<SubjectEntity> findByCourse_CourseNumber(Integer courseNumber, Pageable pageable);
}
