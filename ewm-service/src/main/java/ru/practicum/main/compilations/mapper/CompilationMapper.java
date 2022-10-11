package ru.practicum.main.compilations.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.CompilationToCreateDto;
import ru.practicum.main.compilations.model.Compilation;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation) {

        CompilationDto compilationDto = new CompilationDto();

        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());

        return compilationDto;
    }

    public static Compilation fromCreateDto(CompilationToCreateDto compilationToCreateDto) {

        Compilation compilation = new Compilation();

        compilation.setTitle(compilationToCreateDto.getTitle());
        compilation.setPinned(compilationToCreateDto.isPinned());

        return compilation;

    }

}
