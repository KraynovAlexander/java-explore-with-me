package ru.practicum.emw.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.emw.users.dto.UserDto;
import ru.practicum.emw.users.service.UserServiceAdmin;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminUserController {

    private final UserServiceAdmin adminUserService;

    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        log.info("(Admin)UserController.addNewUser: получен запрос на добавление электронной почты нового пользователя={}", userDto.getEmail());
        return adminUserService.addNewUser(userDto);
    }

    @GetMapping
    public List<UserDto> getUsersPages(@RequestParam List<Long> ids,
                                               @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                               @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("(Admin)UserController.getUsersPages: получен запрос на получение всех пользователей");
        return adminUserService.getUsersByIdInByPages(ids, from, size);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        log.info("(Admin)UserController.getUserById: получен запрос на получение пользователя с id={}", userId);
        return adminUserService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        log.info("(Admin)UserController.getUserById: получен запрос на удаление пользователя с id={}", userId);
        adminUserService.deleteUserById(userId);
    }

}
