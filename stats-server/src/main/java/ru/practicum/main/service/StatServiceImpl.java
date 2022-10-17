package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.mapper.EndpointHitMapper;
import ru.practicum.main.model.Apps;
import ru.practicum.main.model.EndpointHit;
import ru.practicum.main.model.EndpointHitDto;
import ru.practicum.main.model.EndpointStat;
import ru.practicum.main.repository.AppRepository;
import ru.practicum.main.repository.StatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatRepository statRepository;

    private final AppRepository appRepository;

    @Override
    public EndpointHit saveStat(EndpointHitDto endpointHitDto) {

        log.info("StatService.saveStat: отправьте запрос в базу данных для сохранения endpointHitDto");

        EndpointHit endpointHit = EndpointHitMapper.toEndpointHit(endpointHitDto);

        Optional<Apps> apps = appRepository.getAppsByAppIgnoreCase(endpointHitDto.getApp());

        Long appId;

        if (apps.isEmpty()) {
            Apps newAppsToSave = new Apps();
            newAppsToSave.setApp(endpointHitDto.getApp());

            newAppsToSave = appRepository.save(newAppsToSave);

            appId = newAppsToSave.getId();
        } else {
            appId = apps.get().getId();
        }

        endpointHit.setApp(appId);

        return statRepository.save(endpointHit);
    }

    @Override
    public List<EndpointStat> getStatByParams(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime startTime = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(end, FORMATTER);

        List<EndpointStat> endpointStats = new ArrayList<>();

        List<EndpointHit> hits;

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
