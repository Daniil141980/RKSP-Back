package edu.tinkoff.tinkoffbackendacademypetproject.service;

import edu.tinkoff.tinkoffbackendacademypetproject.CommonAbstractTest;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.SubjectAlreadyExistsException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.CourseRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.SubjectRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.services.SubjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

class SubjectEntityServiceTest extends CommonAbstractTest {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @AfterEach
    void clear() {
        subjectRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE subject_pk_seq RESTART");
    }

    @DisplayName("Find all subjects by course number")
    @ParameterizedTest(name = "{index} - find all subjects by course number {2}")
    @CsvSource(value = {
            "0, 50, 2",
            "0, 50, 3"
    })
    void findAllByCourseNumberTest(Integer pageNumber, Integer pageSize, Integer courseNumber) {
        // given
        createSubjects(courseNumber);

        // when
        var subjectsWhen = subjectService.findAllByCourseNumber(pageNumber, pageSize, courseNumber);

        // then
        var subjects = subjectRepository.findByCourse_CourseNumber(courseNumber, PageRequest.of(pageNumber, pageSize, Sort.by("name")));
        assertEquals(subjects.getSize(), subjectsWhen.getSize());
        for (int i = 0; i < subjects.getContent().size(); i++) {
            assertEquals(subjects.getContent().get(i).getId(), subjectsWhen.getContent().get(i).getId());
        }
    }

    @DisplayName("Find all subjects")
    @Test
    void findAllSubjectsTest() {
        // given
        createSubjects(1);
        createSubjects(2);

        // when
        var subjectsWhen = subjectService.findAll();

        // then
        var subjects = subjectRepository.findAll();
        for (int i = 0; i < 3; i++) {
            assertEquals(subjects.get(i).getId(), subjectsWhen.get(i).getId());
            assertEquals(subjects.get(i).getName(), subjectsWhen.get(i).getName());
        }
    }

    @DisplayName("Create subject")
    @ParameterizedTest(name = "{index} - create subject {0}")
    @CsvSource(value = {
            "Mathematics, 1",
            "Russian, 2",
            "Physics, 3"
    })
    void createSubjectTest(String name, Integer courseNumber) throws EntityModelNotFoundException {
        // given
        var subjectGiven = new SubjectEntity(null, name, null, courseRepository.findById(courseNumber).get());

        // when
        subjectService.createSubject(subjectGiven);

        // then
        var subjects = subjectRepository.findAll();
        assertEquals(1, subjects.size());
        var subject = subjects.get(0);
        assertEquals(name, subject.getName());
        assertEquals(courseNumber, subject.getCourse().getCourseNumber());
        assertEquals(1, subject.getId());
    }


    @DisplayName("Create subject with throw SubjectAlreadyExistsException")
    @ParameterizedTest(name = "{index} - create subject {0}")
    @CsvSource(value = {
            "Mathematics, 1",
            "Russian, 2",
            "Physics, 3"
    })
    void createSubjectWithThrowTest(String name, Integer courseNumber) throws EntityModelNotFoundException {
        // given
        var subjectGiven = new SubjectEntity(null, name, null, courseRepository.findById(courseNumber).get());
        createSubjects(courseNumber);

        // when
        var exception = assertThrows(
                SubjectAlreadyExistsException.class,
                () -> subjectService.createSubject(subjectGiven)
        );

        // then
        assertEquals("Предмет с названием " + name + " уже существует", exception.getMessage());
    }

    @DisplayName("Get subject")
    @ParameterizedTest(name = "{index} - subject {0} is find")
    @ValueSource(longs = {1, 3})
    void getSubjectTest(Long id) throws EntityModelNotFoundException {
        // given
        createSubjects(1);

        // when
        var subjectWhen = subjectService.getSubject(id);

        // then
        var subject = subjectRepository.findById(id);
        assertTrue(subject.isPresent());
        var subjectGet = subject.get();
        assertEquals(subjectWhen.getName(), subjectGet.getName());
        assertEquals(subjectWhen.getId(), subjectGet.getId());
    }

    @DisplayName("Get subject with throw")
    @ParameterizedTest(name = "{index} - subject {0} is not find")
    @ValueSource(longs = {1, 2, 3})
    void getSubjectWithThrowTest(Long id) {
        // given

        // when
        var exception = assertThrows(
                EntityModelNotFoundException.class,
                () -> subjectService.getSubject(id)
        );

        // then
        assertEquals("Предмета с id: " + id + " не найдено", exception.getMessage());
    }


    private void createSubjects(Integer courseNumber) {
        subjectRepository.save(new SubjectEntity(null, "Mathematics", null, courseRepository.findById(courseNumber).get()));
        subjectRepository.save(new SubjectEntity(null, "Russian", null, courseRepository.findById(courseNumber).get()));
        subjectRepository.save(new SubjectEntity(null, "Physics", null, courseRepository.findById(courseNumber).get()));
    }
}
