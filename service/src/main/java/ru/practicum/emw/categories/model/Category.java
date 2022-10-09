package ru.practicum.emw.categories.model;

import lombok.Data;
import ru.practicum.emw.events.model.Event;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "categories", schema = "public")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    List<Event> events;

}
