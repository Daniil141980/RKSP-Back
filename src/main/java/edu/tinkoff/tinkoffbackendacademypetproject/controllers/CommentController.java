package edu.tinkoff.tinkoffbackendacademypetproject.controllers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.*;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.CommentResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.PageResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.CommentMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.PageMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.CommentAudit;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsAdmin;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsModerator;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsUser;
import edu.tinkoff.tinkoffbackendacademypetproject.services.CommentAudService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Комментарии", description = "Работа с комментариями")
@RequestMapping("/v1/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentAudService commentAudService;
    private final CommentMapper commentMapper;
    private final PageMapper pageMapper;

    @PostMapping
    @Operation(description = "Добавить новый комментарий к публикации", summary = "Добавить новый комментарий к публикации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно добавлен новый комментарий к публикации"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsUser
    public CommentResponseDto createComment(@RequestBody @Valid CreateCommentRequestDto request,
                                            @AuthenticationPrincipal Account account) throws EntityModelNotFoundException {
        return commentMapper.toCommentResponseDto(
                commentService.createComment(commentMapper.fromCreateCommentRequestDto(request), account)
        );
    }

    @GetMapping("/{id}")
    @Operation(description = "Найти комментарий по id", summary = "Найти комментарий по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно найден комментарий по id"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
    })
    public CommentResponseDto getComment(@PathVariable
                                         @Min(value = 1, message = "Id комментария не может быть меньше 1")
                                         @NotNull(message = "Id комментария не может быть пустым")
                                         @Schema(description = "Id комментария", example = "1")
                                         Long id) throws EntityModelNotFoundException {
        return commentMapper.toCommentResponseDto(commentService.getComment(id));
    }

    @PutMapping
    @Operation(description = "Изменить содержимое комментария", summary = "Изменить содержимое комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно изменен комментарий"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsUser
    public CommentResponseDto updateComment(@RequestBody @Valid ChangeCommentRequestDto request,
                                            @AuthenticationPrincipal Account account) throws EntityModelNotFoundException {
        return commentMapper.toCommentResponseDto(
                commentService.updateComment(commentMapper.fromChangeCommentRequestDto(request), account.getNickname())
        );
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удалить комментарий по id", summary = "Удалить комментарий по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "203", description = "Успешно удален комментарий по id"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsModerator
    public void deleteComment(@PathVariable
                              @Min(value = 1, message = "Id комментария не может быть меньше 1")
                              @NotNull(message = "Id комментария не может быть пустым")
                              @Schema(description = "Id комментария", example = "1")
                              Long id) throws EntityModelNotFoundException {
        commentService.deleteComment(id);
    }

    @GetMapping
    @Operation(description = "Получить все комментарии к публикации",
            summary = "Получить все комментарии к публикации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получены все комментарии к публикации"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так")
    })
    public PageResponseDto<CommentResponseDto> getCommentsOnThePublication(@ParameterObject @Valid CommentsOnThePublicationRequestDto request) {
        return pageMapper.toPageResponseDto(commentService.getCommentsOnThePublication(request.getPageNumber(), request.getPageSize(), request.getPublicationId()),
                commentMapper::toCommentResponseDto);
    }

    @PostMapping("/create-comment-reply")
    @Operation(description = "Добавить новый тред", summary = "Добавить новый тред")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно добавлен новый тред"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsUser
    public CommentResponseDto createThread(@RequestBody @Valid CreateThreadRequestDto request,
                                           @AuthenticationPrincipal Account account) throws EntityModelNotFoundException {
        return commentMapper.toCommentResponseDto(
                commentService.createCommentThread(commentMapper.fromCreateThreadRequestDto(request), account)
        );
    }

    @GetMapping("/{id}/replies")
    @Operation(description = "Получить все треды к комментарию",
            summary = "Получить все треды к комментарию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получены все треды к комментарию"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так")
    })
    public PageResponseDto<CommentResponseDto> getThreadsOnTheComment(@ParameterObject @Valid PageRequestDto request,
                                                                      @PathVariable
                                                                      @Min(value = 1, message = "Id комментария не может быть меньше 1")
                                                                      @NotNull(message = "Id комментария не может быть пустым")
                                                                      @Schema(description = "Id комментария", example = "1")
                                                                      Long id) {
        return pageMapper.toPageResponseDto(commentService.getThreadsOnTheComment(request.getPageNumber(), request.getPageSize(), id),
                commentMapper::toCommentResponseDto);
    }


    @GetMapping("/revisions")
    @Operation(description = "Получить ревезии",
            summary = "Получить ревезии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получены ревезии"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Нет прав")
    })
    @IsAdmin
    public List<CommentAudit> getRevisions(@ParameterObject @Valid CommentAudRequestDto commentAudRequestDto) {
        return commentAudService.getRevisionForComment(commentAudRequestDto.getCommentId());
    }
}
