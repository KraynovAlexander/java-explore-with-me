package ru.practicum.emw.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.emw.categories.model.Create;
import ru.practicum.emw.categories.model.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCategory {

    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank(groups = {Create.class, Update.class})
    @Length(max = 100, groups = {Create.class, Update.class})
    private String name;

}
