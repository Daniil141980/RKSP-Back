package edu.tinkoff.tinkoffbackendacademypetproject.mappers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.SubjectTopicRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.SubjectTopicResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectTopicEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.TopicTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


/**
 * Маппер для топика предмета
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class, TopicMapper.class})
public interface SubjectTopicMapper {
    @Named("getSubjectWithId")
    static SubjectEntity getSubjectWithId(Long id) {
        var subject = new SubjectEntity();
        subject.setId(id);
        return subject;
    }

    @Named("getTopicWithId")
    static TopicTypeEntity getTopicWithId(Long id) {
        var type = new TopicTypeEntity();
        type.setId(id);
        return type;
    }

    /**
     * Конвертация топика предмета в ДТО
     *
     * @param subjectTopic топик предмета
     * @return ДТО топика предмета
     */
    @Mapping(target = "topicTypeResponseDTO", source = "type")
    @Mapping(target = "subjectResponseDTO", source = "subject")
    SubjectTopicResponseDto getSubjectTopicResponseDTO(SubjectTopicEntity subjectTopic);


    @Mapping(target = "id", ignore = true)
    @Mapping(source = "subjectId", target = "subject", qualifiedByName = "getSubjectWithId")
    @Mapping(source = "topicId", target = "type", qualifiedByName = "getTopicWithId")
    SubjectTopicEntity getSubjectTopicFromDTO(SubjectTopicRequestDto dto);
}
