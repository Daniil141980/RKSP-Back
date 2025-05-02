package edu.tinkoff.tinkoffbackendacademypetproject.mappers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.FileResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.model.FileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileResponseDto toFileResponseDto(FileEntity file);

}
