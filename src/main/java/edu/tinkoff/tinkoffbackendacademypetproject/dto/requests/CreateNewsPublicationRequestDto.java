package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateNewsPublicationRequestDto(
        @Schema(description = "Заголовок публикации", example = "Контрольная работа 1")
        @Size(max = 150, message = "Слишком длинный заголовок публикации")
        @NotBlank(message = "Заголовок публикации не может быть пустым")
        String title,

        @Schema(description = "Описание публикации", example = "Фото контрольной работы")
        @Size(max = 300, message = "Слишком длинное описание публикации")
        @NotBlank(message = "Описание публикации не может быть пустым")
        String description,

        @Size(max = 10)
        List<MultipartFile> files) {
}
