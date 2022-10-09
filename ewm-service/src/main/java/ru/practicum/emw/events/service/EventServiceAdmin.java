package ru.practicum.emw.events.service;





import ru.practicum.emw.events.dto.GetDtoEvent;
import ru.practicum.emw.events.dto.UpdateAdminDtoEvent;

import java.util.List;

public interface EventServiceAdmin {
    List<GetDtoEvent> eventSearchByAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    GetDtoEvent updateEventByAdmin(UpdateAdminDtoEvent eventToUpdateByAdminDto, Long eventId);

    GetDtoEvent publishEventByAdmin(Long eventId);

    GetDtoEvent rejectEventByAdmin(Long eventId);
}
