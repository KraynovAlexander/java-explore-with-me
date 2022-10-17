package ru.practicum.main.users.service;

import ru.practicum.main.users.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    List<UserDto> getUsersByIdInByPages(List<Long> ids, Integer from, Integer size);

    UserDto getUserById(Long userId);

    void deleteUserById(Long userId);

    boolean existById(Long userId);

    String getUserNameById(Long userId);

}
