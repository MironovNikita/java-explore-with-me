package ru.practicum.exploreWithMe.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.common.pagination.Pagination;
import ru.practicum.exploreWithMe.user.dto.UserDto;
import ru.practicum.exploreWithMe.user.service.UserService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Validated @RequestBody UserDto userDto) {
        log.info("AdminUserController: Запрос на создание пользователя с email {}", userDto.getEmail());
        return userService.create(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll(@RequestParam(name = "ids", required = false) List<Long> ids,
                                @PositiveOrZero(message = "Минимальное значение индекса: 0")
                                @RequestParam(value = "from", defaultValue = "0") Integer from,
                                @Positive(message = "Минимальное количество элементов: 1")
                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("AdminUserController: Запрос на получение пользователей по ID: {}", ids);
        return userService.getAll(ids, Pagination.splitByPages(from, size));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        log.info("AdminUserController: Запрос на удаление пользователя по ID: {}", userId);
        userService.delete(userId);
    }

}
