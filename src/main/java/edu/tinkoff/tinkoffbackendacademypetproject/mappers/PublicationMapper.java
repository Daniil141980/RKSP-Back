package edu.tinkoff.tinkoffbackendacademypetproject.mappers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.CreateNewsPublicationRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.CreatePublicationRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.PublicationResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.PublicationTitleAndIdResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.model.PublicationEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectTopicEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {FileMapper.class})
public interface PublicationMapper {
    @Named("emptySubjectTopicWithId")
    static Set<SubjectTopicEntity> emptySubjectTopicWithId(Long id) {
        var subjectTopic = new SubjectTopicEntity();
        subjectTopic.setId(id);
        var set = new HashSet<SubjectTopicEntity>();
        set.add(subjectTopic);
        return set;
    }

    @Mapping(target = "nickname", source = "account.nickname")
    PublicationResponseDto toPublicationResponseDto(PublicationEntity publication);

    @Mapping(target = "nickname", source = "account.nickname")
    PublicationTitleAndIdResponseDto toPublicationTitleAndIdResponseDto(PublicationEntity publication);

    @Mapping(target = "publication", ignore = true)
    @Mapping(target = "subjectTopics", qualifiedByName = "emptySubjectTopicWithId", source = "subjectTopicId")
    @Mapping(target = "supportsThread", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "account", ignore = true)
    PublicationEntity fromCreatePublicationRequestDto(CreatePublicationRequestDto publication);

    @Mapping(target = "publication", ignore = true)
    @Mapping(target = "subjectTopics", ignore = true)
    @Mapping(target = "supportsThread", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "account", ignore = true)
    PublicationEntity fromCreateNewsPublicationRequestDto(CreateNewsPublicationRequestDto publication);

}