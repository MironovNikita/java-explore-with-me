package ru.practicum.exploreWithMe.compilation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.exploreWithMe.compilation.dto.CreateCompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.FullCompilationDto;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    FullCompilationDto transformCompilationToFullCompilationDto(Compilation compilation);

    @Mapping(target = "events", ignore = true)
    Compilation transformCreateCompilationDtoToCompilation(CreateCompilationDto compilationDto);
}
