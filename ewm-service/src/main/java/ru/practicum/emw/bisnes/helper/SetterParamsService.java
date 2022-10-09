package ru.practicum.emw.bisnes.helper;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.categories.service.CategoryService;
import ru.practicum.emw.events.client.EventClient;
import ru.practicum.emw.events.dto.CompilationDtoEvent;
import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.model.EndpointStatEvent;
import ru.practicum.emw.users.service.UserService;


import java.time.LocalDateTime;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SetterParamsService {

    private final CategoryService categoryService;

    private final UserService userService;

    private final EventClient eventClient;

    public GetDtoEvent setCategoryNameAndInitiatorName(GetDtoEvent eventToGetDto) {

        eventToGetDto.getCategory().setName(categoryService.getCategoryNameById(eventToGetDto.getCategory().getId()));
        eventToGetDto.getInitiator().setName(userService.getUserNameById(eventToGetDto.getInitiator().getId()));
        eventToGetDto.setViews(getViewsFromStatServiceToEventsDto(eventToGetDto.getId()));

        return eventToGetDto;

    }

    public CompilationDtoEvent setCategoryNameAndInitiatorName(CompilationDtoEvent eventToCompilationDto) {

        eventToCompilationDto.getCategory().setName(categoryService.getCategoryNameById(eventToCompilationDto.getCategory().getId()));
        eventToCompilationDto.getInitiator().setName(userService.getUserNameById(eventToCompilationDto.getInitiator().getId()));
        eventToCompilationDto.setViews(getViewsFromStatServiceToEventsDto(eventToCompilationDto.getId()));

        return eventToCompilationDto;
    }

    private Long getViewsFromStatServiceToEventsDto(Long eventId) {

        String apiPrefix = "/events/";


        String start = DateTimeConverter.toFormattedString(LocalDateTime.now().minusYears(20));
        String end = DateTimeConverter.toFormattedString(LocalDateTime.now().plusYears(1));

        List<String> uris = List.of(apiPrefix + eventId);

        ResponseEntity<List<EndpointStatEvent>> response = eventClient.getStat(start, end, uris);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Ошибка на сервисе статистики");
        }

        List<EndpointStatEvent> stats = response.getBody();

        if (stats == null) {
            return 0L;
        } else if (stats.size() > 0) {
            return stats.get(0).getHits();
        } else {
            return 0L;
        }

    }

}
