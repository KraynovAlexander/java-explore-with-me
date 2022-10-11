package ru.practicum.main.service;

import ru.practicum.main.model.EndpointHit;
import ru.practicum.main.model.EndpointHitDto;
import ru.practicum.main.model.EndpointStat;

import java.util.List;

public interface StatService {
    EndpointHit saveStat(EndpointHitDto endpointHitDto);

    List<EndpointStat> getStatByParams(String start, String end, List<String> uris, Boolean unique);
}
