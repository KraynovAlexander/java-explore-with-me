package ru.practicum.emw.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.model.Endpoint;
import ru.practicum.emw.model.EndpointDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndpointMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Endpoint toEndpointHit(EndpointDto endpointDto) {

        Endpoint endpointHit = new Endpoint();

        endpointHit.setIp(endpointDto.getIp());
        endpointHit.setUri(endpointDto.getUri());

        endpointHit.setTimestamp(LocalDateTime.parse(endpointDto.getTimestamp(), FORMATTER));

        return endpointHit;

    }




}
