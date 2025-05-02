package edu.tinkoff.tinkoffbackendacademypetproject.mappers;


import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PageMapperImpl implements PageMapper {

    @Override
    public <T, R> PageResponseDto<T> toPageResponseDto(Page<R> page, Function<R, T> mapper) {
        return new PageResponseDto<>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.getContent().stream().map(mapper).toList()
        );
    }

}
