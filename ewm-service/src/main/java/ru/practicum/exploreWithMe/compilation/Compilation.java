package ru.practicum.exploreWithMe.compilation;

import lombok.*;
import ru.practicum.exploreWithMe.event.Event;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean pinned;
    private String title;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_compilations",
    joinColumns = @JoinColumn(name = "compilation_id"),
    inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;
}
