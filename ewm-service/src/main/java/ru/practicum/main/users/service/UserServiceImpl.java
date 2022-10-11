package ru.practicum.main.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.users.dto.UserDto;
import ru.practicum.main.users.exceptions.UserException;
import ru.practicum.main.users.mapper.UserMapper;
import ru.practicum.main.users.model.User;
import ru.practicum.main.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {

        log.info("UserService.addUser: отправьте запрос в базу данных для создания нового пользователя с email={}", userDto.getEmail());

        User user = userRepository.save(UserMapper.fromDto(userDto));

        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsersByIdInByPages(List<Long> ids, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id").descending());

        log.info("UserService.getAllUsers: отправьте запрос в базу данных, чтобы получить пользователя по ids={} по страницам", ids);

        return userRepository.getUsersByIdIn(ids, pageable).getContent()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {

        log.info("UserService.getUserById: отправьте запрос в базу данных, чтобы получить пользователя с id={}", userId);

        return UserMapper.toDto(userRepository.findById(userId)
                .orElseThrow(() -> new UserException("пользователь не существует")));
    }

    @Override
    public void deleteUserById(Long userId) {

        if (!existById(userId)) {
            log.error("UserService.deleteUserById: пользователь с id={} не существует", userId);
            throw new UserException("user not exist");
        }

        log.info("UserService.deleteUserById: отправить запрос в базу данных на удаление пользователя с id={}", userId);
        userRepository.deleteById(userId);

    }

    @Override
    public String getUserNameById(Long userId) {

        return userRepository.findUserNameById(userId)
                .orElseThrow(() -> new UserException("пользователь не существует"));

    }

    @Override
    public boolean existById(Long userId) {
        return userRepository.existsById(userId);
    }
}
