package ru.practicum.main.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointStat {

    private String app;

    private String uri;

    private Long hits;

}
