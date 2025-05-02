package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Класс с содержанием кода ошибки и сообщением
 */
@RequiredArgsConstructor
@Getter
public class ApiErrorResponse {
    /**
     * Содержание ошибки
     */
    @Schema(name = "содержание ошибки", example = "Такой человек не существует")
    private final String message;

    /**
     * Дата формирования ошибки
     */
    @Schema(name = "дата формирования ошибки", example = "2020-10-10 13:13:14")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();

    /**
     * Код ошибки
     */
    @Schema(name = "код ошибки", example = "BAD_REQUEST")
    private final HttpStatus status;
}
