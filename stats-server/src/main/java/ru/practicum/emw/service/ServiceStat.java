package ru.practicum.emw.service;

import ru.practicum.emw.model.Endpoint;
import ru.practicum.emw.model.EndpointDto;
import ru.practicum.emw.model.EndpointStat;

import java.util.List;

public interface ServiceStat {
    Endpoint save(EndpointDto endpointDto);

    List<EndpointStat> getByParams(String start, String end, List<String> uris, Boolean unique);
}
