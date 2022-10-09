package ru.practicum.emw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.emw.mapper.EndpointMapper;
import ru.practicum.emw.model.Endpoint;
import ru.practicum.emw.model.EndpointDto;
import ru.practicum.emw.model.EndpointStat;
import ru.practicum.emw.repository.AppRepository;
import ru.practicum.emw.repository.StatRepository;
import ru.practicum.emw.model.Apps;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceStatImpl implements ServiceStat {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatRepository statRepository;

    private final AppRepository appRepository;

    @Override
    public Endpoint save(EndpointDto endpointDto) {

        log.info("StatService.saveStat: отправьте запрос в базу данных для сохранения ");

        Endpoint endpointHit = EndpointMapper.toEndpointHit(endpointDto);

        Optional<Apps> apps = appRepository.getAppsByAppIgnoreCase(endpointDto.getApp());

        Long appId;

        if (apps.isEmpty()) {
            Apps newAppsToSave = new Apps();
            newAppsToSave.setApp(endpointDto.getApp());

            newAppsToSave = appRepository.save(newAppsToSave);

            appId = newAppsToSave.getId();
        } else {
            appId = apps.get().getId();
        }

        endpointHit.setApp(appId);

        return statRepository.save(endpointHit);
    }

    @Override
    public List<EndpointStat> getByParams(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime startTime = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(end, FORMATTER);

        List<EndpointStat> endpointStats = new ArrayList<>();

        List<Endpoint> hits;

        uris = uris.stream()
                .map(x -> x.replace("[", ""))
                .map(x -> x.replace("]", ""))
                .collect(Collectors.toList());

        if (unique) {
            hits = statRepository.findStatByUrisByTimeDistinct(startTime, endTime, uris);
        } else {
            hits = statRepository.getEndpointHitsByUriInAndTimestampBetween(uris, startTime, endTime);
        }

        for (String uri : uris) {
            hits = hits.stream()
                    .filter(x -> x.getUri().equals(uri))
                    .collect(Collectors.toList());

            if (hits.size() > 0) {
                endpointStats.add(new EndpointStat(appRepository.getAppsById(hits.get(0).getApp()).getApp(),
                        hits.get(0).getUri(),
                        (long) hits.size()));
            }
        }

        return endpointStats;

    }
}
