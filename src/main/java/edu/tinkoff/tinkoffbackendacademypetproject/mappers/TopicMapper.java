package edu.tinkoff.tinkoffbackendacademypetproject.mappers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.TopicTypeResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Topic;
import edu.tinkoff.tinkoffbackendacademypetproject.model.TopicTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Конвертор для работы с типом топиков
 */
@Mapper(componentModel = "spring")
public interface TopicMapper {

    /**
     * Получение описания типа топика
     *
     * @param topic тип топика
     * @return описание типа топика
     */
    @Named("getTopicType")
    static String getTopicType(Topic topic) {
        return topic.getDescription();
    }

    /**
     * Конвертация объекта типа топика в ДТО типа топика
     *
     * @param type тип топика
     * @return ДТО топика
     */
    @Mapping(source = "type.topic", target = "topicType", qualifiedByName = "getTopicType")
    TopicTypeResponseDto toTopicResponseDTO(TopicTypeEntity type);

    /**
     * Конвертация списка объектов типа топика в список ДТО типа топика
     *
     * @param types список объектов типа топика
     * @return список ДТО типа топика
     */
    List<TopicTypeResponseDto> toListTopicResponseDTO(List<TopicTypeEntity> types);
}
