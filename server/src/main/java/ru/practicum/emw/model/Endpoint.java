package ru.practicum.emw.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "endpoint_stat", schema = "public")
public class Endpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long app;

    @NotBlank
    private String uri;

    @NotBlank
    private String ip;

    @NotNull
    private LocalDateTime timestamp;

}
