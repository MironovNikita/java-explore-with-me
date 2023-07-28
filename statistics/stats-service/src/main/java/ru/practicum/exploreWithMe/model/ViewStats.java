package ru.practicum.exploreWithMe.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
