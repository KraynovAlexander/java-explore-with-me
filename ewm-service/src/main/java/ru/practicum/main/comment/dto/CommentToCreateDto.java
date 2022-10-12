package ru.practicum.main.comment.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.main.categories.model.Update;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentToCreateDto {

    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank
    @Length(max = 800)
    private String text;

    @NotNull
    private Long eventId;

}