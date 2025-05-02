package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubjectTopicBySubjectIdRequestDto extends PageRequestDto {
    @Min(1)
    @NotNull
    @Schema(description = "Id предмета", example = "1")
    private Long subjectId;
}
