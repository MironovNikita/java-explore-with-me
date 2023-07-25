package ru.practicum.exploreWithMe.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "endpointhits")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String app;
    @Column(nullable = false)
    private String uri;
    @Column(nullable = false, length = 20)
    private String ip;
    @Column(nullable = false)
    private Timestamp timestamp;
}
