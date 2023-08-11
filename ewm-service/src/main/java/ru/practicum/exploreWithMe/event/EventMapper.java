package ru.practicum.exploreWithMe.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.exploreWithMe.event.dto.CreateEventDto;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "latitude", source = "location.lat")
    @Mapping(target = "longitude", source = "location.lon")
    Event transformCreateEventDtoToEvent(CreateEventDto eventDto);

    @Mapping(target = "location.lat", source = "latitude")
    @Mapping(target = "location.lon", source = "longitude")
    EventFullDto transformEventToEventFullDto(Event event);

    EventShortDto transformEventFullDtoToEventShortDto(EventFullDto eventDto);
}
