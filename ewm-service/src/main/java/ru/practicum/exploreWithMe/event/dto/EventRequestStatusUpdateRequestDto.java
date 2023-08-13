package ru.practicum.exploreWithMe.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.request.RequestStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventRequestStatusUpdateRequestDto {
    private List<Long> requestIds;
    private RequestStatus status;
}
