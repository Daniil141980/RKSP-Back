package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Topic;
import edu.tinkoff.tinkoffbackendacademypetproject.model.TopicTypeEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с типами топика
 */
@Service
@RequiredArgsConstructor
public class TopicTypeService {
    /**
     * Репозиторий для работы с типами топиков
     */
    private final TopicRepository topicRepository;

    /**
     * Получение всех типов топиков
     *
     * @return список типов топиков
     */
    public List<TopicTypeEntity> findAll() {
        return topicRepository.findAll();
    }

    public TopicTypeEntity getTopicType(Long id) throws EntityModelNotFoundException {
        return topicRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("Типа топика", "id", Long.toString(id)));
    }

    @Transactional
    public boolean topicExists(Topic topic) {
        return topicRepository.existsByTopic(topic);
    }

    @Transactional
    public TopicTypeEntity save(TopicTypeEntity type) {
        return topicRepository.save(type);
    }
}
