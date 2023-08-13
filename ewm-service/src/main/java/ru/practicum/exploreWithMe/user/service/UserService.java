package ru.practicum.exploreWithMe.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.exploreWithMe.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    List<UserDto> getAll(List<Long> ids, Pageable pageable);

    void delete(Long id);
}
