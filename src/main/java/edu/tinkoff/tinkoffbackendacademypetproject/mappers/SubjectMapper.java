package edu.tinkoff.tinkoffbackendacademypetproject.mappers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.SubjectRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.SubjectResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.model.CourseEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Маппер для предмета
 */
@Mapper(componentModel = "spring")
public interface SubjectMapper {

    /**
     * Перевод названия предмета в верхний регистр
     *
     * @param name название предмета
     * @return название предмета в верхнем регистре
     */
    @Named("getSubjectName")
    static String getSubjectName(String name) {
        return name.trim().toUpperCase();
    }

    @Named("emptyCourseWithCourseNumber")
    static CourseEntity emptyCourseWithCourseNumber(Integer courseNumber) {
        var course = new CourseEntity();
        course.setCourseNumber(courseNumber);
        return course;
    }

    /**
     * Конвертация объекта Subject в ДТО-ответ
     *
     * @param subject объект предмета
     * @return ДТО
     */
    SubjectResponseDto toSubjectResponseDTO(SubjectEntity subject);


    /**
     * Конвертация из ДТО в entity-класс
     *
     * @param dto ДТО
     * @return объекта класса Subject
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subjectTopics", ignore = true)
    @Mapping(target = "course", qualifiedByName = "emptyCourseWithCourseNumber", source = "courseNumber")
    @Mapping(target = "name", qualifiedByName = "getSubjectName", source = "name")
    SubjectEntity fromSubjectRequestDTO(SubjectRequestDto dto);
}
