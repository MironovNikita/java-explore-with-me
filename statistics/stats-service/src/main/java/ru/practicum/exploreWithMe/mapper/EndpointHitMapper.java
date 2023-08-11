package ru.practicum.exploreWithMe.mapper;

import org.mapstruct.Mapper;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    EndpointHit transformEndpointHitDtoToEndpointHit(EndpointHitDto endpointHitDto);

    EndpointHitDto transformEndpointHitToEndpointHitDto(EndpointHit endpointHit);
}
