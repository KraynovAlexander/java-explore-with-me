package ru.practicum.emw.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.emw.events.model.LocationEvent;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UpdateAdminDtoEvent {

    private Long eventId;

    @NotEmpty
    private String annotation;

    @NotNull
    private Long category;

    @NotEmpty
    private String description;

    private LocalDateTime createdOn = LocalDateTime.now();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private LocationEvent location;

    @NotNull
    private Boolean paid;

    @NotNull
    private Long participantLimit;

    @NotNull
    private Boolean requestModeration;

    @NotEmpty
    private String title;

}
