package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.AlreadyExistsException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.SubjectTopicAlreadyExistsException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectTopicEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.SubjectTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с типами топиков для предметов
 */
@Service
@RequiredArgsConstructor
public class SubjectTopicService {
    /**
     * Репозиторий для работы с топиками для предметов
     */
    private final SubjectTopicRepository subjectTopicRepository;
    private final SubjectService subjectService;
    private final TopicTypeService topicTypeService;


    public SubjectTopicEntity getSubjectTopic(Long id) throws EntityModelNotFoundException {
        return subjectTopicRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("Топика", "id", Long.toString(id)));
    }

    @Transactional
    public Page<SubjectTopicEntity> findAllBySubjectId(Integer pageNumber, Integer pageSize, Long subjectId) {
        return subjectTopicRepository.findBySubject_Id(subjectId, PageRequest.of(pageNumber, pageSize, Sort.by("id")));
    }

    @Transactional
    public SubjectTopicEntity createSubjectTopic(SubjectTopicEntity subjectTopic) throws EntityModelNotFoundException, AlreadyExistsException {
        subjectTopic.setSubject(subjectService.getSubject(subjectTopic.getSubject().getId()));
        subjectTopic.setType(topicTypeService.getTopicType(subjectTopic.getType().getId()));
        if (subjectTopicRepository.existsBySubjectAndType(subjectTopic.getSubject(), subjectTopic.getType())) {
            throw new SubjectTopicAlreadyExistsException();
        }
        return subjectTopicRepository.save(subjectTopic);
    }
}
