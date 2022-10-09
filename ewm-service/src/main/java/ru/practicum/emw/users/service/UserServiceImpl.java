package ru.practicum.emw.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.emw.users.dto.UserDto;
import ru.practicum.emw.users.exceptions.UserNotFoundException;
import ru.practicum.emw.users.mapper.UserMapper;
import ru.practicum.emw.users.model.User;
import ru.practicum.emw.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {

        log.info("UserService.addUser: отправьте запрос в базу данных на создание нового пользователя по электронной почте={}", userDto.getEmail());

        User user = userRepository.save(UserMapper.fromDto(userDto));

        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsersPages(List<Long> ids, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id").descending());

        log.info("UserService.getAllUsers: отправьте запрос в базу данных, чтобы получить пользователя по id={} по страницам", ids);

        return userRepository.getUsersByIdIn(ids, pageable).getContent()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {

        log.info("UserService.getUserById: отправьте запрос в базу данных, чтобы получить пользователя с id={}", userId);

        return UserMapper.toDto(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("пользователь не существует")));
    }

    @Override
    public void deleteUserById(Long userId) {

        if (!existById(userId)) {
            log.error("UserService.deleteUserById: пользователь с id={} не существует", userId);
            throw new UserNotFoundException("пользователь не существует");
        }

        log.info("UserService.deleteUserById: отправить запрос в базу данных на удаление пользователя с id={}", userId);
        userRepository.deleteById(userId);

    }

    @Override
    public String getUserNameById(Long userId) {

        return userRepository.findUserNameById(userId)
                .orElseThrow(() -> new UserNotFoundException("пользователь не существует"));

    }

    @Override
    public boolean existById(Long userId) {
        return userRepository.existsById(userId);
    }
}
