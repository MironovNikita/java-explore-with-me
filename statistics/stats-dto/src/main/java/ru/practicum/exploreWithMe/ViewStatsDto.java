package ru.practicum.exploreWithMe;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewStatsDto {
    private String app;
    private String uri;
    private Long hits;
}
