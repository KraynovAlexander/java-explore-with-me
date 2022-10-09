package ru.practicum.emw.compilations.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CreateDto {

    private boolean pinned;

    @NotBlank
    @Length(max = 300)
    private String title;

    private List<Long> events;

}
