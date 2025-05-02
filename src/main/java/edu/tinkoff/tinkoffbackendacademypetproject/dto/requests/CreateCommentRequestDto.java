package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentRequestDto(
        @Schema(description = "Текст комментария", example = "Спасибо!!!")
        @Size(max = 250, message = "Слишком длинный комментарий")
        @NotBlank(message = "Комментарий не может быть пустым")
        String content,

        @JsonProperty("publication_id")
        @Min(value = 1, message = "Id публикации не может быть меньше 1")
        @NotNull(message = "Id публикации не может быть пустым")
        @Schema(description = "Id публикации, к которой написан комментарий", example = "1")
        Long publicationId,
        @JsonProperty("is_anonymous")
        @NotNull(message = "Не может быть пустым")
        Boolean isAnonymous) {
}
