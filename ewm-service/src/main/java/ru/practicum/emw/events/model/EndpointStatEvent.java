package ru.practicum.emw.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointStatEvent {

    private String app;

    private String uri;

    private Long hits;

}
