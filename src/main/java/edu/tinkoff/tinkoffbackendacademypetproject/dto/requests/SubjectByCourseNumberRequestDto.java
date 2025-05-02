package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubjectByCourseNumberRequestDto extends PageRequestDto {
    @Min(1)
    @NotNull
    @Schema(description = "Номер курса предмета", example = "1")
    private Integer course;
}

