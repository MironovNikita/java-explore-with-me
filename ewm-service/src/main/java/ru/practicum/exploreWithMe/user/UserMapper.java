package ru.practicum.exploreWithMe.user;

import org.mapstruct.Mapper;
import ru.practicum.exploreWithMe.user.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User transformUserDtoToUser(UserDto userDto);

    UserDto transformUserToUserDto(User user);
}
