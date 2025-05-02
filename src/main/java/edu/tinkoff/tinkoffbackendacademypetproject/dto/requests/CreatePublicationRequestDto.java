package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreatePublicationRequestDto(
        @Schema(description = "Заголовок публикации", example = "Контрольная работа 1")
        @Size(max = 150, message = "Слишком длинный заголовок публикации")
        @NotBlank(message = "Заголовок публикации не может быть пустым")
        String title,

        @Schema(description = "Описание публикации", example = "Фото контрольной работы")
        @Size(max = 300, message = "Слишком длинное описание публикации")
        @NotBlank(message = "Описание публикации не может быть пустым")
        String description,

        @Min(value = 1, message = "Id топика не может быть меньше 1")
        @NotNull(message = "Id топика не может быть пустым")
        @Schema(description = "Id топика, в котором сделана публикация", example = "1")
        Long subjectTopicId,

        @Size(max = 10)
        List<MultipartFile> files) {
}
