package edu.tinkoff.tinkoffbackendacademypetproject.service;

import edu.tinkoff.tinkoffbackendacademypetproject.CommonAbstractTest;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.CourseRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.services.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CourseEntityServiceTest extends CommonAbstractTest {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @DisplayName("Find all courses")
    @Test
    void findAllCoursesTest() {
        // given

        // when
        var coursesWhen = courseService.findAll();

        // then
        var courses = courseRepository.findAll();
        for (int i = 0; i < 6; i++) {
            assertEquals(courses.get(i).getDescription(), coursesWhen.get(i).getDescription());
            assertEquals(courses.get(i).getCourseNumber(), coursesWhen.get(i).getCourseNumber());
        }
    }

    @DisplayName("Get course")
    @ParameterizedTest(name = "{index} - course {0} is find")
    @ValueSource(ints = {1, 2, 3})
    void getCourseTest(Integer id) throws EntityModelNotFoundException {
        // given

        // when
        var courseWhen = courseService.getCourse(id);

        // then
        var course = courseRepository.findById(id);
        assertTrue(course.isPresent());
        assertEquals(course.get().getDescription(), courseWhen.getDescription());
        assertEquals(course.get().getCourseNumber(), courseWhen.getCourseNumber());
    }

    @DisplayName("Get course with throw")
    @ParameterizedTest(name = "{index} - course {0} is not find")
    @ValueSource(ints = {7, 8, 9})
    void getCourseWithThrowTest(Integer id) {
        // given

        // when
        var exception = assertThrows(
                EntityModelNotFoundException.class,
                () -> courseService.getCourse(id)
        );

        // then
        assertEquals("Курса с id: " + id + " не найдено", exception.getMessage());
    }
}
