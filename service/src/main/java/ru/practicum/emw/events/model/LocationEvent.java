package ru.practicum.emw.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationEvent {

    private int lat;
    private int lon;

}
