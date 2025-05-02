package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangeCommentRequestDto(
        @Min(value = 1, message = "Id комментария не может быть меньше 1")
        @NotNull(message = "Id комментария не может быть пустым")
        @Schema(description = "Id комментария", example = "1")
        Long id,

        @Size(max = 250, message = "Слишком длинный комментарий")
        @NotBlank(message = "Комментарий не может быть пустым")
        @Schema(description = "Текст комментария", example = "Спасибо большое за фото!")
        String content) {
}
