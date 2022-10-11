package ru.practicum.main.business.service.admins;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.users.service.AdminUserService;
import ru.practicum.main.users.dto.UserDto;
import ru.practicum.main.users.service.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserService userService;

    @Override
    public UserDto addUser(UserDto userDto) {
        return userService.addUser(userDto);
    }

    @Override
    public List<UserDto> getUsersByIdInByPages(List<Long> ids, Integer from, Integer size) {
        return userService.getUsersByIdInByPages(ids, from, size);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public void deleteUserById(Long userId) {
        userService.deleteUserById(userId);
    }
}
