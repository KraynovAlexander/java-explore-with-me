package ru.practicum.emw.users.service;

import ru.practicum.emw.users.dto.UserDto;

import java.util.List;

public interface UserServiceAdmin {
    UserDto addNewUser(UserDto userDto);

    List<UserDto> getUsersByIdInByPages(List<Long> ids, Integer from, Integer size);

    UserDto getUserById(Long userId);

    void deleteUserById(Long userId);
}
