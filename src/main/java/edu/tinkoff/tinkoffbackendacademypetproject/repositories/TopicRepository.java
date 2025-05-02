package edu.tinkoff.tinkoffbackendacademypetproject.repositories;

import edu.tinkoff.tinkoffbackendacademypetproject.model.Topic;
import edu.tinkoff.tinkoffbackendacademypetproject.model.TopicTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с типами топиков
 */
@Repository
public interface TopicRepository extends JpaRepository<TopicTypeEntity, Long> {
    boolean existsByTopic(Topic topic);
}
