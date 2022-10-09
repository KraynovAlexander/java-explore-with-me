package ru.practicum.emw.events.model;

import lombok.Data;

@Data
public class EndpointDtoEvent {

    private Long id;

    private String app;

    private String uri;

    private String ip;

    private String timestamp;

}
