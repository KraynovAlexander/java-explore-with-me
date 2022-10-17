package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.EndpointHit;
import ru.practicum.main.model.EndpointHitDto;
import ru.practicum.main.model.EndpointStat;
import ru.practicum.main.service.StatService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {

    private final StatService statService;

    @PostMapping("/hit")
    public EndpointHit saveStat(@RequestBody @Valid EndpointHitDto endpointHitDto) {

        log.info("StatController.saveStat: получен запрос на сохранение статистики с параметрами:\n" +
                "app={},\n" +
                "uri={},\n" +
                "ip={},\n" +
                "timestamp={}\n",
                endpointHitDto.getApp(), endpointHitDto.getUri(), endpointHitDto.getIp(), endpointHitDto.getTimestamp());

        return statService.saveStat(endpointHitDto);

    }

    @GetMapping("/stats")
    public List<EndpointStat> getStatByParams(@RequestParam String start, @RequestParam String end,
                                              @RequestParam List<String> uris,
                                              @RequestParam(required = false, defaultValue = "false") Boolean unique) {

        log.info("StatController.getStatsByParams: получен запрос на получение статистики с параметрами:\n" +
                "start={},\n" +
                "end={},\n" +
                "uris={},\n" +
                "unique={}\n",
                start, end, uris, unique);

        return statService.getStatByParams(start, end, uris, unique);

    }

}
