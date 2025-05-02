package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateThreadRequestDto(
        @Schema(description = "Текст комментария", example = "Спасибо!!!")
        @Size(max = 250, message = "Слишком длинный комментарий")
        @NotBlank(message = "Комментарий не может быть пустым")
        String content,

        @JsonProperty("is_anonymous")
        @NotNull(message = "Не может быть пустым")
        Boolean isAnonymous,
        @JsonProperty("parent_comment_id")
        @Min(value = 1, message = "Id публикации коммента 1")
        @NotNull(message = "Id коммента не может быть пустым")
        @Schema(description = "Id коммента, к которому написан тред", example = "1")
        Long parentCommentId) {
}

