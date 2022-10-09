package ru.practicum.emw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.model.Endpoint;
import ru.practicum.emw.model.EndpointDto;
import ru.practicum.emw.model.EndpointStat;
import ru.practicum.emw.service.ServiceStat;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final ServiceStat statService;

    @PostMapping("/hit")
    public Endpoint save(@RequestBody @Valid EndpointDto endpointDto) {

        log.info("Controller.save: получен запрос на сохранение статистики с параметрами:\n" +
                "app={},\n" +
                "uri={},\n" +
                "ip={},\n" +
                "timestamp={}\n",
                endpointDto.getApp(), endpointDto.getUri(), endpointDto.getIp(), endpointDto.getTimestamp());

        return statService.save(endpointDto);

    }

    @GetMapping("/stats")
    public List<EndpointStat> getByParams(@RequestParam String start, @RequestParam String end,
                                              @RequestParam List<String> uris,
                                              @RequestParam(required = false, defaultValue = "false") Boolean unique) {

        log.info("Controller.getByParams: получен запрос на получение статистики с параметрами:\n" +
                "start={},\n" +
                "end={},\n" +
                "uris={},\n" +
                "unique={}\n",
                start, end, uris, unique);

        return statService.getByParams(start, end, uris, unique);

    }

}
