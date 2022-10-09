package ru.practicum.emw.compilations.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.compilations.dto.CompilationDto;
import ru.practicum.emw.compilations.model.Compilation;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperCompilation {

    public static CompilationDto toDto(Compilation compilation) {

        CompilationDto compilationDto = new CompilationDto();

        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());

        return compilationDto;
    }

    public static Compilation fromCreateDto(CompilationDto compilationToCreateDto) {

        Compilation compilation = new Compilation();

        compilation.setTitle(compilationToCreateDto.getTitle());
        compilation.setPinned(compilationToCreateDto.isPinned());

        return compilation;

    }

}
