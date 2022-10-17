package ru.practicum.main.users.service;

import ru.practicum.main.users.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    UserDto addUser(UserDto userDto);

    List<UserDto> getUsersByIdInByPages(List<Long> ids, Integer from, Integer size);

    UserDto getUserById(Long userId);

    void deleteUserById(Long userId);
}
