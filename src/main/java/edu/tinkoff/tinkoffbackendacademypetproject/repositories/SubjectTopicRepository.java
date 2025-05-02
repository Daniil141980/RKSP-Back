package edu.tinkoff.tinkoffbackendacademypetproject.repositories;

import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectTopicEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.TopicTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectTopicRepository extends JpaRepository<SubjectTopicEntity, Long> {
    boolean existsBySubjectAndType(SubjectEntity subject, TopicTypeEntity type);

    Page<SubjectTopicEntity> findBySubject_Id(Long subjectId, Pageable pageable);
}
