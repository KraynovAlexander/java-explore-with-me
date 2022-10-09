package ru.practicum.emw.users.service;

import ru.practicum.emw.users.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    List<UserDto> getUsersPages(List<Long> ids, Integer from, Integer size);

    UserDto getUserById(Long userId);

    void deleteUserById(Long userId);

    boolean existById(Long userId);

    String getUserNameById(Long userId);

}
