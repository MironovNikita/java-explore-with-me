package ru.practicum.exploreWithMe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.common.exception.ObjectNotFoundException;
import ru.practicum.exploreWithMe.user.User;
import ru.practicum.exploreWithMe.user.UserMapper;
import ru.practicum.exploreWithMe.user.UserRepository;
import ru.practicum.exploreWithMe.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = userMapper.transformUserDtoToUser(userDto);
        return userMapper.transformUserToUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getAll(List<Long> ids, Pageable pageable) {
        List<User> searchedUsers;
        if (ids == null || ids.isEmpty()) {
            searchedUsers = userRepository.findAll(pageable).toList();
        } else searchedUsers = userRepository.findAllByIdIn(ids, pageable);

        return searchedUsers.stream().map(userMapper::transformUserToUserDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        checkUserExistence(userId);
        userRepository.deleteById(userId);
    }

    private void checkUserExistence(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> {
            log.error("Ошибка получения пользователя по ID {}, такого ID не существует!", userId);
            throw new ObjectNotFoundException("Пользователь", userId);
        });
    }
}
