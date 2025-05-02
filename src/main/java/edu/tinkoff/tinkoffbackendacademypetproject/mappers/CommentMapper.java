package edu.tinkoff.tinkoffbackendacademypetproject.mappers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.ChangeCommentRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.CreateCommentRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.CreateThreadRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.CommentResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.model.CommentEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.PublicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Named("emptyPublicationWithId")
    static PublicationEntity emptyPublicationWithId(Long id) {
        var publication = new PublicationEntity();
        publication.setId(id);
        return publication;
    }

    @Named("emptyCommentWithId")
    static CommentEntity emptyCommentWithId(Long id) {
        var comment = new CommentEntity();
        comment.setId(id);
        return comment;
    }

    @Mapping(target = "nickname", expression = "java(comment.getIsAnonymous() ? \"\" : comment.getAccount().getNickname())")
    CommentResponseDto toCommentResponseDto(CommentEntity comment);


    @Mapping(target = "thread", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "publication", qualifiedByName = "emptyPublicationWithId", source = "publicationId")
    @Mapping(target = "account", ignore = true)
    CommentEntity fromCreateCommentRequestDto(CreateCommentRequestDto comment);

    @Mapping(target = "thread", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "isAnonymous", ignore = true)
    @Mapping(target = "publication", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "account", ignore = true)
    CommentEntity fromChangeCommentRequestDto(ChangeCommentRequestDto comment);

    @Mapping(target = "thread", ignore = true)
    @Mapping(target = "parent", qualifiedByName = "emptyCommentWithId", source = "parentCommentId")
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "publication", ignore = true)
    @Mapping(target = "account", ignore = true)
    CommentEntity fromCreateThreadRequestDto(CreateThreadRequestDto comment);
}
