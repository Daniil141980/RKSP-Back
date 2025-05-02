package edu.tinkoff.tinkoffbackendacademypetproject.service;

import edu.tinkoff.tinkoffbackendacademypetproject.CommonAbstractTest;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectTopicEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.CourseRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.SubjectRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.SubjectTopicRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.TopicRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.services.SubjectTopicService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

class SubjectEntityTopicServiceTest extends CommonAbstractTest {
    @Autowired
    private SubjectTopicService subjectTopicService;

    @Autowired
    private SubjectTopicRepository subjectTopicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @AfterEach
    void clear() {
        subjectTopicRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE subject_topic_pk_seq RESTART");
        subjectRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE subject_pk_seq RESTART");
    }

    @DisplayName("Find all subject topics by subject id")
    @ParameterizedTest(name = "{index} - find all subject topics by subject id {2}")
    @CsvSource(value = {
            "0, 50, 2",
            "0, 50, 3"
    })
    void findAllBySubjectIdTest(Integer pageNumber, Integer pageSize, Long subjectId) {
        // given
        createSubjects(1);
        createSubjectTopics(subjectId);

        // when
        var subjectTopicsWhen = subjectTopicService.findAllBySubjectId(pageNumber, pageSize, subjectId);

        // then
        var subjectTopics = subjectTopicRepository.findBySubject_Id(subjectId, PageRequest.of(pageNumber, pageSize, Sort.by("id")));
        assertEquals(subjectTopicsWhen.getSize(), subjectTopics.getSize());
        for (int i = 0; i < subjectTopics.getContent().size(); i++) {
            assertEquals(subjectTopics.getContent().get(i).getId(), subjectTopicsWhen.getContent().get(i).getId());
        }
    }

    @DisplayName("Create subjectTopic")
    @ParameterizedTest(name = "{index} - create subjectTopic")
    @CsvSource(value = {
            "1, 1",
            "2, 3"
    })
    void createSubjectTopicTest(Long topicId, Long subjectId) throws EntityModelNotFoundException {
        // given
        createSubjects(1);
        var subjectTopicGiven = new SubjectTopicEntity(null, topicRepository.findById(topicId).get(), subjectRepository.findById(subjectId).get());

        // when
        subjectTopicService.createSubjectTopic(subjectTopicGiven);

        // then
        var subjectTopics = subjectTopicRepository.findAll();
        assertEquals(1, subjectTopics.size());
        var subjectTopic = subjectTopics.get(0);
        assertEquals(1, subjectTopic.getId());
        assertEquals(subjectId, subjectTopic.getSubject().getId());
        assertEquals(topicId, subjectTopic.getType().getId());
    }

    @DisplayName("Get subjectTopic")
    @ParameterizedTest(name = "{index} - subjectTopic {0} is find")
    @ValueSource(longs = {1, 2})
    void getSubjectTopicTest(Long id) throws EntityModelNotFoundException {
        // given
        createSubjects(1);
        createSubjectTopics(1L);

        // when
        var subjectTopicWhen = subjectTopicService.getSubjectTopic(id);

        // then
        var subjectTopic = subjectTopicRepository.findById(id);
        assertTrue(subjectTopic.isPresent());
        var subjectTopicGet = subjectTopic.get();
        assertEquals(subjectTopicWhen.getSubject().getId(), subjectTopicGet.getSubject().getId());
        assertEquals(subjectTopicWhen.getId(), subjectTopicGet.getId());
        assertEquals(subjectTopicWhen.getType().getId(), subjectTopicGet.getType().getId());
    }

    @DisplayName("Get subjectTopic with throw")
    @ParameterizedTest(name = "{index} - subjectTopic {0} is not find")
    @ValueSource(longs = {1, 2, 3})
    void getSubjectTopicWithThrowTest(Long id) {
        // given

        // when
        var exception = assertThrows(
                EntityModelNotFoundException.class,
                () -> subjectTopicService.getSubjectTopic(id)
        );

        // then
        assertEquals("Топика с id: " + id + " не найдено", exception.getMessage());
    }

    private void createSubjects(Integer courseNumber) {
        subjectRepository.save(new SubjectEntity(null, "Mathematics", null, courseRepository.findById(courseNumber).get()));
        subjectRepository.save(new SubjectEntity(null, "Russian", null, courseRepository.findById(courseNumber).get()));
        subjectRepository.save(new SubjectEntity(null, "Physics", null, courseRepository.findById(courseNumber).get()));
    }

    private void createSubjectTopics(Long subjectId) {
        subjectTopicRepository.save(new SubjectTopicEntity(null, topicRepository.findById(1L).get(), subjectRepository.findById(subjectId).get()));
        subjectTopicRepository.save(new SubjectTopicEntity(null, topicRepository.findById(2L).get(), subjectRepository.findById(subjectId).get()));
    }
}
