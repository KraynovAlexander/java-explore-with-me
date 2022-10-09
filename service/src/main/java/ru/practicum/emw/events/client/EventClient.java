package ru.practicum.emw.events.client;

import org.springframework.stereotype.Service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.emw.bisnes.helper.DateTimeConverter;
import ru.practicum.emw.events.model.EndpointDtoEvent;
import ru.practicum.emw.events.model.EndpointStatEvent;

import java.net.URI;

import java.time.LocalDateTime;
import java.util.List;



@Service
public class EventClient {

    private final RestTemplate rest;
    private final String serverUrl;

    private static final String HIT = "/hit";
    private static final String STATS = "/stats";

    public EventClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        this.rest = builder.build();
        this.serverUrl = serverUrl;
        rest.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public void saveStat(String app, String uri, String ip) {

        EndpointDtoEvent endpointHitDto = new EndpointDtoEvent();

        endpointHitDto.setApp(app);
        endpointHitDto.setIp(ip);
        endpointHitDto.setUri(uri);
        endpointHitDto.setTimestamp(DateTimeConverter.toFormattedString(LocalDateTime.now()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<EndpointDtoEvent> requestEntity = new HttpEntity<>(endpointHitDto, headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(serverUrl + HIT);

        ResponseEntity<Object> response = rest.exchange(
                uriBuilder.build().encode().toUri(),
                HttpMethod.POST,
                requestEntity,
                Object.class);

    }

    public ResponseEntity<List<EndpointStatEvent>> getStat(String start, String end,
                                                           List<String> uris) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(serverUrl + STATS);

        uriBuilder.queryParam("start", start);
        uriBuilder.queryParam("end", end);

        for (String s : uris) {
            uriBuilder.queryParam("uris", s);
        }

        URI uri = uriBuilder.build().encode().toUri();

        return rest.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

    }

}
