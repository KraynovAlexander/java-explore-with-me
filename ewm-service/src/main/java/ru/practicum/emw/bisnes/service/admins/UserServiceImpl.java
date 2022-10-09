package ru.practicum.emw.bisnes.service.admins;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.emw.users.dto.UserDto;
import ru.practicum.emw.users.service.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserService userService;

    @Override
    public UserDto addUser(UserDto userDto) {
        return userService.addUser(userDto);
    }

    @Override
    public List<UserDto> getUsersPages(List<Long> ids, Integer from, Integer size) {
        return userService.getUsersPages(ids, from, size);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public void deleteUserById(Long userId) {
        userService.deleteUserById(userId);
    }

    @Override
    public boolean existById(Long userId) {
        return false;
    }

    @Override
    public String getUserNameById(Long userId) {
        return null;
    }


}
